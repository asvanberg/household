package com.svanberg.household.web;

import com.svanberg.household.web.pages.ExpensePage;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import org.apache.wicket.devutils.stateless.StatelessChecker;
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

        mountPages();

        // Hook up Bootstrap
        Bootstrap.install(get(), new BootstrapSettings());

        // Hook up Spring
        getComponentInstantiationListeners().add(getInjector());
        getComponentPostOnBeforeRenderListeners().add(new StatelessChecker());
    }

    protected SpringComponentInjector getInjector() {
        return new SpringComponentInjector(this);
    }

    private void mountPages() {
        mountPage("expenses", ExpensePage.class);
    }
}
