package com.svanberg.household.web.expense;

import com.svanberg.household.service.ExpenseService;
import com.svanberg.household.web.test.WicketTest;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class AddExpensePanelTest extends WicketTest {

    private static final Locale locale = Locale.UK;

    @Mock ExpenseService service;

    AddExpensePanel panel;

    @Before
    public void setUp() throws Exception {
        tester.getSession().setLocale(locale);
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
        tester.assertComponent(path(AddExpensePanel.FORM, AddExpensePanel.DATE), DateTextField.class);
        tester.assertComponent(path(AddExpensePanel.FORM, AddExpensePanel.COST), TextField.class);
        tester.assertComponent(path(AddExpensePanel.FORM, AddExpensePanel.DESCRIPTION), TextArea.class);
    }

    @Test
    public void testSubmitFormSavesExpense() throws Exception {
        // given
        Date date = new Date();
        int cost = 345;
        String description = "Newspaper";

        // when
        FormTester formTester = tester.newFormTester(AddExpensePanel.FORM);
        formTester.setValue(AddExpensePanel.DATE, DateFormat.getDateInstance(DateFormat.SHORT, locale).format(date));
        formTester.setValue(AddExpensePanel.COST, String.valueOf(cost));
        formTester.setValue(AddExpensePanel.DESCRIPTION, description);
        formTester.submit();

        // then
        tester.assertNoErrorMessage();

        ArgumentCaptor<Date> captor = ArgumentCaptor.forClass(Date.class);
        verify(service, times(1)).create(captor.capture(), eq(description), eq(cost));

        assertSameDate(date, captor.getValue());
    }

    public static void assertSameDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(d1);
        c2.setTime(d2);

        assertEquals("Wrong year", c1.get(Calendar.YEAR), c2.get(Calendar.YEAR));
        assertEquals("Wrong month", c1.get(Calendar.MONTH), c2.get(Calendar.MONTH));
        assertEquals("Wrong day", c1.get(Calendar.DATE), c2.get(Calendar.DATE));
    }
}
