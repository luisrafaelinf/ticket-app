/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.tiketstatus;

import com.tickettest.ticket.backend.model.TicketStatus;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.provider.ListDataProvider;
import java.util.Objects;

/**
 *
 * @author luisrafaelinf
 */
public final class TicketStatusGrid extends Grid<TicketStatus> {
    
    public final static String DESCRIPTION = "description";
    private String filterText = "";
    
    public TicketStatusGrid() {
        setSizeFull();
        
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        addThemeVariants(GridVariant.LUMO_NO_BORDER);
        
        addColumn(TicketStatus::getDescription)
                .setHeader("Description Ticket Status")
                .setKey(DESCRIPTION);
        
    }
    
    public void refreshItem(TicketStatus ticket) {
        
        ListDataProvider<TicketStatus> e = (ListDataProvider<TicketStatus>) getDataProvider();
        
        if (!e.getItems().contains(ticket)) {
            e.getItems().add(ticket);
        }
        e.refreshAll();
        
    }
    
    public final void filter(String filterText) {

        Objects.requireNonNull(filterText, "Filter text cannot be null");

        if (this.filterText.equals(filterText)) {
            return;
        }

        this.filterText = filterText;

        ListDataProvider<TicketStatus> data = (ListDataProvider<TicketStatus>) getDataProvider();
        data.setFilter(null);
        data.setFilter(this::filterStatus);

    }

    private boolean filterStatus(TicketStatus status) {
        return passFilter(status.getDescription(), filterText);
    }

    private boolean passFilter(String attribute, String filterText) {
        return Objects.nonNull(attribute)
                && attribute.toLowerCase().trim()
                        .contains(filterText.toLowerCase().trim());
    }
    
}
