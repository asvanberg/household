package com.svanberg.household.web.components.stateless;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.StatelessLink;

/**
 * A border to be used in table headers to decorate the header with a sorting
 * link.
 *
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableCellBorder<S> extends Border
{
    private static final long serialVersionUID = -3903421327963551000L;

    /**
     * Construct.
     *
     * @param id                   {@inheritDoc}
     * @param sortProperty         property to sort by
     * @param sortState            current sort state
     * @param pageParameterSorting provider for sorting page parameters
     */
    public SortableCellBorder(final String id, S sortProperty, ISortState<S> sortState, IPageParameterSorting<S> pageParameterSorting)
    {
        super(id);

        addToBorder(newSortableLink(sortProperty, sortState, pageParameterSorting));
    }

    /**
     * Factory method for the sorting link.
     *
     * @param sortProperty         property the link should sort by
     * @param sortState            current sort state
     * @param pageParameterSorting provider for sorting page parameters
     * @return
     */
    protected StatelessLink<?> newSortableLink(final S sortProperty, final ISortState<S> sortState, final IPageParameterSorting<S> pageParameterSorting)
    {
        return new SortableCellLink<>(LINK, sortProperty, nextOrder(sortProperty, sortState), pageParameterSorting);
    }

    /**
     * Calculates the next order to enable toggling.
     *
     * @param sortProperty    property to sort by
     * @param sortState       current sort state
     * @return the next sort order
     */
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

    public static final String LINK = "link";
}
