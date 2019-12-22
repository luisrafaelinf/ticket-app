/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.report;

import com.tickettest.ticket.backend.model.TicketEntry;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.server.Command;
import java.util.List;

/**
 *
 * @author luisrafaelinf
 */
public final class ReportFillGrid implements Command {

    private List<TicketEntry> entries;
    private TicketEntryReportGrid reportGrid;
    private double totalHours;

    public final static ReportFillGrid of(List<TicketEntry> entries, TicketEntryReportGrid reportGrid) {
        return new ReportFillGrid(entries, reportGrid);
    }
    
    private ReportFillGrid(List<TicketEntry> entries, TicketEntryReportGrid reportGrid) {
        this.entries = entries;
        this.reportGrid = reportGrid;

        this.totalHours = entries.stream()
                .mapToDouble(m -> m.time())
                .sum();
    }

    @Override
    public void execute() {
        
        reportGrid.setItems(this.entries);
        
        final Grid.Column<TicketEntry> ticketColumn = reportGrid.getColumnByKey(TicketEntryReportGrid.TICKET);
        ticketColumn.setFooter(String.format("Total: %d entries", this.entries.size()));

        final Grid.Column<TicketEntry> timeColumn = reportGrid.getColumnByKey(TicketEntryReportGrid.TIME);
        timeColumn.setFooter(String.format("Total: %.02f hours", totalHours));
    }

}
