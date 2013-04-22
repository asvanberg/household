package com.svanberg.household.web.wicket.pages;

import com.svanberg.household.web.expense.ExpensePage;

import de.agilecoders.wicket.markup.html.bootstrap.behavior.BootstrapResourcesBehavior;
import de.agilecoders.wicket.markup.html.bootstrap.navbar.ImmutableNavbarComponent;
import de.agilecoders.wicket.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.markup.html.bootstrap.navbar.NavbarButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public abstract class HouseholdPage extends WebPage {
    private static final long serialVersionUID = -3134919560093463628L;

    protected HouseholdPage(final PageParameters parameters) {
        super(parameters);

        add(new Label("title", new StringResourceModel("page.title", this, getDefaultModel())));

        add(BootstrapResourcesBehavior.instance());

        Navbar navbar = new Navbar("navbar");
        navbar.fluid();
        navbar.brandName(new ResourceModel("application.title"));
        navbar.setPosition(Navbar.Position.TOP);
        navbar.addComponents(
                new ImmutableNavbarComponent(new NavbarButton<>(ExpensePage.class, new ResourceModel("page.expense")))
        );
        add(navbar);
    }
}
