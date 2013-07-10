package com.svanberg.household.web.components.stateless;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * A stateless table header toolbar with sorting support via page parameters.
 *
 * @param <S> type of sorting parameters
 *
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableHeadersToolbar<S> extends HeadersToolbar<S> implements IPageParameterSorting<S>
{
    private static final long serialVersionUID = 5439576634619819790L;

    /**
     * Default page parameter key for the sorting value.
     *
     * @see #getSortParameter()
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
        super(table, sortStateLocator);

        this.sortStateLocator = sortStateLocator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sort()
    {
        String sortingValue = getPage().getPageParameters().get(getSortParameter()).toOptionalString();
        if (sortingValue != null)
        {
            S sortProperty = decodeSortProperty(sortingValue);
            SortOrder sortOrder = decodeSortOrder(sortingValue);
            sortStateLocator.getSortState().setPropertySortOrder(sortProperty, sortOrder);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSortParameter()
    {
        return SORTING_PAGE_PARAMETER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSortValue(final S sortProperty, final SortOrder order)
    {
        return (order == SortOrder.DESCENDING ? "!" : "") + sortProperty;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public S decodeSortProperty(final String sortingValue)
    {
        return (S) (sortingValue.startsWith("!") ? sortingValue.substring(1) : sortingValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortOrder decodeSortOrder(final String sortingValue)
    {
        return sortingValue.startsWith("!") ? SortOrder.DESCENDING : SortOrder.ASCENDING;
    }

    @Override
    protected WebMarkupContainer newSortableHeader(final String headerId, final S property, final ISortStateLocator<S> locator)
    {
        return new SortableCellBorder<>(headerId, property, locator.getSortState(), this);
    }
}
