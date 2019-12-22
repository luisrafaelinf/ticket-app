/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.authentication;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

/**
 *
 * @author luisrafaelinf
 */
abstract class LoginView extends FlexLayout {
    
    protected TextField userName = new TextField("Username");
    protected PasswordField password = new PasswordField("Password");
    
    protected Button login = new Button("Login");
    
    public LoginView() {
        
        setId("login-view");
        setSizeFull();
        
        userName.focus();
        
        createLoginForm();
        
    }

    private void createLoginForm() {
        
        FlexLayout centerLAyout = new FlexLayout();
        centerLAyout.setHeightFull();
        centerLAyout.setJustifyContentMode(JustifyContentMode.CENTER);
        centerLAyout.setAlignItems(Alignment.CENTER);
        
        FormLayout loginForm = new FormLayout();
        loginForm.setWidth("350px");
        loginForm.addClassName("login-form");
        
        loginForm.add(userName);
        userName.setWidth("15em");
        userName.setValue("Admin");
        
        loginForm.add(new Html("<br/>"));
        
        loginForm.add(password);
        password.setWidth("15em");
        password.setValue("admin");
        
        loginForm.add(new Html("<br/>"));
        
        login.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
        loginForm.add(login);
        login.setMaxWidth("100px");
        
        centerLAyout.add(loginForm);
        add(createLoginInformation());
        add(centerLAyout);
        
    }
    
    private Component createLoginInformation() {
        
        VerticalLayout loginInformation = new VerticalLayout();
        loginInformation.setClassName("login-info");
        
        H1 loginInfoHeader = new H1("Login Information");
        loginInformation.add(loginInfoHeader);
        
        Span loginInfoText = new Span(
                "Log in as \"Admin\" with the password \"admin\" for to have access. Enjoy the best app for trade your tickets with the people you like."
        );
        loginInformation.add(loginInfoText);
        
        return loginInformation;
    }
    
}
