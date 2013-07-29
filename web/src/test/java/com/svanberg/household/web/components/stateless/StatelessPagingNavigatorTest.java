package com.svanberg.household.web.components.stateless;

import com.svanberg.household.web.test.WicketTest;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.svanberg.household.web.components.stateless.StatelessPagingNavigator.*;
import static com.svanberg.household.web.test.Assert.assertStateless;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@RunWith(MockitoJUnitRunner.class)
public class StatelessPagingNavigatorTest extends WicketTest
{
    public static final long CURRENT_PAGE = 2L;
    public static final long PAGE_COUNT = 4L;

    @Mock
    private IPageable pageable;

    private StatelessPagingNavigator statelessPagingNavigator;

    @Before
    public void setUp()
    {
        when(pageable.getCurrentPage()).thenReturn(CURRENT_PAGE);
        when(pageable.getPageCount()).thenReturn(PAGE_COUNT);
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

        PageParameters parameters = new PageParameters();
        parameters.set(PAGING_PAGE_PARAMETER, page);

        start(parameters);

        verify(pageable).setCurrentPage(page);
    }

    @Test
    public void first_page_link() throws Exception
    {
        start();

        assertLinksToPage(path("first"), 0);
    }

    @Test
    public void previous_page_link() throws Exception
    {
        start();

        assertLinksToPage(path("prev"), CURRENT_PAGE - 1);
    }

    @Test
    public void specific_page_link() throws Exception
    {
        int page = 1;

        start();

        assertLinksToPage(path("navigation", page, "pageLink"), page);
    }

    @Test
    public void next_page_link() throws Exception
    {
        start();

        assertLinksToPage(path("next"), CURRENT_PAGE + 1);
    }

    @Test
    public void last_page_link() throws Exception
    {
        start();

        assertLinksToPage(path("last"), PAGE_COUNT - 1);
    }

    private void assertLinksToPage(String linkPath, long page)
    {
        PageParameters parameters = new PageParameters();
        parameters.set(PAGING_PAGE_PARAMETER, page);

        @SuppressWarnings("unchecked")
        Class<? extends WebPage> pageClass =
                (Class<? extends WebPage>) tester().getLastRenderedPage().getPageClass();

        tester().assertBookmarkablePageLink(path(statelessPagingNavigator, linkPath), pageClass, parameters);
    }

    private void start()
    {
        start(new PageParameters());
    }

    private void start(PageParameters parameters)
    {
        statelessPagingNavigator = tester().startComponentInPage(new StatelessPagingNavigator("id", parameters, pageable));
    }
}
