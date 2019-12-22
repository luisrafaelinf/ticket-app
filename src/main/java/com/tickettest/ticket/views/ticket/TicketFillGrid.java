/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.ticket;

import com.tickettest.ticket.backend.model.Ticket;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.server.Command;
import java.util.List;

/**
 *
 * @author luisrafaelinf
 */
public final class TicketFillGrid implements Command {

    private List<Ticket> tickets;
    private TicketGrid ticketGrid;

    public final static TicketFillGrid of(List<Ticket> tickets, TicketGrid ticketGrid) {
        return new TicketFillGrid(tickets, ticketGrid);
    }
    
    private TicketFillGrid(List<Ticket> entries, TicketGrid reportGrid) {
        this.tickets = entries;
        this.ticketGrid = reportGrid;

    }

    @Override
    public void execute() {
        
        ticketGrid.getDataProvider().refreshAll();
        ticketGrid.setItems(this.tickets);
        
        final Grid.Column<Ticket> ticketColumn = ticketGrid.getColumnByKey(TicketGrid.TICKET);
        ticketColumn.setFooter(String.format("Total: %d tickets", this.tickets.size()));

    }

}
