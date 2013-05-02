package com.svanberg.household.web.components.stateless;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
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
public class SortableHeadersToolbar<S> extends AbstractToolbar implements SortableCellLink.ISortingParametersProvider<S>
{
    private static final long serialVersionUID = 5439576634619819790L;

    public static final String SORTING_PAGE_PARAMETER = "sort";

    private final ISortStateLocator<S> sortStateLocator;

    public SortableHeadersToolbar(final DataTable<?, S> table, final ISortStateLocator<S> sortStateLocator)
    {
        super(table);
        this.sortStateLocator = sortStateLocator;

        add(new ListView<IColumn<?, S>>(ROW, table.getColumns()) {
            @Override
            protected void populateItem(final ListItem<IColumn<?, S>> item)
            {
                IColumn<?, S> column = item.getModelObject();

                WebMarkupContainer cell;
                if (column.isSortable())
                {
                    cell = newSortableCell(column.getSortProperty(), sortStateLocator.getSortState());
                }
                else
                {
                    cell = new WebMarkupContainer(CELL);
                }

                if (column instanceof IStyledColumn)
                {
                    IStyledColumn<?, ?> styledColumn = (IStyledColumn<?, ?>) column;
                    cell.add(new AttributeAppender("class", Model.of(styledColumn.getCssClass()), " "));
                }

                cell.add(column.getHeader(HEADER).setRenderBodyOnly(true));
                item.add(cell);
            }
        });
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        String sortingValue = getPage().getPageParameters().get(getSortingParameter()).toOptionalString();
        if (sortingValue != null)
        {
            S sortProperty = getSortingProperty(sortingValue);
            SortOrder sortOrder = getSortingOrder(sortingValue);
            sortStateLocator.getSortState().setPropertySortOrder(sortProperty, sortOrder);
        }
    }

    @Override
    public String getSortingParameter()
    {
        return SORTING_PAGE_PARAMETER;
    }

    @Override
    public Object getSortingValue(final S sortProperty, final SortOrder order)
    {
        return (order == SortOrder.DESCENDING ? "!" : "") + sortProperty;
    }

    @SuppressWarnings("unchecked")
    protected S getSortingProperty(final String sortingValue)
    {
        return (S) (sortingValue.startsWith("!") ? sortingValue.substring(1) : sortingValue);
    }

    protected SortOrder getSortingOrder(final String sortingValue)
    {
        return sortingValue.startsWith("!") ? SortOrder.DESCENDING : SortOrder.ASCENDING;
    }

    protected SortableCellBorder newSortableCell(final S sortProperty, final ISortState<S> sortState)
    {
        return new SortableCellBorder(CELL, sortProperty, nextOrder(sortProperty, sortState), SortableHeadersToolbar.this);
    }

    protected SortOrder nextOrder(final S sortProperty, final ISortState<S> sortState)
    {
        SortOrder order = sortState.getPropertySortOrder(sortProperty);

        if (order == SortOrder.NONE)
        {
            return SortOrder.ASCENDING;
        }
        else
        {
            return order == SortOrder.ASCENDING ? SortOrder.DESCENDING : SortOrder.ASCENDING;
        }
    }

    public static final String ROW = "row";
    public static final String CELL = "cell";
    public static final String HEADER = "header";
}
