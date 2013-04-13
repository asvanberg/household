package com.svanberg.household.web.expense;

import com.svanberg.household.service.ExpenseService;
import com.svanberg.household.web.test.WicketTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class ExpensePageTest extends WicketTest {

    @Mock ExpenseService expenseService;

    private ExpensePage page;

    @Before
    public void setUp() throws Exception {
        page = tester.startPage(ExpensePage.class);
    }

    @Test
    public void testRenders() throws Exception {
        assertNotNull("Page does not render", page);
    }
}
