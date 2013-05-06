package com.svanberg.household.web.expense;

import com.svanberg.household.domain.Expense;
import com.svanberg.household.service.ExpenseService;
import com.svanberg.household.web.test.SpringWicketTest;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class ExpenseTableTest extends SpringWicketTest
{

    @Mock ExpenseService expenseService;

    private ExpenseTable panel;

    @Before
    public void setUp() throws Exception {
        panel = tester().startComponentInPage(ExpenseTable.class);
    }

    @Test
    public void testRenders() throws Exception {
        assertNotNull("Does not render", panel);
    }

    @Test
    public void testDefaultsToDateSort() throws Exception
    {
        IDataProvider<Expense> provider = panel.getProvider();

        assertTrue("Table is not backed by a sortable provider", provider instanceof ISortableDataProvider);

        ISortableDataProvider<Expense, String> sortableProvider = (ISortableDataProvider<Expense, String>) provider;

        assertEquals("Does not sort by date", SortOrder.DESCENDING, sortableProvider.getSortState().getPropertySortOrder("date"));
    }
}
