package com.svanberg.household.web.expense;

import com.svanberg.household.service.ExpenseService;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Date;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class AddExpensePanel extends Panel {
    private static final long serialVersionUID = 8253264659998445704L;

    @SpringBean ExpenseService expenseService;

    private final DateTextField date;
    private final TextField<Integer> cost;
    private final TextArea<String> description;

    public AddExpensePanel(String id) {
        super(id);

        Form<Void> form = new Form<Void>(FORM) {
            @Override
            protected void onSubmit() {
                expenseService.addExpense(
                        date.getModelObject(),
                        description.getModelObject(),
                        cost.getModelObject());
            }
        };
        add(form);

        date = new DateTextField(DATE, new Model<Date>()) {
            @Override
            protected String getInputType() {
                return "date";
            }
        };
        description = new TextArea<>(DESCRIPTION, new Model<String>());
        cost = new TextField<Integer>(COST, new Model<Integer>(), Integer.class) {
            @Override
            protected String getInputType() {
                return "number";
            }
        };

        form.add(date);
        form.add(cost);
        form.add(description);
    }

    static final String FORM = "form";
    static final String DATE = "date";
    static final String COST = "cost";
    static final String DESCRIPTION = "description";
}
