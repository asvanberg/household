package com.svanberg.household.web.components.stateless;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;

/**
 * Interface for components that implement sorting via page parameters.
 *
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public interface IPageParameterSorting<S>
{
    /**
     * Returns the page parameter used to store the sort value.
     *
     * @return the page parameter used to store the sort value
     */
    String getSortParameter();

    /**
     * Returns the sort value for the given property and order.
     *
     * @param sortProperty property to sort by
     * @param sortOrder    order to sort by
     * @return the sort value
     */
    String getSortValue(S sortProperty, SortOrder sortOrder);

    /**
     * Decodes a sort value and extracts the property.
     *
     * @param sortValue the sort value from page parameters
     * @return the property
     * @see #getSortValue(S, SortOrder)
     */
    S decodeSortProperty(String sortValue);

    /**
     * Decodes a sort value and extracts the sort order.
     *
     * @param sortValue the sort value from page parameters
     * @return the sort order
     * @see #getSortValue(S, SortOrder)
     */
    SortOrder decodeSortOrder(String sortValue);

    /**
     * Sorting should be done in this method.
     *
     * Called by {@link PageParameterSorter} if it is registered as a
     * {@link org.apache.wicket.application.IComponentInitializationListener}
     * in the application, if it is not the component has to call the method
     * itself in {@link org.apache.wicket.Component#onInitialize()}.
     */
    void sort();
}
