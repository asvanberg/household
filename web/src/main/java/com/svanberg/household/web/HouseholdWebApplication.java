package com.svanberg.household.web;

import com.svanberg.household.web.expense.ExpensePage;

import de.agilecoders.wicket.Bootstrap;
import de.agilecoders.wicket.settings.BootstrapSettings;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class HouseholdWebApplication extends WebApplication {
    
    @Override
    public Class<? extends WebPage> getHomePage() {
        return ExpensePage.class;
    }

    @Override
    public void init() {
        super.init();

        Bootstrap.install(get(), new BootstrapSettings());

        getComponentInstantiationListeners().add(getInjector());
    }

    protected SpringComponentInjector getInjector() {
        return new SpringComponentInjector(this);
    }
}
