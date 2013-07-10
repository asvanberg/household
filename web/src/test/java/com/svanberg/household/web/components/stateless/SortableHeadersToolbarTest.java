package com.svanberg.household.web.components.stateless;

import com.svanberg.household.web.test.WicketTest;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.data.IDataProvider;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SortableHeadersToolbarTest extends WicketTest
{
    @Mock IDataProvider<String> provider;
    @Mock ISortStateLocator<String> sortStateLocator;
    @Mock DataTable<String, String> table; // cant actually use the mock since getColumns() is final

    private SortableHeadersToolbar toolbar;

    @Before
    public void setUp() throws Exception
    {
        IColumn<String, String> column = mockColumn();
        startToolbar(Arrays.asList(column));
    }

    private void startToolbar(final List<IColumn<String, String>> columns)
    {
        table = new DataTable<>("id", columns, provider, 10L);
        toolbar = tester().startComponentInPage(new SortableHeadersToolbar<>(table, sortStateLocator));
    }

    @Test
    public void testRenders() throws Exception
    {
        assertNotNull("Does not render", toolbar);
    }

    @Test
    public void testStateless() throws Exception
    {
        assertTrue("Is not stateless", toolbar.isStateless());
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
        return column;
    }
}
