package com.svanberg.household.web.expense;

import com.svanberg.household.service.ExpenseService;
import com.svanberg.household.web.test.WebUnitTest;
import com.svanberg.household.web.wicket.DateField;
import com.svanberg.household.web.wicket.NumberField;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class AddExpensePanelTest extends WebUnitTest {

    @Mock ExpenseService service;

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

    @Test
    public void testFormComponentsExist() throws Exception {
        tester.assertComponent(path(AddExpensePanel.FORM, AddExpensePanel.DATE), DateField.class);
        tester.assertComponent(path(AddExpensePanel.FORM, AddExpensePanel.COST), NumberField.class);
        tester.assertComponent(path(AddExpensePanel.FORM, AddExpensePanel.DESCRIPTION), TextArea.class);
    }
}
