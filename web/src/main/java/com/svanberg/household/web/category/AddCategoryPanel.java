package com.svanberg.household.web.category;

import com.svanberg.household.service.CategoryService;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class AddCategoryPanel extends Panel {
    private static final long serialVersionUID = 7362182798476289232L;

    @SpringBean CategoryService categoryService;

    private final TextField<String> name;
    private final TextArea<String> description;

    public AddCategoryPanel(final String id) {
        super(id);

        Form<Void> form = new Form<Void>(FORM) {
            @Override
            protected void onSubmit() {
                categoryService.create(
                        name.getModelObject(),
                        description.getModelObject());
            }
        };
        add(form);

        name = new TextField<>(NAME, new Model<String>());
        description = new TextArea<>(DESCRIPTION, new Model<String>());

        form.add(name);
        form.add(description);
    }

    static final String FORM = "form";
    static final String NAME = "name";
    static final String DESCRIPTION = "description";
}
