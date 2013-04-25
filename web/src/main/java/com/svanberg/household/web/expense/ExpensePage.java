package com.svanberg.household.web.expense;

import com.svanberg.household.domain.Expense;
import com.svanberg.household.service.ExpenseService;
import com.svanberg.household.web.pages.HouseholdPage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Collection;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class ExpensePage extends HouseholdPage {
    private static final long serialVersionUID = -7046290354423141576L;

    private @SpringBean ExpenseService expenseService;

    private final ExpenseTable table;

    public ExpensePage(PageParameters parameters) {
        super(parameters);

        AddExpensePanel dialog = new AddExpensePanel("dialog");
        add(dialog);
        WebMarkupContainer wmc = new WebMarkupContainer("open");
        dialog.addOpenerAttributesTo(wmc);
        add(wmc);
        table = new ExpenseTable("list");
        table.setOutputMarkupId(true);
        add(table);

        AjaxFallbackLink<Void> delete = new AjaxFallbackLink<Void>("delete") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                Collection<Expense> selection = table.getSelection();
                expenseService.delete(selection);
                target.add(table);
            }
        };
        add(delete);
    }
}
