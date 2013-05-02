package com.svanberg.household.web.components.stateless;

import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * A stateless link for sorting. Uses page parameters to specify the sorting.
 * It is up to the owner of the link to perform the actual sorting from the
 * available page parameters.
 *
 * @param <S> the type of sorting property
 *
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableCellLink<S> extends StatelessLink<Void>
{
    private static final long serialVersionUID = 1274981167864507968L;

    private final S sortProperty;
    private final SortOrder order;
    private final IPageParameterSorting<S> pageParameterSorting;

    /**
     * Construct.
     *
     * @param id {@inheritDoc}
     * @param sortProperty         property this link should sort by
     * @param order                order to sort by
     * @param pageParameterSorting provider for sorting page parameters
     */
    public SortableCellLink(final String id, S sortProperty, SortOrder order, IPageParameterSorting<S> pageParameterSorting)
    {
        super(id);
        this.sortProperty = sortProperty;
        this.order = order;
        this.pageParameterSorting = pageParameterSorting;
    }

    /**
     * No-op.
     */
    @Override
    public void onClick()
    {
        // Noop, sorting done via page-parameters
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CharSequence getURL()
    {
        return urlFor(getPageClass(), createPageParameters());
    }

    /**
     * Returns the page that the sorting link points to. Defaults to the
     * current page.
     *
     * @return page the link points to
     */
    protected Class<? extends Page> getPageClass()
    {
        return getPage().getPageClass();
    }

    /**
     * Creates the page parameters to append on this link. Will include all
     * page parameters already present plus the sorting parameter.
     *
     * @return page parameters with sorting added
     */
    protected PageParameters createPageParameters()
    {
        PageParameters pp = new PageParameters(getPage().getPageParameters());
        pp.set(pageParameterSorting.getSortParameter(), pageParameterSorting.getSortValue(sortProperty, order));
        return pp;
    }
}
