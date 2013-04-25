package com.svanberg.household.web.components;

import de.agilecoders.wicket.markup.html.bootstrap.form.ControlGroup;
import org.apache.wicket.model.IModel;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class InlineControlGroup extends ControlGroup {
    private static final long serialVersionUID = 8478883260796237101L;

    public InlineControlGroup(String id, IModel<String> label) {
        super(id, label);
    }

    public InlineControlGroup(String id, IModel<String> label, IModel<String> help) {
        super(id, label, help);
    }
}
