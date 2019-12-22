/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket.views.report;

import com.tickettest.ticket.MainView;
import com.tickettest.ticket.backend.model.TicketEntry;
import com.tickettest.ticket.backend.repository.TicketEntryJpaRepository;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author luisrafaelinf
 */
@Route(value = "report", layout = MainView.class)
@PageTitle("Report")
@CssImport("./styles/views/report/report-view.css")
public final class ReportController extends ReportView {

    @Autowired
    private TicketEntryJpaRepository entryJpaRepository;

    private UI ui;

    public ReportController() {

        ui = UI.getCurrent();

        addAction();

    }

    private void addAction() {

        fromDate.addValueChangeListener(this::valueFromDateChange);

        toDate.addValueChangeListener(this::valueToDateChange);

        search.addValueChangeListener(this::valueFilterChanged);

    }

    private void valueFilterChanged(AbstractField.ComponentValueChangeEvent<TextField, String> event) {
        reportGrid.filter(event.getValue());
    }

    private void valueFromDateChange(final AbstractField.ComponentValueChangeEvent<DatePicker, LocalDate> event) {

        final LocalDate startDate = event.getValue();

        if (Objects.nonNull(startDate)) {

            toDate.setMin(startDate);
            toDate.setOpened(true);

        } else {

            toDate.setMin(null);
        }
    }

    private void valueToDateChange(final AbstractField.ComponentValueChangeEvent<DatePicker, LocalDate> event) {
        if (Objects.nonNull(event.getValue())) {
            afterNavigation(null);
        }
    }

    private void loadData() {

        if (fromDate.isInvalid() || toDate.isInvalid()) {
            return;
        }

        try {

            final List<TicketEntry> entrys = entryJpaRepository.findBetweenDate(fromDate.getValue(), toDate.getValue());

            ReportFillGrid reportFooterGrid = ReportFillGrid.of(entrys, reportGrid);
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

}
