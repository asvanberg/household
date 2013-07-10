package com.svanberg.household.web.components.stateless;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.INamedParameters;

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
    public static final String DESCENDING_PREFIX = "!";

    private final ISortStateLocator<S> sortStateLocator;
    private final Class<S> sortType;
    private final INamedParameters parameters;

    public SortableHeadersToolbar(DataTable<?, S> table, ISortStateLocator<S> sortStateLocator, INamedParameters parameters)
    {
        this(table, sortStateLocator, parameters, null);
    }

    public SortableHeadersToolbar(
            DataTable<?, S> table,
            ISortStateLocator<S> sortStateLocator,
            INamedParameters parameters,
            Class<S> sortType)
    {
        super(table, sortStateLocator);

        this.sortStateLocator = sortStateLocator;
        this.sortType = sortType;
        this.parameters = parameters;

        sort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sort()
    {
        String sortingValue = parameters.get(getSortParameter()).toOptionalString();
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
        return (order == SortOrder.DESCENDING ? DESCENDING_PREFIX : "") +
                getConverter(sortType).convertToString(sortProperty, getLocale());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public S decodeSortProperty(final String sortingValue)
    {
        String sort = sortingValue.startsWith(DESCENDING_PREFIX) ? sortingValue.substring(1) : sortingValue;
        return getConverter(sortType).convertToObject(sort, getLocale());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortOrder decodeSortOrder(final String sortingValue)
    {
        return sortingValue.startsWith(DESCENDING_PREFIX) ? SortOrder.DESCENDING : SortOrder.ASCENDING;
    }

    @Override
    protected WebMarkupContainer newSortableHeader(final String headerId, final S property, final ISortStateLocator<S> locator)
    {
        return new SortableCellBorder<>(headerId, property, locator.getSortState(), this);
    }
}
