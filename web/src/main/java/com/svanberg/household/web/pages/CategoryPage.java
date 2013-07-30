package com.svanberg.household.web.pages;

import com.svanberg.household.service.CategoryService;
import com.svanberg.household.web.components.InlineControlGroup;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.inject.Inject;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class CategoryPage extends HouseholdPage
{
    static final String FORM = "form";
    static final String NAME = "name";
    static final String DESCRIPTION = "description";
    static final String INPUT = "input";

    private @Inject CategoryService categoryService;

    private IModel<String> name = new Model<>();
    private IModel<String> description = new Model<>();

    public CategoryPage(PageParameters parameters)
    {
        super(parameters);

        add(new AddCategoryForm(CategoryPage.FORM));

    }

    private class AddCategoryForm extends StatelessForm<Void>
    {
        public AddCategoryForm(String id)
        {
            super(id);

            add(new FormBehavior(FormType.Horizontal));

            add(new InlineControlGroup(NAME, new ResourceModel("name"))
                    .add(new TextField<>(INPUT, name)
                            .setRequired(true)));
            add(new InlineControlGroup(DESCRIPTION, new ResourceModel("description"))
                    .add(new TextArea<>(INPUT, description)
                            .setRequired(true)));
        }

        @Override
        protected void onSubmit()
        {
            categoryService.create(name.getObject(), description.getObject());

            setResponsePage(CategoryPage.class);
        }
    }
}
