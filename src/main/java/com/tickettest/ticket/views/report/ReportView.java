package com.tickettest.ticket.views.report;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

abstract class ReportView extends Div implements AfterNavigationObserver {

    protected TextField search = new TextField();
    protected TicketEntryReportGrid reportGrid;

    protected DatePicker fromDate = new DatePicker();
    protected DatePicker toDate = new DatePicker();

    public ReportView() {
        setId("report-view");

        reportGrid = TicketEntryReportGrid.of();
        reportGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        reportGrid.setHeightFull();

        VerticalLayout formPane = new VerticalLayout();
        formPane.setSizeFull();

        createTitle(formPane);
        createFilterLayout(formPane);
        createGridLayout(formPane);

        add(formPane);

    }
    
    private void createTitle(VerticalLayout wrapper) {
        H1 h1 = new H1("Report");
        wrapper.add(h1);
    }

    private void createFilterLayout(VerticalLayout fieldContainer) {
        HorizontalLayout fieldLayout = new HorizontalLayout();
        fieldLayout.setId("field-layout");
        fieldLayout.setSpacing(true);
        fieldLayout.setWidthFull();
        fieldLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        search.setValueChangeMode(ValueChangeMode.EAGER);
        search.setPlaceholder("Filter by Ticket number, Note");
        
        fieldLayout.add(search);
        fieldLayout.setVerticalComponentAlignment(FlexComponent.Alignment.START, search);
        
        FormLayout formLayout = new FormLayout();
        
        formLayout.addFormItem(fromDate, "From");
        formLayout.addFormItem(toDate, "To");
        formLayout.setResponsiveSteps(
                new ResponsiveStep("25em", 1),
                new ResponsiveStep("32em", 2)
        );
        
        fieldLayout.add(formLayout);
        fieldLayout.setVerticalComponentAlignment(FlexComponent.Alignment.END, formLayout);
        
        fieldLayout.expand(search);
        fieldLayout.setFlexGrow(1, search);

        fieldContainer.add(fieldLayout);
        fieldContainer.setHorizontalComponentAlignment(FlexComponent.Alignment.START, fieldLayout);
        
    }

    private void createGridLayout(VerticalLayout fieldContainer) {
        Div wrapper = new Div();
        wrapper.setId("wrapper");
        wrapper.setWidthFull();

        wrapper.add(reportGrid);
        fieldContainer.add(wrapper);
        fieldContainer.setFlexGrow(1, wrapper);
    }

}
