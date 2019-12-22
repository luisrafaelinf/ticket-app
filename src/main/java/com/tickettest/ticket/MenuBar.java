/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tickettest.ticket;

import com.tickettest.ticket.views.employee.EmployeeController;
import com.tickettest.ticket.views.report.ReportController;
import com.tickettest.ticket.views.ticket.TicketController;
import com.tickettest.ticket.views.tiketstatus.TicketStatusController;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author luisrafaelinf
 */
public final class MenuBar extends Tabs {
 
    private MainView main;
    
    public static MenuBar of(MainView main) {
        return new MenuBar(main);
    }
    
    private MenuBar(MainView main) {
        this.main = main;
        
        addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
        setOrientation(Tabs.Orientation.VERTICAL);
        add(getAvailableTabs());
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("Ticket", TicketController.class, VaadinIcon.TICKET.create()));
        tabs.add(createTab("Employee", EmployeeController.class, VaadinIcon.USER.create()));
        tabs.add(createTab("Ticket Status", TicketStatusController.class, VaadinIcon.EDIT.create()));
        tabs.add(createTab("Report", ReportController.class, VaadinIcon.RECORDS.create()));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(String title, Class<? extends Component> viewClass, Icon icon) {

        RouterLink routerLink = new RouterLink(null, viewClass);
        routerLink.addClassName("router-link");

        HorizontalLayout optionMenu = new HorizontalLayout(icon, new Span(title));
        optionMenu.setSpacing(true);
        optionMenu.setPadding(true);

        routerLink.add(optionMenu);
        
        return createTab(routerLink);
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();

        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }
    
    public void selectTab() {

        String target = RouteConfiguration.forSessionScope()
                .getUrl(main.getContent().getClass());

        Optional<Component> tabToSelect = getChildren().filter(tab -> {

            Component child = tab.getChildren().findFirst().get();

            return child instanceof RouterLink
                    && ((RouterLink) child).getHref().equals(target);

        }).findFirst();

        tabToSelect.ifPresent(tab -> setSelectedTab((Tab) tab));
    }
    
}
