package com.svanberg.household.web.category;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class AddCategoryPanel extends Panel {
    private static final long serialVersionUID = 7362182798476289232L;

    public AddCategoryPanel(final String id) {
        super(id);

        Form<Void> form = new Form<>(FORM);
        add(form);

        form.add(new TextField<>(NAME));
        form.add(new TextArea<>(DESCRIPTION));
    }

    static final String FORM = "form";
    static final String NAME = "name";
    static final String DESCRIPTION = "description";
}
