package com.tickettest.ticket.views.ticket;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

abstract class TicketView extends Div implements AfterNavigationObserver {

    protected TextField search = new TextField();
    protected TicketGrid ticketGrid;

    protected Button createTicket = new Button("Create Ticket");

    public TicketView() {
        setId("ticket-view");

        ticketGrid = TicketGrid.of();
        ticketGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        ticketGrid.setHeightFull();

        VerticalLayout formPane = new VerticalLayout();
        formPane.setSizeFull();
        
        createTitle(formPane);
        createFilterLayout(formPane);
        createGridLayout(formPane);

        add(formPane);

    }
    
    private void createTitle(VerticalLayout wrapper) {
        H1 h1 = new H1("Ticket");
        wrapper.add(h1);
    }

    private void createFilterLayout(VerticalLayout fieldContainer) {
        HorizontalLayout fieldLayout = new HorizontalLayout();
        fieldLayout.setId("field-layout");
        fieldLayout.setSpacing(true);
        fieldLayout.setWidthFull();
        fieldLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        
        search.setValueChangeMode(ValueChangeMode.EAGER);
        search.setPlaceholder("Filter by Ticket number, Description");
        
        fieldLayout.add(search);
        fieldLayout.setVerticalComponentAlignment(FlexComponent.Alignment.START, search);
        
        createTicket.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        fieldLayout.add(createTicket);
        fieldLayout.setVerticalComponentAlignment(FlexComponent.Alignment.END, createTicket);
        
        fieldLayout.expand(search);
        fieldLayout.setFlexGrow(1, search);

        fieldContainer.add(fieldLayout);
        fieldContainer.setHorizontalComponentAlignment(FlexComponent.Alignment.START, fieldLayout);
        
    }

    private void createGridLayout(VerticalLayout fieldContainer) {
        Div wrapper = new Div();
        wrapper.setId("wrapper");
        wrapper.setWidthFull();

        wrapper.add(ticketGrid);
        fieldContainer.add(wrapper);
        fieldContainer.setFlexGrow(1, wrapper);
    }

}
