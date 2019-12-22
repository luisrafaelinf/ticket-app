/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.report;

import com.tickettest.ticket.backend.model.TicketEntry;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

/**
 *
 * @author luisrafaelinf
 */
public final class TicketEntryReportGrid extends Grid<TicketEntry> {
    
    public final static String TICKET = "ticket";
    public final static String TIME = "time";
    
    private String filterText = "";

    public final static TicketEntryReportGrid of() {
        return new TicketEntryReportGrid();
    }
    
    private TicketEntryReportGrid() {
        
        setSizeFull();

        
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        final String ticketTemplate = "<div class-name=\"ticket-number\" >[[item.ticketNumber]]</div>";
        addColumn(TemplateRenderer.<TicketEntry>of(ticketTemplate)
                .withProperty("ticketNumber", this::formatTicketNumber)
        ).setHeader("Ticket")
                .setKey(TICKET);

        addColumn(t -> t.getEmployeeId().name())
                .setHeader("Employee");
        addColumn(TicketEntry::getNote)
                .setHeader("Note");

        addColumn(new LocalDateTimeRenderer<TicketEntry>(TicketEntry::getFromDate,
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM))
        ).setHeader("Date Start");

        addColumn(new LocalDateTimeRenderer<TicketEntry>(TicketEntry::getToDate,
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM))
        ).setHeader("Date End");

        addColumn(TicketEntry::time).setHeader("Hours")
                .setKey(TIME);

    }

    private String formatTicketNumber(TicketEntry entry) {
        return String.format("#%d", entry.getTicketId().getId());
    }

    public final void filter(String filterText) {

        Objects.requireNonNull(filterText, "Filter text cannot be null");

        if (this.filterText.equals(filterText)) {
            return;
        }

        this.filterText = filterText;

        ListDataProvider<TicketEntry> data = (ListDataProvider<TicketEntry>) getDataProvider();
        data.setFilter(null);
        data.setFilter(this::filterTicketEntry);

    }

    private boolean filterTicketEntry(TicketEntry ticketEntry) {
        return passFilter(String.valueOf(ticketEntry.getTicketId()), filterText)
        || passFilter(String.valueOf(ticketEntry.getEmployeeId()), filterText)
                || passFilter(ticketEntry.getNote(), filterText);
    }

    private boolean passFilter(String attribute, String filterText) {
        return Objects.nonNull(attribute)
                && attribute.toLowerCase().trim()
                        .contains(filterText.toLowerCase().trim());
    }
}
