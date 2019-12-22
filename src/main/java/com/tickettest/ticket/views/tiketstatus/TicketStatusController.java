/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.tiketstatus;

import com.tickettest.ticket.MainView;
import com.tickettest.ticket.backend.model.TicketStatus;
import com.tickettest.ticket.backend.repository.TicketStatusJpaRepository;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author luisrafaelinf
 */
@Route(value = "ticketstatus", layout = MainView.class)
@PageTitle("TicketStatus")
@CssImport("./styles/views/ticketstatus/ticketstatus-view.css")
public final class TicketStatusController extends TicketStatusView {

    @Autowired
    private TicketStatusJpaRepository ticketStatusJpaRepository;

    private UI ui;

    public TicketStatusController() {

        addAction();
        ui = UI.getCurrent();
    }

    private void addAction() {

        cancel.addClickListener(e -> {
            status.asSingleSelect().clear();
        });

        save.addClickListener(e -> {
            createEmployee();
        });

        status.asSingleSelect().addValueChangeListener(this::populateForm);
        search.addValueChangeListener(this::valueFilterChanged);

    }
    
    private void valueFilterChanged(AbstractField.ComponentValueChangeEvent<TextField, String> event) {
        status.filter(event.getValue());
    }

    private void populateForm(AbstractField.ComponentValueChangeEvent<Grid<TicketStatus>, TicketStatus> event) {
        binder.readBean(event.getValue());
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        Executor ex = Executors.newSingleThreadExecutor();

        ex.execute(this::loadData);
    }

    private void loadData() {
        
        List<TicketStatus> ti = ticketStatusJpaRepository.findAll();

        ui.access(TicketStatusFillGrid.of(ti, status));
    }

    private void createEmployee() {

        TicketStatus ticket = status.asSingleSelect().getValue();
        BinderValidationStatus<TicketStatus> validate = binder.validate();

        if (validate.isOk()) {

            if (Objects.isNull(ticket)) {
                ticket = this.of();

            }
            binder.writeBeanIfValid(ticket);

            ticketStatusJpaRepository.save(ticket);
            status.refreshItem(ticket);
            status.asSingleSelect().clear();
            binder.readBean(this.of());

        }

    }

    private TicketStatus of() {
        return new TicketStatus();
    }

}
