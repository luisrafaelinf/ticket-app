package com.tickettest.ticket.views.tiketstatus;


import com.tickettest.ticket.backend.model.TicketStatus;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.value.ValueChangeMode;


abstract class TicketStatusView extends VerticalLayout implements AfterNavigationObserver {

    protected TextField search = new TextField();
    protected TicketStatusGrid status = new TicketStatusGrid();
    
    protected TextField description = new TextField();

    protected Button cancel = new Button("Cancel");
    protected Button save = new Button("Save");

    protected BeanValidationBinder<TicketStatus> binder = new BeanValidationBinder<>(TicketStatus.class);

    public TicketStatusView() {
        
        setId("ticketstatus-view");

        createBinderValidation();

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);
    }
    
    private void createTitle(VerticalLayout wrapper) {
        H1 h1 = new H1("Ticket Status");
        wrapper.add(h1);
    }
    
    private void createBinderValidation() {
        
        binder.forField(description)
                .withValidator(this::validationDescription, "The description can't be empty")
                .bind(TicketStatus::getDescription, TicketStatus::setDescription);
    }

    private boolean validationDescription(String e) {
        return !e.toString().trim().isEmpty();
    }
    
    private void createEditorLayout(SplitLayout splitLayout) {
        
        Div editorDiv = new Div();
        editorDiv.setId("editor-layout");
        FormLayout formLayout = new FormLayout();
        addFormItem(editorDiv, formLayout, description, "Description");
        createButtonLayout(editorDiv);
        splitLayout.addToSecondary(editorDiv);
    }

    private void createButtonLayout(Div editorDiv) {
        
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(cancel, save);
        editorDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setWidthFull();
        wrapper.setSpacing(true);
        splitLayout.addToPrimary(wrapper);
        
        search.setValueChangeMode(ValueChangeMode.EAGER);
        search.setPlaceholder("Filter by Description");
        search.setWidthFull();
        createTitle(wrapper);
        wrapper.add(search);
        wrapper.add(status);
    }

    private void addFormItem(Div wrapper, FormLayout formLayout, AbstractField field, String fieldName) {
        formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
    }

}
