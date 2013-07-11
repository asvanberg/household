package com.svanberg.household.web.components.stateless;

import com.svanberg.household.web.test.WicketTest;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableHeadersToolbarTest extends WicketTest
{
    @Mock IDataProvider<String> provider;
    @Mock ISortState<String> sort;
    @Mock DataTable<String, String> table; // cant actually use the mock since getColumns() is final

    private SortableHeadersToolbar toolbar;

    @Before
    public void setUp() throws Exception
    {
        IColumn<String, String> column = mockColumn();
        startToolbar(Arrays.asList(column), new PageParameters());
    }

    @Test
    public void testRenders() throws Exception
    {
        assertNotNull("Does not render", toolbar);
    }

    @Test
    public void testStateless() throws Exception
    {
        Component statefulComponent = tester().getLastRenderedPage().visitChildren(Component.class,
                new IVisitor<Component, Component>()
                {
                    @Override
                    public void component(final Component component, final IVisit<Component> visit)
                    {
                        if (!component.isStateless())
                        {
                            visit.stop(component);
                        }
                    }
                });
        boolean stateless = statefulComponent == null;

        assertTrue("Is not stateless", stateless);
    }

    @Test
    public void testAscendingSort() throws Exception
    {
        String property = "date";

        startToolbarWithSort(property);

        verify(sort).setPropertySortOrder(property, SortOrder.ASCENDING);
    }

    @Test
    public void testDescendingSort() throws Exception
    {
        String property = "date";

        startToolbarWithSort(SortableHeadersToolbar.DESCENDING_PREFIX + property);

        verify(sort).setPropertySortOrder(property, SortOrder.DESCENDING);
    }

    @Test
    public void testNoSort() throws Exception
    {
        startToolbarWithSort(null);

        verify(sort, never()).setPropertySortOrder(anyString(), any(SortOrder.class));
    }


    private void startToolbar(List<IColumn<String, String>> columns, PageParameters parameters)
    {
        ISortStateLocator<String> sortStateLocator = new ISortStateLocator<String>()
        {
            @Override
            public ISortState<String> getSortState()
            {
                return sort;
            }
        };
        table = new DataTable<>("id", columns, provider, 10L);
        toolbar = tester().startComponentInPage(new SortableHeadersToolbar<>(table, sortStateLocator, parameters));
    }

    private void startToolbarWithSort(String property)
    {
        PageParameters parameters = new PageParameters();
        parameters.set(SortableHeadersToolbar.SORTING_PAGE_PARAMETER, property);

        startToolbar(Arrays.asList(mockColumn()), parameters);
    }

    private IColumn<String, String> mockColumn()
    {
        @SuppressWarnings("unchecked")
        IColumn<String, String> column = mock(IColumn.class);
        when(column.getHeader(isA(String.class))).then(new Answer<Object>()
        {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable
            {
                return new WebMarkupContainer((String) invocation.getArguments()[0]);
            }
        });
        when(column.isSortable()).thenReturn(true);
        return column;
    }
}
