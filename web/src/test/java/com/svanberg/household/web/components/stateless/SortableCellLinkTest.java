package com.svanberg.household.web.components.stateless;

import com.svanberg.household.web.test.WicketTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableCellLinkTest extends WicketTest
{
    private SortableCellLink link;

    @Before
    public void setUp()
    {
        link = tester().startComponentInPage(SortableCellLink.class);
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
}
