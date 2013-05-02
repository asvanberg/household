package com.svanberg.household.web.components.stateless;

import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInitializationListener;

/**
 * Component initialization listener that automatically tells all components
 * that implement {@link IPageParameterSorting} to sort themselves.
 *
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class PageParameterSorter implements IComponentInitializationListener
{
    @Override
    public void onInitialize(final Component component)
    {
        if (component instanceof IPageParameterSorting)
        {
            IPageParameterSorting<?> sortingComponent = (IPageParameterSorting<?>) component;
            sortingComponent.sort();
        }
    }
}
