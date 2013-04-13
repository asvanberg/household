package com.svanberg.household.web.wicket;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * A wicket component for use with {@code &lt;input type="number" /&gt;}.
 *
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class NumberField<N extends Number> extends FormComponent<N> {
    private static final long serialVersionUID = 3598569202515365370L;

    public NumberField(String id) {
        this(id, new Model<N>());
    }

    public NumberField(String id, IModel<N> model) {
        super(id, model);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        checkComponentTag(tag, "input");
        checkComponentTagAttribute(tag, "type", "number");

        tag.put("value", getValue());

        super.onComponentTag(tag);
    }
}
