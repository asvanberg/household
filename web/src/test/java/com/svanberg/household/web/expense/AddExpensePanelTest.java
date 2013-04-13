package com.svanberg.household.web.expense;

import com.svanberg.household.web.test.WebUnitTest;

import org.apache.wicket.markup.html.form.Form;
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
        panel = tester.startComponentInPage(AddExpensePanel.class);
    }

    @Test
    public void testRenders() throws Exception {
        assertNotNull("Panel does not render", panel);
    }

    @Test
    public void testContainsForm() throws Exception {
        tester.assertComponent(AddExpensePanel.FORM, Form.class);
    }
}
