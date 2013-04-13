package com.svanberg.household.web.expense;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class AddExpensePanel extends Panel {
    private static final long serialVersionUID = 8253264659998445704L;

    public AddExpensePanel(String id) {
        super(id);

        add(new Form<Void>(FORM));
    }

    static final String FORM = "form";
}
