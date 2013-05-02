package com.svanberg.household.web.components.stateless;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableHeadersToolbar extends AbstractToolbar
{
    private static final long serialVersionUID = 5439576634619819790L;

    public SortableHeadersToolbar(final DataTable<?, ?> table)
    {
        super(table);
    }
}
