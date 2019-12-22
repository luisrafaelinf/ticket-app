package com.tickettest.ticket;

import com.tickettest.ticket.util.VaadinAttributeSession;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

/**
 * The main view is a top-level placeholder for other views.
 */
@Push
@Route
@JsModule("./styles/shared-styles.js")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
public class MainView extends AppLayout {

    private final MenuBar menu;

    public MainView() {

        menu = MenuBar.of(this);

        addToNavbar(TopBar.of());

        addToDrawer(menu);

        setLocale();
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();

        Long userId = (Long) VaadinSession.getCurrent().getAttribute(VaadinAttributeSession.USER_ID);

        if (Objects.isNull(userId)) {

            getUI().get().navigate("login");

        } else {
            menu.selectTab();
        }
    }

    private void setLocale() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-04:00"));
        Locale.setDefault(new Locale("es", "DO"));
    }
}
