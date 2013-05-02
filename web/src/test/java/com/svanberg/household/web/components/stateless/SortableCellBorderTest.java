package com.svanberg.household.web.components.stateless;

import com.svanberg.household.web.test.WicketTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableCellBorderTest extends WicketTest
{

    private SortableCellBorder border;

    @Before
    public void setUp() throws Exception
    {
        border = tester().startComponentInPage(SortableCellBorder.class);
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
}
