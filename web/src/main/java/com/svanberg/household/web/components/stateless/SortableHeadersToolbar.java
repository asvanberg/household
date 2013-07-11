package com.svanberg.household.web.components.stateless;

import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.request.mapper.parameter.INamedParameters;

public class SortableHeadersToolbar<S> extends HeadersToolbar<S>
{
    private static final long serialVersionUID = 5439576634619819790L;

    public static final String SORTING_PAGE_PARAMETER = "sort";
    public static final String DESCENDING_PREFIX = "!";

    private final Class<S> sortType;

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

        this.sortType = sortType;

        sort(parameters, sortStateLocator);
    }

    private void sort(INamedParameters parameters, ISortStateLocator<S> locator)
    {
        String sortParameter = parameters.get(SORTING_PAGE_PARAMETER).toOptionalString();
        if (sortParameter != null)
        {
            S sortProperty = extractSortProperty(sortParameter);
            SortOrder sortOrder = extractSortOrder(sortParameter);
            locator.getSortState().setPropertySortOrder(sortProperty, sortOrder);
        }
    }

    private S extractSortProperty(String sortParameter)
    {
        String sortProperty = sortParameter.startsWith(DESCENDING_PREFIX) ? sortParameter.substring(1) : sortParameter;
        return getConverter(sortType).convertToObject(sortProperty, getLocale());
    }

    private SortOrder extractSortOrder(String sortParameter)
    {
        return sortParameter.startsWith(DESCENDING_PREFIX) ? SortOrder.DESCENDING : SortOrder.ASCENDING;
    }

    private String getSortParameter(S property, ISortStateLocator<S> locator)
    {
        String parameter = getConverter(sortType).convertToString(property, getLocale());
        SortOrder order = locator.getSortState().getPropertySortOrder(property);

        return (order == SortOrder.ASCENDING ? DESCENDING_PREFIX : "") + parameter;
    }

    protected ParameterLink newSortLink(String id, Class<? extends Page> page, String parameter, String value)
    {
        return new ParameterLink(id, parameter, value, page);
    }
    @Override
    protected WebMarkupContainer newSortableHeader(final String headerId, final S property, final ISortStateLocator<S> locator)
    {
        return new Header(headerId, property, locator);
    }

    protected Class<? extends Page> getPageClass()
    {
        return getPage().getPageClass();
    }

    private class Header extends Border
    {
        private static final long serialVersionUID = -3536225677977779161L;

        public Header(String id, S property, ISortStateLocator<S> locator)
        {
            super(id);

            addToBorder(newSortLink("link", getPageClass(), SORTING_PAGE_PARAMETER, getSortParameter(property, locator)));
        }
    }
}
