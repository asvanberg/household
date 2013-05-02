package com.svanberg.household.web.components.stateless;

import com.svanberg.household.web.test.WicketTest;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableCellBorderTest extends WicketTest
{
    @Mock IPageParameterSorting<String> sortingParametersProvider;
    @Mock ISortState<String> sortState;

    private SortableCellBorder border;

    @Before
    public void setUp() throws Exception
    {
        when(sortingParametersProvider.getSortParameter()).thenReturn("sort");

        border = tester().startComponentInPage(new SortableCellBorder<>("id", "date", sortState, sortingParametersProvider));
    }

    @Test
    public void testRenders() throws Exception
    {
        assertNotNull("Does not render", border);
    }

    @Test
    public void testStateless() throws Exception
    {
        assertTrue("Is not stateless", border.isStateless());
    }

    @Test
    public void testContainsSortingLink() throws Exception
    {
        tester().assertComponent(path(border, SortableCellBorder.LINK), SortableCellLink.class);
    }
}
