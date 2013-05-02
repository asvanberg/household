package com.svanberg.household.web.components;

import com.svanberg.household.web.test.WicketTest;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.EmptyDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class StatelessHeadersToolbarTest extends WicketTest
{

    private StatelessHeadersToolbar headersToolbar;

    @Before
    public void setUp()
    {
        MockColumn c1 = new MockColumn();
        MockColumn c2 = new MockColumn();

        List<MockColumn> columns = Arrays.asList(c1, c2);

        headersToolbar = tester().startComponentInPage(new StatelessHeadersToolbar(new MockTable(columns)));
    }

    @Test
    public void testRenders()
    {
        assertNotNull("Does not render", headersToolbar);
    }

    @Test
    public void testStateless()
    {
        assertTrue("Is not stateless", headersToolbar.isStateless());
    }

    public class MockTable extends DataTable<String, String>
    {
        public MockTable(List<MockColumn> columns)
        {
            super("id", columns, new EmptyDataProvider<String>(), 10L);
        }
    }

    private class MockColumn extends AbstractColumn<String, String>
    {
        public MockColumn()
        {
            super(Model.of("Header"), "class");
        }

        @Override
        public void populateItem(final Item<ICellPopulator<String>> cellItem, final String componentId, final IModel<String> rowModel)
        {
            //noop
        }
    }
}
