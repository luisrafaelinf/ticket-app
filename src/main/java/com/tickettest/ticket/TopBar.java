/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinSession;

/**
 *
 * @author luisrafaelinf
 */
public final class TopBar extends HorizontalLayout {

    public static TopBar of() {
        return new TopBar();
    }

    private TopBar() {
        
        addClassName("top-bar");
        setSpacing(true);
        setWidthFull();
        
        loadImage();
        createTitleApp();
        createLogOutButton();
    }

    private void loadImage() {
        Image image = new Image("/icons/ticket.png", "");
        image.setWidth("45px");
        image.setHeight("45px");

        add(image);
        setVerticalComponentAlignment(Alignment.START, image);
    }

    private void createTitleApp() {
        H2 title = new H2("Ticket-nitech");

        add(title);
        setVerticalComponentAlignment(Alignment.START, title);
        expand(title);
    }

    private void createLogOutButton() {
        
        Button logOut = new Button("Logout", VaadinIcon.SIGN_OUT.create());
        logOut.addClickListener(this::closeSession);
        logOut.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        
        add(logOut);
        setVerticalComponentAlignment(Alignment.END, logOut);
    }
    
    private void closeSession(ClickEvent<Button> t) {
        VaadinSession.getCurrent().getSession().invalidate();
        UI.getCurrent().getPage().reload();
        
    }

}
