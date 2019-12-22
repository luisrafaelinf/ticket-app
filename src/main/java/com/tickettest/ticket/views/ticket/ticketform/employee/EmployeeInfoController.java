/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.ticket.ticketform.employee;

import com.tickettest.ticket.backend.model.Employee;
import com.tickettest.ticket.backend.model.Ticket;
import com.tickettest.ticket.backend.model.TicketEmployee;
import com.tickettest.ticket.backend.repository.EmployeeJpaRepository;
import com.tickettest.ticket.backend.repository.TicketJpaRepository;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author luisrafaelinf
 */
@CssImport("./styles/views/ticket/employee-info.css")
public class EmployeeInfoController extends EmployeeInfoView {

    private TicketJpaRepository ticketJpaRepository;
    private EmployeeJpaRepository employeeJpaRepository;
    private List<Employee> employeesSelected;
    private Ticket ticket;

    public EmployeeInfoController(TicketJpaRepository ticketJpaRepository,
            EmployeeJpaRepository employeeJpaRepository,
            Ticket ticket) {

        this.ticketJpaRepository = ticketJpaRepository;
        this.employeeJpaRepository = employeeJpaRepository;
        this.ticket = ticket;
        this.employeesSelected = ticket.getEmployees()
                .stream().map(e -> e.getEmployeeId())
                .collect(Collectors.toList());

        ticketNumber.setText("#" + ticket.getId());

        addAction();
        loadEmployees();

    }

    private void addAction() {
        cancel.addClickListener(this::closeForm);

        save.addClickListener(this::saveEmployeeRelation);
    }

    private void loadEmployees() {

        List<Employee> employeeAll = employeeJpaRepository.findAllActive();

        List<Employee> collect = employeeAll.stream()
                .filter(e -> {
                    return employeesSelected.stream().anyMatch(em -> em.getId() == e.getId());
                })
                .collect(Collectors.toList());
        
        employees.setItems(employeeAll);

        collect.forEach(employees::addSelectedItem);

    }

    private void saveEmployeeRelation(ClickEvent<Button> t) {

        ticketJpaRepository.deleteRelationWithEmployee(ticket.getId());

        ticket.setEmployees(
                employees.getValue()
                        .stream()
                        .map(this::mapEmployee)
                        .collect(Collectors.toList())
        );

        ticketJpaRepository.save(ticket);

        closeForm(null);

    }

    private TicketEmployee mapEmployee(Employee employee) {
        return new TicketEmployee(ticket, employee);
    }

    private void closeForm(ClickEvent<Button> t) {
        setOpened(false);
        close();
    }

}
