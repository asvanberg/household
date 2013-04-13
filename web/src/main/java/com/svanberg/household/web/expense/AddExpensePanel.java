package com.svanberg.household.web.expense;

import com.svanberg.household.web.wicket.DateField;
import com.svanberg.household.web.wicket.NumberField;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import java.util.Date;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class AddExpensePanel extends Panel {
    private static final long serialVersionUID = 8253264659998445704L;

    private final FormComponent<Date> date;
    private final NumberField<Integer> cost;
    private final TextArea<String> description;

    public AddExpensePanel(String id) {
        super(id);

        Form<Void> form = new Form<>(FORM);
        add(form);

        date = new DateField(DATE);
        cost = new NumberField<>(COST);
        description = new TextArea<>(DESCRIPTION, new Model<String>());

        form.add(date);
        form.add(cost);
        form.add(description);
    }

    static final String FORM = "form";
    static final String DATE = "date";
    static final String COST = "cost";
    static final String DESCRIPTION = "description";
}
