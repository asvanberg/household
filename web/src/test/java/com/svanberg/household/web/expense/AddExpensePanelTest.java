package com.svanberg.household.web.expense;

import com.svanberg.household.domain.Category;
import com.svanberg.household.domain.Expense;
import com.svanberg.household.service.CategoryService;
import com.svanberg.household.service.DomainObjectService;
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
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class AddExpensePanelTest extends WicketTest {

    private static final Locale locale = Locale.UK;

    @Mock ExpenseService service;
    @Mock CategoryService categories;
    @Mock DomainObjectService domainObjectService;

    AddExpensePanel panel;

    @Before
    public void setUp() throws Exception {
        tester().getSession().setLocale(locale);
        panel = tester().startComponentInPage(AddExpensePanel.class);
    }

    @Test
    public void testRenders() throws Exception {
        assertNotNull("Panel does not render", panel);
    }

    @Test
    public void testContainsForm() throws Exception {
        tester().assertComponent(AddExpensePanel.FORM, Form.class);
    }

    @Test
    public void testFormComponentsExist() throws Exception {
        tester().assertComponent(path(AddExpensePanel.FORM, AddExpensePanel.DATE), DateTextField.class);
        tester().assertComponent(path(AddExpensePanel.FORM, AddExpensePanel.COST), TextField.class);
        tester().assertComponent(path(AddExpensePanel.FORM, AddExpensePanel.DESCRIPTION), TextArea.class);
    }

    @Test
    public void testSubmitFormSavesExpense() throws Exception {
        // given
        Date date = new Date();
        int cost = 345;
        String description = "Newspaper";

        // when
        FormTester formTester = tester().newFormTester(AddExpensePanel.FORM);
        formTester.setValue(AddExpensePanel.DATE, convertDate(date));
        formTester.setValue(AddExpensePanel.COST, String.valueOf(cost));
        formTester.setValue(AddExpensePanel.DESCRIPTION, description);
        formTester.submit();

        // then
        tester().assertNoErrorMessage();

        ArgumentCaptor<Date> captor = ArgumentCaptor.forClass(Date.class);
        verify(service, times(1)).create(captor.capture(), eq(description), eq(cost));

        assertSameDate(date, captor.getValue());
    }

    @Test
    public void testSetCategory() throws Exception {
        // given
        Date date = new Date();
        int cost = 345;
        String description = "Foo";
        int categoryIndex = 0;

        Category c1 = new Category("Food", "Desc");
        Category c2 = new Category("Utility", "Desc");
        Category c3 = new Category("Entertainment", "Desc");
        List<Category> list = Arrays.asList(c1, c2, c3);

        when(categories.findAll()).thenReturn(list);
        when(service.create(isA(Date.class), isA(String.class), isA(Integer.class))).thenReturn(new Expense());

        // when
        setUp();
        FormTester formTester = tester().newFormTester(AddExpensePanel.FORM);
        formTester.setValue(AddExpensePanel.DATE, convertDate(date));
        formTester.setValue(AddExpensePanel.COST, String.valueOf(cost));
        formTester.setValue(AddExpensePanel.DESCRIPTION, description);
        formTester.select(AddExpensePanel.CATEGORY, categoryIndex);
        formTester.submit();

        //then
        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(service, times(1)).setCategory(isA(Expense.class), captor.capture());

        assertEquals("Sets wrong category", list.get(categoryIndex), captor.getValue());
    }

    private String convertDate(final Date date) {
        return DateFormat.getDateInstance(DateFormat.SHORT, locale).format(date);
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
