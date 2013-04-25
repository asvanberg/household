package com.svanberg.household.web.category;

import com.svanberg.household.web.pages.HouseholdPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class CategoryPage extends HouseholdPage {
    private static final long serialVersionUID = -3327649337900892864L;

    public CategoryPage(PageParameters parameters) {
        super(parameters);

        add(new AddCategoryPanel(ADD_CATEGORY));
    }

    static final String ADD_CATEGORY = "add_panel";
}
