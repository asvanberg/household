package com.svanberg.household.web.components.stateless;

import com.svanberg.household.web.test.WicketTest;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableCellLinkTest extends WicketTest
{
    @Mock IPageParameterSorting<String> pageParameterSorting;

    private SortableCellLink<String> link;

    private String sortProperty = "date";
    private SortOrder order = SortOrder.ASCENDING;

    @Before
    public void setUp()
    {
        when(pageParameterSorting.getSortParameter()).thenReturn("sort");

        link = tester().startComponentInPage(new SortableCellLink<>("id", sortProperty, order, pageParameterSorting));
    }

    @Test
    public void testRenders() throws Exception
    {
        assertNotNull("Does not render", link);
    }

    @Test
    public void testStateless() throws Exception
    {
        assertTrue("Is not stateless", link.isStateless());
    }

    @Test
    public void testCreatePageParameters() throws Exception
    {
        // given
        String key = "sort";
        String value = "value";

        when(pageParameterSorting.getSortParameter()).thenReturn(key);
        when(pageParameterSorting.getSortValue(eq(sortProperty), eq(order))).thenReturn(value);

        // when

        // then
        PageParameters pp = link.createPageParameters();

        assertTrue("Does not contain sort parameter", pp.getNamedKeys().contains(key));
        assertEquals("Contains wrong sort parameter value", value, pp.get(key).toString());
    }
}
