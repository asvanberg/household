package com.svanberg.household.web.components.stateless;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.markup.html.border.Border;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableCellBorder extends Border
{
    private static final long serialVersionUID = -3903421327963551000L;

    public <S> SortableCellBorder(final String id, S sortProperty, SortOrder sortOrder, SortableCellLink.ISortingParametersProvider<S> sortingParametersProvider)
    {
        super(id);

        addToBorder(new SortableCellLink<>(LINK, sortProperty, sortOrder, sortingParametersProvider));
    }

    public static final String LINK = "link";
}
