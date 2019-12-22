/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.ticket.ticketform.employee;

import com.flowingcode.vaadin.addons.chipfield.ChipField;
import com.tickettest.ticket.backend.model.Employee;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 *
 * @author luisrafaelinf
 */
public class EmployeeInfoView extends Dialog {
    
    private H4 ticket = new H4("Employees Ticket ");
    protected Div ticketNumber = new Div();
    protected ChipField<Employee> employees = new ChipField<>("");
    
    protected Button cancel = new Button("Close");
    protected Button save = new Button("Save");
    
    public EmployeeInfoView() {
        setId("employee-info");
        
        setWidth("700px");
        //setHeight("500px");
        
        createForm();
    }

    public void setItems() {
    }
    
    private void createForm() {
        VerticalLayout editorDiv = new VerticalLayout();
        editorDiv.addClassName("editor-employee");
        editorDiv.setSizeFull();
        addComponentAsFirst(editorDiv);
        
        HorizontalLayout title = new HorizontalLayout(ticket,ticketNumber);
        title.setWidthFull();
        editorDiv.add(title);
        
        employees.addClassName("employee");
        employees.setClosable(true);
        employees.setWidthFull();
        editorDiv.add(employees);
        
        createButtonLayout(editorDiv);
        
    }
    
    private void createButtonLayout(VerticalLayout editorDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(cancel, save);
        editorDiv.add(buttonLayout);
    }
    
    
}
