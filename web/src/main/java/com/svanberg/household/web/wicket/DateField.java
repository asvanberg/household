package com.svanberg.household.web.wicket;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Date;

/**
 * A wicket form component for use with {@code &lt;input type="date" /&gt;}.
 *
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class DateField extends FormComponent<Date> {
    private static final long serialVersionUID = -7887015748957963947L;

    public DateField(String id) {
        this(id, new Model<Date>());
    }

    public DateField(String id, IModel<Date> model) {
        super(id, model);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        checkComponentTag(tag, "input");
        checkComponentTagAttribute(tag, "type", "date");

        tag.put("value", getValue());

        super.onComponentTag(tag);
    }
}
