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

    /**
     * Interface for providing this link with the proper sorting page parameter
     * values.
     *
     * @param <S> the type of sorting property
     */
    public static interface ISortingParametersProvider<S>
    {
        /**
         * The key in the pageparameter to store the sorting value in.
         *
         * @return the key for the sorting parameter
         */
        String getSortingParameter();

        /**
         * The value to use as the sorting parameter.
         *
         * @param sortProperty the property to sort by
         * @param order        the order to sort in
         * @return             the sorting parameter value
         */
        Object getSortingValue(S sortProperty, SortOrder order);
    }

    private final S sortProperty;
    private final SortOrder order;
    private final ISortingParametersProvider<S> sortingParametersProvider;

    /**
     * Construct.
     *
     * @param id {@inheritDoc}
     * @param sortProperty              the property this link should sort by
     * @param order                     the order to sort by
     * @param sortingParametersProvider the provider for sorting page params
     */
    public SortableCellLink(final String id, S sortProperty, SortOrder order, ISortingParametersProvider<S> sortingParametersProvider)
    {
        super(id);
        this.sortProperty = sortProperty;
        this.order = order;
        this.sortingParametersProvider = sortingParametersProvider;
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
        pp.set(sortingParametersProvider.getSortingParameter(), sortingParametersProvider.getSortingValue(sortProperty, order));
        return pp;
    }
}
