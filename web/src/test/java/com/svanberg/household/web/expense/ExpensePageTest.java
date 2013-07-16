package com.svanberg.household.web.expense;

import com.svanberg.household.service.CategoryService;
import com.svanberg.household.service.ExpenseService;
import com.svanberg.household.web.test.SpringWicketTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.svanberg.household.web.test.Assert.assertStateless;
import static org.junit.Assert.assertNotNull;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class ExpensePageTest extends SpringWicketTest
{

    @Mock ExpenseService expenseService;
    @Mock CategoryService categoryService;

    private ExpensePage page;

    @Before
    public void setUp() throws Exception {
        page = tester().startPage(ExpensePage.class);
    }

    @Test
    public void testRenders() throws Exception {
        assertNotNull("Page does not render", page);
    }

    @Test
    public void testStateless() throws Exception
    {
        assertStateless(page);
    }
}
