package com.svanberg.household.web.expense;

import com.svanberg.household.domain.Category;
import com.svanberg.household.domain.Expense;
import com.svanberg.household.service.CategoryService;
import com.svanberg.household.service.ExpenseService;
import com.svanberg.household.web.components.EntityModel;

import de.agilecoders.wicket.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.markup.html.bootstrap.form.BootstrapForm;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;

import java.util.Date;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class AddExpensePanel extends Modal {
    private static final long serialVersionUID = 8253264659998445704L;

    @SpringBean private ExpenseService expenseService;
    @SpringBean private CategoryService categoryService;

    private final IModel<Date> date = Model.of(new Date());
    private final IModel<Integer> cost = new Model<>();
    private final IModel<String> description = new Model<>();
    private final IModel<Category> category = new EntityModel<>(Category.class);

    private BootstrapForm<Void> form;

    public AddExpensePanel(String id) {
        super(id);

        header(new ResourceModel("title"));
        setFadeIn(false);

        addForm();

        addDate();
        addDescription();
        addCost();
        addCategory();

        addFeedback();

        addCreateButton(new ResourceModel("create"));
        addCloseButton(new ResourceModel("cancel"));
    }

    private void addFeedback() {
        NotificationPanel feedback = new NotificationPanel(FEEDBACK);
        feedback.hideAfter(Duration.seconds(10));
        form.add(feedback);
    }

    private void addCreateButton(final IModel<String> label) {
        Component button = new Label(BUTTON_MARKUP_ID, label);
        button.add(new ButtonBehavior(Buttons.Type.Primary));
        button.add(new AjaxFormSubmitBehavior(form, "onclick") {
            @Override
            protected void onAfterSubmit(final AjaxRequestTarget target) {
                appendCloseDialogJavaScript(target);
            }

            @Override
            protected void onError(final AjaxRequestTarget target) {
                target.add(getForm());
            }
        });
        addButton(button);
    }

    private void addCategory() {
        DropDownChoice<Category> category = new DropDownChoice<>(CATEGORY, this.category, categoryService.findAll(),
                new IChoiceRenderer<Category>() {
            @Override
            public Object getDisplayValue(final Category object) {
                return object.getName();
            }

            @Override
            public String getIdValue(final Category object, final int index) {
                return String.valueOf(object.getIdentifier());
            }
        });
        category.setNullValid(true);
        form.add(category);
    }

    private void addCost() {
        TextField<Integer> cost = new TextField<Integer>(COST, this.cost, Integer.class) {
            @Override
            protected String getInputType() {
                return "number";
            }
        };
        cost.setRequired(true);
        form.add(cost);
    }

    private void addDescription() {
        TextArea<String> description = new TextArea<>(DESCRIPTION, this.description);
        description.setRequired(true);
        form.add(description);
    }

    private void addDate() {
        DateTextField date = new DateTextField(DATE, this.date) {
            @Override
            protected String getInputType() {
                return "date";
            }
        };
        date.setRequired(true);
        form.add(date);
    }

    private void addForm() {
        form = new BootstrapForm<Void>(FORM) {
            @Override
            protected void onSubmit() {
                Expense expense = expenseService.create(
                        date.getObject(),
                        description.getObject(),
                        cost.getObject());
                expenseService.setCategory(expense, category.getObject());
            }
        };
        form.setOutputMarkupId(true);
        add(form);
    }

    static final String FORM = "form";
    static final String DATE = "date";
    static final String COST = "cost";
    static final String DESCRIPTION = "description";
    static final String CATEGORY = "category";
    static final String FEEDBACK = "feedback";
}
