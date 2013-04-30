package com.svanberg.household.web.components;

import com.svanberg.household.web.test.WicketTest;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class StatelessPagingNavigatorTest extends WicketTest
{
    private StatelessPagingNavigator statelessPagingNavigator;

    @Before
    public void setUp()
    {
        statelessPagingNavigator = tester().startComponentInPage(new StatelessPagingNavigator("id", new MockPageable()));
    }

    @Test
    public void testRenders()
    {
        assertNotNull("Does not render", statelessPagingNavigator);
    }

    @Test
    public void testStateless()
    {
        assertTrue("Is not stateless", statelessPagingNavigator.isStateless());
    }

    private static class MockPageable implements IPageable
    {
        private static final long serialVersionUID = 8134235812881164697L;

        private long page = 0;

        @Override
        public long getCurrentPage()
        {
            return page;
        }

        @Override
        public void setCurrentPage(final long page)
        {
            this.page = page;
        }

        @Override
        public long getPageCount()
        {
            return 4;
        }
    }
}
