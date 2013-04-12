package com.svanberg.household.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.stereotype.Component;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@Component
public class HouseholdWebApplication extends WebApplication {
    
    @Override
    public Class<? extends WebPage> getHomePage() {
        throw new UnsupportedOperationException("Still in development");
    }

    @Override
    public void init() {
        super.init();

        getComponentInstantiationListeners().add(getInjector());
    }

    protected SpringComponentInjector getInjector() {
        return new SpringComponentInjector(this);
    }
}
