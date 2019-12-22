/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.employee;

import com.tickettest.ticket.MainView;
import com.tickettest.ticket.backend.model.Employee;
import com.tickettest.ticket.backend.repository.EmployeeJpaRepository;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author luisrafaelinf
 */
@Route(value = "employee", layout = MainView.class)
@PageTitle("Employee")
@CssImport("./styles/views/employee/employee-view.css")
public final class EmployeeController extends EmployeeView {

    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    private UI ui;

    public EmployeeController() {

        addAction();
        ui = UI.getCurrent();
    }

    private void addAction() {

        cancel.addClickListener(e -> {
            employees.asSingleSelect().clear();
        });

        save.addClickListener(this::createEmployee);

        employees.asSingleSelect().addValueChangeListener(this::populateForm);
        
        search.addValueChangeListener(this::valueFilterChanged);

    }
    
    private void valueFilterChanged(AbstractField.ComponentValueChangeEvent<TextField, String> event) {
        employees.filter(event.getValue());
    }

    private void populateForm(final AbstractField.ComponentValueChangeEvent<Grid<Employee>, Employee> event) {

        binder.readBean(event.getValue());

        confirmPassword.setValue("");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        Executor ex = Executors.newSingleThreadExecutor();

        ex.execute(this::loadData);

    }

    private void loadData() {

        List<Employee> emp = employeeJpaRepository.findAll();

        ui.access(EmployeeFillGrid.of(emp, employees));
    }

    private void createEmployee(final ClickEvent<Button> event) {

        Employee employee = employees.asSingleSelect().getValue();
        BinderValidationStatus<Employee> validate = binder.validate();

        if (validate.isOk()) {

            if (Objects.isNull(employee)) {
                employee = this.of();

            }
            binder.writeBeanIfValid(employee);

            employeeJpaRepository.save(employee);
            employees.refreshItem(employee);
            employees.asSingleSelect().clear();
            binder.readBean(this.of());
            confirmPassword.setValue("");

        }

    }

    private Employee of() {
        return new Employee(Boolean.TRUE);
    }

}
