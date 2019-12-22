/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.ticket.ticketform;

import com.tickettest.ticket.backend.model.Ticket;
import com.tickettest.ticket.backend.model.TicketStatus;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author luisrafaelinf
 */
public class TicketFormView extends Dialog {
    
    
    protected TextField subject = new TextField("Subject");
    protected DatePicker date = new DatePicker("Date", LocalDate.now());
    protected ComboBox<TicketStatus> status = new ComboBox<>("Status");
    protected TextArea description = new TextArea();
    
    protected Button cancel = new Button("Cancel");
    protected Button save = new Button("Save");
    
    protected BeanValidationBinder<Ticket> binder = new BeanValidationBinder<>(Ticket.class);

    public TicketFormView() {
        setId("ticket-form");
        
        setWidth("600px");
        
        createBinderValidation();
        
        createForm();
    }

    private void createBinderValidation() {
        
        subject.setRequired(true);
        subject.setRequiredIndicatorVisible(true);
        binder.forField(subject)
                .withValidator(this::validationEmptyField, "The subject can't be empty")
                .bind(Ticket::getSubject, Ticket::setSubject);
        
        date.setRequired(true);
        date.setRequiredIndicatorVisible(true);
        binder.forField(date)
                .withValidator(this::validationNullField, "The date can't be empty")
                .bind(Ticket::getDate, Ticket::setDate);
        
        status.setAllowCustomValue(false);
        status.setInvalid(false);
        status.setRequired(true);
        status.setRequiredIndicatorVisible(true);
        binder.forField(status)
                .withValidator(this::validationNullField ,"Select a ticket status")
                .bind(Ticket::getTicketStatus, Ticket::setTicketStatus);
        
        description.setHeight("150px");
        description.setPlaceholder("Description");
        description.setRequired(true);
        description.setRequiredIndicatorVisible(true);
        binder.forField(description)
                .withValidator(this::validationEmptyField, "The date can't be empty")
                .bind(Ticket::getDescription, Ticket::setDescription);
        
    }
    
    private boolean validationEmptyField(String e) {
        return !e.toString().trim().isEmpty();
    }
    
    private boolean validationNullField(Object e) {
        return Objects.nonNull(e);
    }
    
    private void createForm() {
        VerticalLayout editorDiv = new VerticalLayout();
        editorDiv.setSizeFull();
        addComponentAsFirst(editorDiv);
        
        H3 title = new H3("Form Ticket");
        title.setWidthFull();
        editorDiv.add(title);
        
        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();
        formLayout.setResponsiveSteps(
           new ResponsiveStep("25em", 1),
           new ResponsiveStep("32em", 2),
           new ResponsiveStep("40em", 3));
        
        formLayout.add(subject, date, status, description);
        formLayout.setColspan(subject, 2);
        formLayout.setColspan(description, 3);
        
        editorDiv.add(formLayout);
        
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
