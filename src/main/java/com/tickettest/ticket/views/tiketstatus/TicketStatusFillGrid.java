/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.tiketstatus;

import com.tickettest.ticket.views.report.*;
import com.tickettest.ticket.backend.model.TicketEntry;
import com.tickettest.ticket.backend.model.TicketStatus;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.server.Command;
import java.util.List;

/**
 *
 * @author luisrafaelinf
 */
public final class TicketStatusFillGrid implements Command {

    private List<TicketStatus> entries;
    private TicketStatusGrid statusGrid;
    
    public final static TicketStatusFillGrid of(List<TicketStatus> status, TicketStatusGrid reportGrid) {
        return new TicketStatusFillGrid(status, reportGrid);
    }
    
    private TicketStatusFillGrid(List<TicketStatus> status, TicketStatusGrid statusGrid) {
        this.entries = status;
        this.statusGrid = statusGrid;

    }

    @Override
    public void execute() {
        
        statusGrid.setItems(this.entries);
        
        final Grid.Column<TicketStatus> ticketColumn = statusGrid.getColumnByKey(TicketStatusGrid.DESCRIPTION);
        ticketColumn.setFooter(String.format("Total: %d status", this.entries.size()));

    }

}
