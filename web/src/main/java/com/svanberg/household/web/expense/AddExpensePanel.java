package com.svanberg.household.web.expense;

import com.svanberg.household.domain.Category;
import com.svanberg.household.domain.Expense;
import com.svanberg.household.service.CategoryService;
import com.svanberg.household.service.ExpenseService;
import com.svanberg.household.web.wicket.EntityModel;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.*;
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
    @SpringBean private CategoryService categoryService;

    private final DateTextField date;
    private final TextField<Integer> cost;
    private final TextArea<String> description;
    private final DropDownChoice<Category> category;

    public AddExpensePanel(String id) {
        super(id);

        Form<Void> form = new Form<Void>(FORM) {
            @Override
            protected void onSubmit() {
                Expense expense = expenseService.create(
                        date.getModelObject(),
                        description.getModelObject(),
                        cost.getModelObject());
                expenseService.setCategory(expense, category.getModelObject());
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
        category = new DropDownChoice<>(CATEGORY, new EntityModel<>(Category.class), categoryService.findAll(), new IChoiceRenderer<Category>() {
            @Override
            public Object getDisplayValue(final Category object) {
                return object.getName();
            }

            @Override
            public String getIdValue(final Category object, final int index) {
                return String.valueOf(object.getIdentifier());
            }
        });

        form.add(date);
        form.add(cost);
        form.add(description);
        form.add(category);
    }

    static final String FORM = "form";
    static final String DATE = "date";
    static final String COST = "cost";
    static final String DESCRIPTION = "description";
    static final String CATEGORY = "category";
}
