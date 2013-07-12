package com.svanberg.household.web.expense;

import com.svanberg.household.domain.Category;
import com.svanberg.household.domain.Expense;
import com.svanberg.household.service.CategoryService;
import com.svanberg.household.service.ExpenseService;
import com.svanberg.household.web.components.EntityModel;
import com.svanberg.household.web.components.InlineControlGroup;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Date;
import java.util.List;

public class AddExpenseDialog extends Modal
{
    private static final long serialVersionUID = -3888489317063097931L;

    @SpringBean private CategoryService categoryService;
    @SpringBean private ExpenseService expenseService;

    private final IModel<Date> date = new Model<>(new Date());
    private final IModel<Integer> cost = new Model<>();
    private final IModel<String> description = new Model<>();
    private final IModel<Category> category = new EntityModel<>(Category.class);

    public AddExpenseDialog(final String id)
    {
        super(id);

        header(new ResourceModel("title"));

        ExpenseForm form = new ExpenseForm(FORM);
        add(form);

        SubmitLink create = new SubmitLink(BUTTON_MARKUP_ID, form);
        create.setBody(new ResourceModel("create"));
        create.add(new ButtonBehavior(Buttons.Type.Primary));
        addButton(create);

        // addClosebutton() is not stateless and cant be used
        Label close = new Label(BUTTON_MARKUP_ID, new ResourceModel("cancel"));
        close.add(new ButtonBehavior(Buttons.Type.Default));
        close.add(new AttributeModifier("data-dismiss", "modal"));
        addButton(close);
    }

    private IModel<? extends List<? extends Category>> getCategories()
    {
        return new LoadableDetachableModel<List<? extends Category>>()
        {
            @Override
            protected List<? extends Category> load()
            {
                return categoryService.findAll();
            }
        };
    }

    private class ExpenseForm extends StatelessForm<Void>
    {
        private static final long serialVersionUID = 9103097394820856360L;

        public ExpenseForm(final String id)
        {
            super(id);

            add(new FormBehavior(FormType.Horizontal));

            DateTextField dateInput = new DateTextField(INPUT, date) {
                @Override
                protected String getInputType()
                {
                    return "date";
                }
            };
            dateInput.setRequired(true);
            addGroup(DATE, dateInput);

            TextField<Integer> costInput = new NumberTextField<>(INPUT, cost, Integer.class);
            costInput.setRequired(true);
            addGroup(COST, costInput);

            TextArea<String> descriptionInput = new TextArea<>(INPUT, description);
            descriptionInput.setRequired(true);
            addGroup(DESCRIPTION, descriptionInput);

            DropDownChoice<Category> categoryChoice = new DropDownChoice<>(INPUT, category, getCategories(),
                    new ChoiceRenderer<>("name"));
            categoryChoice.setNullValid(true);
            addGroup(CATEGORY, categoryChoice);
        }

        private void addGroup(String id, FormComponent<?> dateInput)
        {
            add(new InlineControlGroup(id, new ResourceModel(id))
                    .add(dateInput));
        }

        @Override
        protected void onSubmit()
        {
            Expense expense = expenseService.create(date.getObject(), description.getObject(), cost.getObject());
            expenseService.setCategory(expense, category.getObject());
            clearInput();
            description.setObject(null);
            cost.setObject(null);
            category.setObject(null);
        }

        @Override
        protected void onError()
        {
            show(true);
            setFadeIn(false);
        }
    }

    // Wicket component ids
    static final String FORM = "form";
    static final String DATE = "date";
    static final String COST = "cost";
    static final String DESCRIPTION = "description";
    static final String CATEGORY = "category";
    static final String INPUT = "input";
}
