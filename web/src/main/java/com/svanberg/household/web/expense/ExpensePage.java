package com.svanberg.household.web.expense;

import com.svanberg.household.web.wicket.pages.HouseholdPage;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class ExpensePage extends HouseholdPage {
    private static final long serialVersionUID = -7046290354423141576L;

    public ExpensePage(PageParameters parameters) {
        super(parameters);

        AddExpensePanel dialog = new AddExpensePanel("dialog");
        add(dialog);
        WebMarkupContainer wmc = new WebMarkupContainer("open");
        dialog.addOpenerAttributesTo(wmc);
        add(wmc);
        add(new ViewExpensesPanel("list"));
    }
}
