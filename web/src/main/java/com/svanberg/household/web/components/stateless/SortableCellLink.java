package com.svanberg.household.web.components.stateless;

import org.apache.wicket.markup.html.link.StatelessLink;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableCellLink extends StatelessLink<Void>
{
    private static final long serialVersionUID = 1274981167864507968L;

    /**
     * Construct.
     *
     * @param id {@inheritDoc}
     */
    public SortableCellLink(final String id)
    {
        super(id);
    }

    @Override
    public void onClick()
    {
        // Noop, sorting done via page-parameters
    }

    @Override
    protected CharSequence getURL()
    {
        return super.getURL();
    }
}
