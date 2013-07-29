package com.svanberg.household.web.components.stateless;

import com.svanberg.household.web.test.WicketTest;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.svanberg.household.web.components.stateless.StatelessPagingNavigator.*;
import static com.svanberg.household.web.test.Assert.assertStateless;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@RunWith(MockitoJUnitRunner.class)
public class StatelessPagingNavigatorTest extends WicketTest
{
    private static final StringValue EMPTY_STRING = StringValue.valueOf((String) null);

    @Mock
    private PageParameters parameters;
    @Mock
    private IPageable pageable;

    private StatelessPagingNavigator statelessPagingNavigator;

    @Before
    public void setUp()
    {
        when(pageable.getCurrentPage()).thenReturn(2L);
        when(pageable.getPageCount()).thenReturn(4L);

        when(parameters.get(eq(PAGING_PAGE_PARAMETER))).thenReturn(EMPTY_STRING);
    }

    @Test
    public void testRenders()
    {
        start();
        assertNotNull("Does not render", statelessPagingNavigator);
    }

    @Test
    public void testStateless()
    {
        start();
        assertStateless(statelessPagingNavigator);
    }

    @Test
    public void testPagingParameter() throws Exception
    {
        long page = 1L;
        when(parameters.get(eq(PAGING_PAGE_PARAMETER))).thenReturn(StringValue.valueOf(page));

        start();

        verify(pageable).setCurrentPage(page);
    }

    private void start()
    {
        start(parameters, pageable);
    }

    private void start(PageParameters parameters, IPageable pageable)
    {
        statelessPagingNavigator = tester().startComponentInPage(new StatelessPagingNavigator("id", parameters, pageable));
    }
}
