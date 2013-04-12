package com.svanberg.household.web.expense;

import com.svanberg.household.web.test.WebUnitTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@RunWith(MockitoJUnitRunner.class)
public class AddExpensePanelTest extends WebUnitTest {

    AddExpensePanel panel;

    @Before
    public void setUp() throws Exception {
        panel = (AddExpensePanel) tester.startComponent(new AddExpensePanel("panel"));
    }

    @Test
    public void testRenders() throws Exception {
        assertNotNull("Panel does not render", panel);
    }
}
