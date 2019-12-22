/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.vaadinservice;

import com.tickettest.ticket.util.VaadinAttributeSession;
import com.tickettest.ticket.views.authentication.LoginController;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.UIInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;
import java.util.Objects;

/**
 *
 * @author luisrafaelinf
 */
public class TicketInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent initEvent) {
        
        initEvent.getSource().addUIInitListener(this::Listener);

    }

    private void Listener(UIInitEvent initEvent) {
        

        initEvent.getUI().addBeforeEnterListener(this::beforeEvent);

    }

    private void beforeEvent(BeforeEnterEvent enterEvent) {

        Long userId = (Long) VaadinSession.getCurrent().getAttribute(VaadinAttributeSession.USER_ID);
        
        if (Objects.isNull(userId)
                && !LoginController.class.equals(enterEvent.getNavigationTarget())) {
            
            enterEvent.rerouteTo(LoginController.class);
            UI.getCurrent().navigate("login");
            
        }
    }
}
