/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.ticket.ticketform;

import com.tickettest.ticket.backend.model.Employee;
import com.tickettest.ticket.backend.model.Ticket;
import com.tickettest.ticket.backend.model.TicketStatus;
import com.tickettest.ticket.backend.repository.EmployeeJpaRepository;
import com.tickettest.ticket.backend.repository.TicketJpaRepository;
import com.tickettest.ticket.backend.repository.TicketStatusJpaRepository;
import com.tickettest.ticket.util.VaadinAttributeSession;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.server.VaadinSession;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luisrafaelinf
 */
@CssImport("./styles/views/ticket/ticket-form.css")
public final class TicketFormController extends TicketFormView {

    private TicketJpaRepository ticketJpaRepository;
    private TicketStatusJpaRepository ticketStatusJpaRepository;

    private UI ui;

    public TicketFormController(TicketJpaRepository ticketJpaRepository,
            TicketStatusJpaRepository ticketStatusJpaRepository,
            Long ticketId) {

        this.ticketJpaRepository = ticketJpaRepository;
        this.ticketStatusJpaRepository = ticketStatusJpaRepository;

        addAction();
        ui = UI.getCurrent();
        
        loadTicketStatus();
        setParameter(ticketId);
    }

    private void addAction() {

        cancel.addClickListener(e -> {
            closeForm();
        });

        save.addClickListener(this::createTicket);
        
    }
    
    private void populateForm(final Ticket ticket) {
        binder.setBean(ticket);
    }

    private void loadData(Long id) {

        Ticket ticket = ticketJpaRepository.findById(id);

        populateForm(ticket);
    }

    private void createTicket(final ClickEvent<Button> event) {

        Ticket ticket = binder.getBean();
        BinderValidationStatus<Ticket> validate = binder.validate();

        if (validate.isOk()) {

            binder.writeBeanIfValid(ticket);

            Long idUser = (Long) VaadinSession.getCurrent().getAttribute(VaadinAttributeSession.USER_ID);

            if (Objects.isNull(ticket.getId())) {
                ticket.setUser(new Employee(idUser));
            }
            
            ticketJpaRepository.save(ticket);
            closeForm();
        }
    }

    private Ticket of() {
        return new Ticket();
    }

    private void closeForm() {

        setOpened(false);
        close();
        ui.navigate("ticket");

    }

    public void setParameter(Long ticketId) {

        if (Objects.isNull(ticketId)) {
            populateForm(this.of());
        } else {
            loadData(ticketId);
        }

    }

    private void loadTicketStatus() {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            
            executor.submit(this::loadStatus).get();
            
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(TicketFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void loadStatus() {

        List<TicketStatus> statusAll = ticketStatusJpaRepository.findAll();

        status.setItems(statusAll);

    }

}
