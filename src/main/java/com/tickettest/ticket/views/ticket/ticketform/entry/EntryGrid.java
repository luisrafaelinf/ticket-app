/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.ticket.ticketform.entry;

import com.tickettest.ticket.backend.model.TicketEntry;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author luisrafaelinf
 */
public final class EntryGrid extends Grid<TicketEntry> {
    
    public final static String ENTRY = "entry";
    
    DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");

    public EntryGrid() {
        
        setWidthFull();
        setHeight("425px");
        addThemeVariants(GridVariant.LUMO_NO_BORDER);
        
        addComponentColumn(this::cellCustom)
                .setHeader("History Entries")
                .setKey(ENTRY);
        setHeightByRows(true);
        
    }
    
    private Component cellCustom(TicketEntry entry) {
    
        VerticalLayout cellPanel = new VerticalLayout();
        cellPanel.addClassName("cell-entry");
        cellPanel.setSpacing(true);
        
        Div user = new Div();
        user.add(new H3(entry.getEmployeeId().name()));
        user.add(new Span("User"));
        
        cellPanel.add(user);
        
        Div dateFrom = new Div();
        dateFrom.add(new H4(entry.getFromDate().format(dtf)));
        dateFrom.add(new Span("Date From"));
        
        Div dateTo = new Div();
        dateTo.add(new H4(entry.getToDate().format(dtf)));
        dateTo.add(new Span("Date End"));
        
        HorizontalLayout datePane = new HorizontalLayout( dateFrom, dateTo);
        datePane.setWidthFull();
        datePane.setSpacing(true);
        datePane.expand(dateFrom);
        datePane.expand(dateTo);
        
        cellPanel.add(datePane);
        
        Div note = new Div();
        note.setText(entry.getNote());
        note.setSizeFull();
        
        cellPanel.add(note);
        
        return cellPanel;
        
        
        
    }
    
    
}
