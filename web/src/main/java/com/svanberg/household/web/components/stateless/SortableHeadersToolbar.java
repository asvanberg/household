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
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

/**
 * A stateless table header toolbar with sorting support via page parameters.
 *
 * @param <S> type of sorting parameters
 *
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableHeadersToolbar<S> extends AbstractToolbar implements SortableCellLink.ISortingParametersProvider<S>
{
    private static final long serialVersionUID = 5439576634619819790L;

    /**
     * Default page parameter key for the sorting value.
     *
     * @see #getSortingParameter()
     */
    public static final String SORTING_PAGE_PARAMETER = "sort";

    private final ISortStateLocator<S> sortStateLocator;

    /**
     * Construct.
     *
     * @param table {@inheritDoc}
     * @param sortStateLocator the locator whose state will be sorted
     */
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

    /**
     * Method responsible for doing the sorting.
     */
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

    /**
     * Returns the page parameter key for the sorting parameter.
     *
     * @return the page parameter key for the sorting parameter
     * @see #SORTING_PAGE_PARAMETER
     */
    @Override
    public String getSortingParameter()
    {
        return SORTING_PAGE_PARAMETER;
    }

    /**
     * Returns the sort value for the given property and order.
     *
     * @param sortProperty the property to sort by
     * @param order        the order to sort in
     * @return the sort value for the given property and order
     */
    @Override
    public Object getSortingValue(final S sortProperty, final SortOrder order)
    {
        return (order == SortOrder.DESCENDING ? "!" : "") + sortProperty;
    }

    /**
     * Decodes a sorting value and returns the property to sort by.
     *
     * @param sortingValue sorting value from the page parameters
     * @return the sorting property
     */
    @SuppressWarnings("unchecked")
    protected S getSortingProperty(final String sortingValue)
    {
        return (S) (sortingValue.startsWith("!") ? sortingValue.substring(1) : sortingValue);
    }

    /**
     * Decodes a sorting value and returns the order to sort by.
     *
     * @param sortingValue sorting value from the page parameters
     * @return the sorting order
     */
    protected SortOrder getSortingOrder(final String sortingValue)
    {
        return sortingValue.startsWith("!") ? SortOrder.DESCENDING : SortOrder.ASCENDING;
    }

    /**
     * Factory method for the cell border to use for decorating sortable column
     * headers.
     *
     * @param sortProperty sort property of the column
     * @param sortState    current sortstate
     * @return the decorating border
     */
    protected Border newSortableCell(final S sortProperty, final ISortState<S> sortState)
    {
        return new SortableCellBorder<>(CELL, sortProperty, sortState, SortableHeadersToolbar.this);
    }

    public static final String ROW = "row";
    public static final String CELL = "cell";
    public static final String HEADER = "header";
}
