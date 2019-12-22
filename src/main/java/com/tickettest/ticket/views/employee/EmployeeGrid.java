/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.employee;

import com.tickettest.ticket.backend.model.Employee;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author luisrafaelinf
 */
public final class EmployeeGrid extends Grid<Employee> {

    public final static String USER_NAME = "username";

    private String filterText = "";
    
    DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
    DateTimeFormatter dayFormat =  DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH);

    public EmployeeGrid() {
        setSizeFull();

        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        addThemeVariants(GridVariant.LUMO_NO_BORDER);
        
        addComponentColumn(this::cellCustom)
                .setKey(USER_NAME);

    }
    
    private Component cellCustom(Employee employee) {
        
        HorizontalLayout cellPanel = new HorizontalLayout();
        cellPanel.addClassName("cell-employee");
        cellPanel.setSpacing(true);
        
        Div status = new Div();
        status.setText(employee.getStatus() ? "Active" : "Inactive");
        status.addClassName("status");
        status.addClassName(validStatusLayoutRender(employee));
        Div wrapStatus = new Div(status);
        wrapStatus.setMinWidth("100px");
        cellPanel.add(wrapStatus);
        
        Div id = new Div();
        id.add(new H3(employee.getUserName()));
        id.add(new Span("User"));
        
        Div user = new Div();
        user.add(new H3(employee.name()));
        user.add(new Span(employee.getEmail()));
        
        Div date = new Div();
        date.add(new H3(employee.getDateCreated().format(dtf)));
        date.add(new Span(employee.getDateCreated().format(dayFormat)));
        
        FormLayout ticketPanel = new FormLayout( id, user, date);
        ticketPanel.setWidthFull();
        
        ticketPanel.setResponsiveSteps(
           new FormLayout.ResponsiveStep("30em", 1),
           new FormLayout.ResponsiveStep("35em", 2),
           new FormLayout.ResponsiveStep("35em", 3),
           new FormLayout.ResponsiveStep("35em", 4)
        );
        ticketPanel.setColspan(id, 1);
        ticketPanel.setColspan(user, 2);
        ticketPanel.setColspan(date, 1);
        
        cellPanel.add(ticketPanel);
        cellPanel.expand(ticketPanel);
        
        return cellPanel;
        
        
    }

    private String validStatusLayoutRender(Employee employee) {
        return employee.getStatus() ? "active" : "inactive";
    }

    public void refreshItem(Employee employee) {

        ListDataProvider<Employee> data = (ListDataProvider<Employee>) getDataProvider();

        if (!data.getItems().contains(employee)) {
            data.getItems().add(employee);
        }
        data.refreshAll();

    }

    public final void filter(String filterText) {

        Objects.requireNonNull(filterText, "Filter text cannot be null");

        if (this.filterText.equals(filterText)) {
            return;
        }

        this.filterText = filterText;

        ListDataProvider<Employee> data = (ListDataProvider<Employee>) getDataProvider();
        data.setFilter(null);
        data.setFilter(this::filterEmployee);

    }

    private boolean filterEmployee(Employee employee) {
        return passFilter(employee.getUserName(), filterText)
                || passFilter(employee.getFirstName(), filterText)
                || passFilter(employee.getLastName(), filterText)
                || passFilter(employee.getEmail(), filterText);
    }

    private boolean passFilter(String attribute, String filterText) {
        return Objects.nonNull(attribute)
                && attribute.toLowerCase().trim()
                        .contains(filterText.toLowerCase().trim());
    }

}
