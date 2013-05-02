package com.svanberg.household.web.components;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class StatelessHeadersToolbar extends AbstractToolbar
{
    private static final long serialVersionUID = -5035009554907089080L;

    public StatelessHeadersToolbar(final DataTable<?, ?> table)
    {
        super(table);

        ListView<? extends IColumn<?,?>> headers = new ListView<IColumn<?, ?>>(ROW, table.getColumns())
        {
            @Override
            protected void populateItem(final ListItem<IColumn<?, ?>> item)
            {
                populate(item);
            }
        };

        add(headers);
    }

    private void populate(final ListItem<IColumn<?, ?>> item)
    {
        IColumn<?, ?> column = item.getModelObject();

        WebMarkupContainer cell = new WebMarkupContainer(CELL);

        if (column instanceof IStyledColumn) {
            IStyledColumn<?, ?> styledColumn = (IStyledColumn<?, ?>) column;
            cell.add(new AttributeAppender("class", Model.of(styledColumn.getCssClass()), " "));
        }

        cell.add(column.getHeader(HEADER));
        item.add(cell);
    }

    static final String ROW = "headers";
    static final String CELL = "cell";
    static final String HEADER = "header";
}
