/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.ticket;

import com.tickettest.ticket.backend.model.Ticket;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
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
public final class TicketGrid extends Grid<Ticket> {

    public final static String TICKET = "ticket";

    private String filterText = "";
    
    DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
    DateTimeFormatter dayFormat =  DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH);

    
    public final static TicketGrid of() {
        return new TicketGrid();
    }

    private TicketGrid() {

        setSizeFull();
        setSelectionMode(SelectionMode.NONE);
        
        addThemeVariants(GridVariant.LUMO_NO_BORDER);

        addComponentColumn(this::cellCustom)
                .setKey(TICKET);
        
    }

    

    private Component cellCustom(Ticket ticket) {
        
        HorizontalLayout cellPanel = new HorizontalLayout();
        cellPanel.addClassName("cell-ticket");
        cellPanel.setSpacing(true);
        
        Div status = new Div();
        status.setText(ticket.getTicketStatus().getDescription());
        status.addClassName("status");
        Div div1 = new Div(status);
        div1.setMinWidth("100px");
        cellPanel.add(div1);
        
        Div id = new Div();
        id.add(new H3(ticket.getId()+""));
        id.add(new Span("Ticket number"));
        
        Div user = new Div();
        user.add(new H3(ticket.getUser().name()));
        user.add(new Span("User"));
        
        Div date = new Div();
        date.add(new H3(ticket.getDate().format(dtf)));
        date.add(new Span(ticket.getDate().format(dayFormat)));
        
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
        
        H5 subject = new H5(ticket.getSubject());
        ticketPanel.add(subject);
        ticketPanel.setColspan(subject, 3);
        
        Div note = new Div();
        note.addClassName("note");
        note.setText(ticket.getDescription());
        ticketPanel.add(note);
        ticketPanel.setColspan(note, 3);
        
        cellPanel.add(ticketPanel);
        cellPanel.expand(ticketPanel);
        
        return cellPanel;
        
    }

    public final void filter(String filterText) {

        Objects.requireNonNull(filterText, "Filter text cannot be null");

        if (this.filterText.equals(filterText)) {
            return;
        }

        this.filterText = filterText;

        ListDataProvider<Ticket> data = (ListDataProvider<Ticket>) getDataProvider();
        data.setFilter(null);
        data.setFilter(this::filterTicket);

    }

    private boolean filterTicket(Ticket ticket) {
        return passFilter(String.valueOf(ticket.getId()), filterText)
                || passFilter(ticket.getDescription(), filterText);
    }

    private boolean passFilter(String attribute, String filterText) {
        return Objects.nonNull(attribute)
                && attribute.toLowerCase().trim()
                        .contains(filterText.toLowerCase().trim());
    }
    
    
}
