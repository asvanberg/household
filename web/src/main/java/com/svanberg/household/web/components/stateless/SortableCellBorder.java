package com.svanberg.household.web.components.stateless;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.markup.html.border.Border;

/**
 * A border to be used in table headers to decorate the header with a sorting
 * link.
 *
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableCellBorder extends Border
{
    private static final long serialVersionUID = -3903421327963551000L;

    /**
     * Construct.
     *
     * @param id                        {@inheritDoc}
     * @param sortProperty              property to sort by
     * @param sortOrder                 order to sort by
     * @param sortingParametersProvider provider for sorting page parameters
     * @param <S>                       type of the sorting parameter
     */
    public <S> SortableCellBorder(final String id, S sortProperty, SortOrder sortOrder, SortableCellLink.ISortingParametersProvider<S> sortingParametersProvider)
    {
        super(id);

        addToBorder(new SortableCellLink<>(LINK, sortProperty, sortOrder, sortingParametersProvider));
    }

    public static final String LINK = "link";
}
