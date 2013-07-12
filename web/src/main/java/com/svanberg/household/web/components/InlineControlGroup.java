package com.svanberg.household.web.components;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.ControlGroup;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

public class InlineControlGroup extends ControlGroup {
    private static final long serialVersionUID = 8478883260796237101L;
    private static final AttributeModifier INLINE = new AttributeModifier("class", "help-inline");

    public InlineControlGroup(String id, IModel<String> label) {
        super(id, label);
    }

    @Override
    protected Component newFeedbackMessageContainer(String id)
    {
        Component component = super.newFeedbackMessageContainer(id);
        component.add(INLINE);
        return component;
    }

    @Override
    protected Component newHelpLabel(String id, IModel<String> model)
    {
        Component component = super.newHelpLabel(id, model);
        component.add(INLINE);
        return component;
    }
}
