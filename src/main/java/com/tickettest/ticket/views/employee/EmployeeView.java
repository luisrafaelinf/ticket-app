package com.tickettest.ticket.views.employee;

import com.tickettest.ticket.backend.model.Employee;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.value.ValueChangeMode;

abstract class EmployeeView extends VerticalLayout implements AfterNavigationObserver {

    protected TextField search = new TextField();
    protected EmployeeGrid employees = new EmployeeGrid();

    protected TextField userName = new TextField();
    protected TextField firstName = new TextField();
    protected TextField lastName = new TextField();
    protected TextField email = new TextField();
    protected Checkbox status = new Checkbox(true);
    protected PasswordField password = new PasswordField();
    protected PasswordField confirmPassword = new PasswordField();

    protected Button cancel = new Button("Cancel");
    protected Button save = new Button("Save");

    protected BeanValidationBinder<Employee> binder = new BeanValidationBinder<>(Employee.class);

    public EmployeeView() {
        
        setId("employee-view");

        createBinderValidation();

        SplitLayout formPane = new SplitLayout();
        formPane.setSizeFull();

        createGridLayout(formPane);
        createEditorLayout(formPane);

        add(formPane);
    }
    
    private void createTitle(VerticalLayout wrapper) {
        H1 h1 = new H1("Employee");
        wrapper.add(h1);
    }
    
    private void createBinderValidation() {

        binder.forField(userName)
                .withValidator(this::validationEmptyField, "The user name can't be empty")
                .bind(Employee::getUserName, Employee::setUserName);

        binder.forField(firstName)
                .withValidator(this::validationEmptyField, "The first name can't be empty")
                .bind(Employee::getFirstName, Employee::setFirstName);

        binder.forField(lastName)
                .withValidator(this::validationEmptyField, "The last name can't be empty")
                .bind(Employee::getLastName, Employee::setLastName);

        binder.forField(email)
                .withValidator(this::validationEmptyField, "The email can't be empty")
                .withValidator(this::validationEmailCorrect, "The email is not valid")
                .bind(Employee::getEmail, Employee::setEmail);

        binder.forField(status)
                .bind(Employee::getStatus, Employee::setStatus);

        binder.forField(password)
                .withValidator(this::validationEmptyField, "The password can't be empty")
                .withValidator(this::validationConfirmPassword, "The password is not confirmed")
                .bind(Employee::getPassword, Employee::setPassword);

    }

    private boolean validationEmailCorrect(String e) {
        return e.contains("@");
    }

    private boolean validationConfirmPassword(String e) {
        return e.toString().trim().equals(confirmPassword.getValue().trim());
    }

    private boolean validationEmptyField(String e) {
        return !e.toString().trim().isEmpty();
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorDiv = new Div();
        editorDiv.setId("editor-layout");
        FormLayout formLayout = new FormLayout();
        addFormItem(editorDiv, formLayout, userName, "Username");
        addFormItem(editorDiv, formLayout, firstName, "First Name");
        addFormItem(editorDiv, formLayout, lastName, "Last Name");
        addFormItem(editorDiv, formLayout, email, "Email");
        addFormItem(editorDiv, formLayout, password, "Password");
        addFormItem(editorDiv, formLayout, confirmPassword, "Confirm Password");
        addFormItem(editorDiv, formLayout, status, "Status");
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
        search.setPlaceholder("Filter by User name, First name, Last name and email");
        search.setWidthFull();
        
        createTitle(wrapper);
        wrapper.add(search);
        wrapper.add(employees);
    }

    private void addFormItem(Div wrapper, FormLayout formLayout, AbstractField field, String fieldName) {
        formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
    }

}
