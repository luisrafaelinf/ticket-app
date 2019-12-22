/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.authentication;

import com.tickettest.ticket.backend.model.Employee;
import com.tickettest.ticket.backend.repository.EmployeeJpaRepository;
import com.tickettest.ticket.util.VaadinAttributeSession;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author luisrafaelinf
 */
@Route(value = "login")
@RouteAlias(value = "login")
@PageTitle("Login")
@CssImport("./styles/views/authentication/login-view.css")
public class LoginController extends LoginView {
     
    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;
    
    public LoginController() {
        
        addAction();
        
    }

    private void addAction() {
        
        login.addClickListener(this::findUserLogin);
        getElement().addEventListener("keypress", event -> findUserLogin(null))
                .setFilter("event.key == 'Enter'");
        
    }

    private void findUserLogin(ClickEvent<Button> t) {
        
        Employee employee = employeeJpaRepository.findEmployeeLogin(userName.getValue().trim(), password.getValue().trim());
        
        if (Objects.isNull(employee.getId())) {
            showNotification("Login failed. Please check your username and password.");
        }
        
        VaadinSession.getCurrent().setAttribute(VaadinAttributeSession.USER_ID, employee.getId());
        getUI().get().navigate("ticket");
        
        
    }
    
    private void showNotification(String message) {
        
        new Notification(message, 2000)
                .open();
    }
    
}
