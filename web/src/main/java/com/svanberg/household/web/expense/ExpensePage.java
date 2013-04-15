package com.svanberg.household.web.expense;

import com.svanberg.household.web.wicket.pages.HouseholdPage;

import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class ExpensePage extends HouseholdPage {
    private static final long serialVersionUID = -7046290354423141576L;

    public ExpensePage(PageParameters parameters) {
        super(parameters);

        add(new AddExpensePanel("add"));
        add(new ViewExpensesPanel("list"));
    }
}
