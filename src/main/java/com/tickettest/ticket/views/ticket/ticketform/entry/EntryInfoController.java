/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.ticket.ticketform.entry;

import com.tickettest.ticket.backend.model.Employee;
import com.tickettest.ticket.backend.model.Ticket;
import com.tickettest.ticket.backend.model.TicketEntry;
import com.tickettest.ticket.backend.repository.TicketEntryJpaRepository;
import com.tickettest.ticket.util.VaadinAttributeSession;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.server.VaadinSession;
import java.util.List;

/**
 *
 * @author luisrafaelinf
 */
@CssImport("./styles/views/ticket/entry-info.css")
public class EntryInfoController extends EntryInfoView {

    private TicketEntryJpaRepository entryJpaRepository;
    
    private Ticket ticket;

    public EntryInfoController(TicketEntryJpaRepository entryJpaRepository,
            Ticket ticket) {

        this.entryJpaRepository = entryJpaRepository;
        this.ticket = ticket;
                
        ticketNumber.setText("#" + ticket.getId());

        addAction();
        loadEntries();

    }

    private void addAction() {
        cancel.addClickListener(this::closeForm);

        save.addClickListener(this::saveEmployeeRelation);
    }

    private void loadEntries() {

        List<TicketEntry> entrys = ticket.getEntrys();
        
        entries.setItems(entrys);
        Grid.Column<TicketEntry> column = entries.getColumnByKey(EntryGrid.ENTRY);
        column.setFooter(String.format("Total: %d entrys", entrys.size()));

    }

    private void saveEmployeeRelation(ClickEvent<Button> t) {

        Long UserId = (Long) VaadinSession.getCurrent().getAttribute(VaadinAttributeSession.USER_ID);
        
        TicketEntry entry = of();
        entry.setEmployeeId(new Employee(UserId));
        entry.setTicketId(ticket);
        
        BinderValidationStatus<TicketEntry> validate = binder.validate();
        
        if (validate.isOk()) {
            
            binder.writeBeanIfValid(entry);
            entryJpaRepository.save(entry);
            closeForm(null);
            
        }

    }
    
    private TicketEntry of() {
        return new TicketEntry();
    }

    private void closeForm(ClickEvent<Button> t) {
        setOpened(false);
        close();
    }

}
