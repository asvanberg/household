package com.svanberg.household.web.components.stateless;

import com.svanberg.household.web.test.WicketTest;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableHeadersToolbarTest extends WicketTest
{
    @Mock DataTable<String, String> table;

    @Before
    public void setUp() throws Exception
    {
        tester().startComponentInPage(new SortableHeadersToolbar(table));
    }

    @Test
    public void testRenders() throws Exception
    {
        assertNotNull("Does not render", table);
    }

    @Test
    public void testStateless() throws Exception
    {
        assertTrue("Is not stateless", table.isStateless());
    }
}
