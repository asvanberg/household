package com.svanberg.household.web.components;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class StatelessHeadersToolbar extends AbstractToolbar
{
    private static final long serialVersionUID = -5035009554907089080L;

    public StatelessHeadersToolbar(final DataTable<?, ?> table)
    {
        super(table);

        ListView<? extends IColumn<?,?>> headers = new ListView<IColumn<?, ?>>(HEADERS, table.getColumns())
        {
            @Override
            protected void populateItem(final ListItem<IColumn<?, ?>> item)
            {
                item.add(item.getModelObject().getHeader(HEADER));
            }
        };

        add(headers);
    }

    static final String HEADERS = "headers";
    static final String HEADER = "header";
}
