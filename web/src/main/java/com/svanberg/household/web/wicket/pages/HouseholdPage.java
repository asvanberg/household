package com.svanberg.household.web.wicket.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
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
    }
}
