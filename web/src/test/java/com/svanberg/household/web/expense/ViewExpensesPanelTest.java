package com.svanberg.household.web.expense;

import com.svanberg.household.web.test.WicketTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class ViewExpensesPanelTest extends WicketTest {

    private ViewExpensesPanel panel;

    @Before
    public void setUp() throws Exception {
        panel = tester.startComponentInPage(ViewExpensesPanel.class);
    }

    @Test
    public void testRenders() throws Exception {
        assertNotNull("Does not render", panel);
    }
}
