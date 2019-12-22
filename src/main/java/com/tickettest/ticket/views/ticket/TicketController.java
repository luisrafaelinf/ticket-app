/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.ticket;

import com.tickettest.ticket.MainView;
import com.tickettest.ticket.backend.model.Ticket;
import com.tickettest.ticket.backend.repository.EmployeeJpaRepository;
import com.tickettest.ticket.backend.repository.TicketEntryJpaRepository;
import com.tickettest.ticket.backend.repository.TicketJpaRepository;
import com.tickettest.ticket.backend.repository.TicketStatusJpaRepository;
import com.tickettest.ticket.views.ticket.ticketform.employee.EmployeeInfoController;
import com.tickettest.ticket.views.ticket.ticketform.TicketFormController;
import com.tickettest.ticket.views.ticket.ticketform.entry.EntryInfoController;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author luisrafaelinf
 */
@Route(value = "ticket", layout = MainView.class)
@PageTitle("Ticket")
@CssImport("./styles/views/ticket/ticket-view.css")
public final class TicketController extends TicketView {

    @Autowired
    private TicketJpaRepository ticketJpaRepository;

    @Autowired
    private TicketStatusJpaRepository ticketStatusJpaRepository;

    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;
    
    @Autowired 
    private TicketEntryJpaRepository entryJpaRepository;

    private Long ticketId;
    private UI ui;

    public TicketController() {

        addAction();
        ui = UI.getCurrent();

    }

    private void addAction() {

        createTicket.addClickListener(this::newTicket);

        search.addValueChangeListener(this::valueFilterChanged);

        ticketGrid.addComponentColumn(this::action)
                .setFlexGrow(0)
                .setWidth("350px");

    }

    private void openInfoEmployee(Ticket ticket) {

        EmployeeInfoController employeeInfoController
                = new EmployeeInfoController(ticketJpaRepository, employeeJpaRepository, ticket);
        employeeInfoController.addDetachListener(e -> loadData());
        employeeInfoController.setOpened(true);

    }

    private HorizontalLayout action(Ticket source) {

        HorizontalLayout actionPanel = new HorizontalLayout();
        
        Button totalEmployee = new Button();
        totalEmployee.setText(source.getEmployees().size()+" employees");
        totalEmployee.addClickListener(e -> openInfoEmployee(source));
        actionPanel.add(totalEmployee);

        Button entry = new Button();
        entry.setText(source.getEntrys().size()+" entries");
        entry.addClickListener(e -> openInfoEntry(source));
        actionPanel.add(entry);
        
        Button edit = new Button("Edit");
        edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        edit.addClickListener(e -> editTicket(source));
        actionPanel.add(edit);
        
        Button delete = new Button("Delete");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        delete.addClickListener(e -> delete(source));
        actionPanel.add(delete);

        return actionPanel;
    }
    
    private void openInfoEntry(Ticket source) {

        EntryInfoController entry = new EntryInfoController(entryJpaRepository, source);
            entry.setOpened(true);
        entry.addDetachListener(e -> loadData());
        entry.setOpened(true);

    }

    private void valueFilterChanged(AbstractField.ComponentValueChangeEvent<TextField, String> event) {
        ticketGrid.filter(event.getValue());
    }

    private void loadData() {

        try {
            final List<Ticket> tickets = ticketJpaRepository.findAll();

            TicketFillGrid reportFooterGrid = TicketFillGrid.of(tickets, ticketGrid);
            ui.access(reportFooterGrid);

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        final Executor ex = Executors.newSingleThreadExecutor();

        ex.execute(this::loadData);

    }

    private void newTicket(ClickEvent<Button> t) {

        ticketId = null;

        openForm();
    }
    private void editTicket(Ticket ticket) {

        ticketId = ticket.getId();

        openForm();
    }

    private void openForm() {

        TicketFormController form = new TicketFormController(ticketJpaRepository,
                ticketStatusJpaRepository,
                ticketId);

        form.addDetachListener(event -> loadData());
        form.setOpened(true);

    }

    private void delete(Ticket source) {
        ticketJpaRepository.delete(source.getId());
        loadData();
    }

}
