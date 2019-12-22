/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.ticket.ticketform.entry;

import com.tickettest.ticket.backend.model.TicketEntry;
import com.tickettest.ticket.customcomponent.CustomDateTimePicker;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import java.util.Objects;

/**
 *
 * @author luisrafaelinf
 */
public class EntryInfoView extends Dialog {
    
    private H4 ticket = new H4("Entry Ticket ");
    protected Div ticketNumber = new Div();
    
    protected CustomDateTimePicker fromDate = new CustomDateTimePicker("From");
    protected CustomDateTimePicker toDate = new CustomDateTimePicker("To");
    protected TextArea note = new TextArea();
    protected EntryGrid entries = new EntryGrid();
    
    protected Button cancel = new Button("Close");
    protected Button save = new Button("Save");
    
    BeanValidationBinder<TicketEntry> binder = new BeanValidationBinder<>(TicketEntry.class);
    
    public EntryInfoView() {
        
        setId("entry-info");
        
        setWidth("500px");
        setHeight("700px");
        
        createBinderValidation();
        
        createForm();
    }

    public void createBinderValidation() {
        
        binder.forField(fromDate)
                .withValidator(this::validationNullField,"Indicate the start date")
                .bind(TicketEntry::getFromDate, TicketEntry::setFromDate);
        
        binder.forField(toDate)
                .withValidator(this::validationNullField,"Indicate the end date")
                .bind(TicketEntry::getToDate, TicketEntry::setToDate);
        
        note.setPlaceholder("Description");
        note.setRequired(true);
        note.setRequiredIndicatorVisible(true);
        binder.forField(note)
                .withValidator(this::validationEmptyField, "The note can't be empty")
                .bind(TicketEntry::getNote, TicketEntry::setNote);
        
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
        
        HorizontalLayout title = new HorizontalLayout(ticket,ticketNumber);
        title.setWidthFull();
        editorDiv.add(title);
        
        FormLayout formEdit = new FormLayout();
        formEdit.setWidthFull();
        
        formEdit.add(fromDate, toDate, note);
        formEdit.setResponsiveSteps(
           new FormLayout.ResponsiveStep("25em", 1),
           new FormLayout.ResponsiveStep("32em", 2),
           new FormLayout.ResponsiveStep("40em", 3));
        formEdit.setColspan(fromDate, 1);
        formEdit.setColspan(toDate, 1);
        formEdit.setColspan(note, 3);
        
        editorDiv.add(formEdit);
        editorDiv.add(entries);
        
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
