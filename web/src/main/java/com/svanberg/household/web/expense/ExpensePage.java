package com.svanberg.household.web.expense;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class ExpensePage extends WebPage {
    private static final long serialVersionUID = -7046290354423141576L;

    public ExpensePage(PageParameters parameters) {
        super(parameters);

        add(new AddExpensePanel("add"));
    }
}
