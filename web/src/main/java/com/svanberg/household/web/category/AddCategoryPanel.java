package com.svanberg.household.web.category;

import com.svanberg.household.service.CategoryService;
import com.svanberg.household.web.components.InlineControlGroup;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class AddCategoryPanel extends Border {
    private static final long serialVersionUID = 7362182798476289232L;

    @SpringBean private CategoryService categoryService;

    private final IModel<String> name = new Model<>();
    private final IModel<String> description = new Model<>();

    public AddCategoryPanel(final String id) {
        super(id);

        setRenderBodyOnly(true);

        addToBorder(new AddCategoryForm());
    }

    private class AddCategoryForm extends BootstrapForm<Void>
    {
        private static final long serialVersionUID = 8804875825503550624L;

        public AddCategoryForm() {
            super(FORM);

            type(FormType.Horizontal);

            InlineControlGroup nameGroup = new InlineControlGroup(NAME_GROUP, new ResourceModel("name"));
            TextField<String> name = new TextField<>(NAME, AddCategoryPanel.this.name);
            add(nameGroup.add(name.setRequired(true)));

            InlineControlGroup descGroup = new InlineControlGroup(DESCRIPTION_GROUP, new ResourceModel("description"));
            TextArea<String> desc = new TextArea<>(DESCRIPTION, AddCategoryPanel.this.description);
            add(descGroup.add(desc));
        }

        @Override
        protected void onSubmit() {
            categoryService.create(name.getObject(), description.getObject());
        }
    }

    static final String FORM = "form";
    static final String NAME = "name";
    static final String NAME_GROUP = "name_group";
    static final String DESCRIPTION = "description";
    static final String DESCRIPTION_GROUP = "desc_group";
}
