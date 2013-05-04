package com.svanberg.household.web.expense;

import com.svanberg.household.domain.Expense;
import com.svanberg.household.service.ExpenseService;
import com.svanberg.household.web.pages.HouseholdPage;

import org.apache.wicket.devutils.stateless.StatelessComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@StatelessComponent
public class ExpensePage extends HouseholdPage {
    private static final long serialVersionUID = -7046290354423141576L;

    private @SpringBean ExpenseService expenseService;

    private final ExpenseTable table;

    public ExpensePage(PageParameters parameters) {
        super(parameters);

        StatelessAddExpensePanel dialog = new StatelessAddExpensePanel("dialog");
        add(dialog);
        WebMarkupContainer wmc = new WebMarkupContainer("open");
        dialog.addOpenerAttributesTo(wmc);
        add(wmc);
        table = new ExpenseTable("list");
        table.setOutputMarkupId(true);
        add(table);

        add(new SubmitLink("delete", table.getForm()) {
            @Override
            public void onSubmit()
            {
                for (IModel<Expense> model : table.getSelection())
                {
                    expenseService.delete(model.getObject());
                }

                setResponsePage(getPageClass(), getPageParameters());
            }
        });

        int total = expenseService.totalExpenses();
        long count = expenseService.count();
        add(new Label("total", total));
        add(new Label("count", count));
        add(new Label("average", count == 0 ? 0 : total / (double) count));
        add(new Label("weekly", expenseService.averageWeeklyExpenses()));
    }
}
