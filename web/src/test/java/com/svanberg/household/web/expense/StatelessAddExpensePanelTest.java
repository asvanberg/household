package com.svanberg.household.web.expense;

import com.svanberg.household.domain.Category;
import com.svanberg.household.domain.Expense;
import com.svanberg.household.service.CategoryService;
import com.svanberg.household.service.ExpenseService;
import com.svanberg.household.web.test.WicketTest;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static com.svanberg.household.web.test.Assert.assertSameDay;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class StatelessAddExpensePanelTest extends WicketTest
{
    private static final Locale LOCALE = Locale.UK;

    @Mock CategoryService categoryService;
    @Mock ExpenseService expenseService;

    private StatelessAddExpensePanel panel;

    @Before
    public void setUp()
    {
        tester().getSession().setLocale(LOCALE);
        panel = tester().startComponentInPage(StatelessAddExpensePanel.class);
    }

    @Test
    public void testRenders()
    {
        assertNotNull("Panel does not render", panel);
    }

    @Test
    public void testStateless()
    {
        assertTrue("Panel is not stateless", panel.isStateless());
    }

    @Test
    public void testHasForm()
    {
        tester().assertComponent(path(StatelessAddExpensePanel.FORM), Form.class);
    }

    @Test
    public void testContainsFormComponents()
    {
        assertFormComponent(StatelessAddExpensePanel.DATE);
        assertFormComponent(StatelessAddExpensePanel.COST);
        assertFormComponent(StatelessAddExpensePanel.DESCRIPTION);
        assertFormComponent(StatelessAddExpensePanel.CATEGORY);
    }

    @Test
    public void testShowsErrorOnEmptySubmit()
    {
        FormTester formTester = tester().newFormTester(StatelessAddExpensePanel.FORM);
        formTester.submit();

        assertFalse("Expected error feedback messages", tester().getMessages(FeedbackMessage.ERROR).isEmpty());
    }

    @Test
    public void testCreatesExpenseOnSubmit()
    {
        // given
        Date date = new Date();
        int cost = 78;
        String description = "Description";

        // when
        FormTester formTester = tester().newFormTester(StatelessAddExpensePanel.FORM);
        formTester.setValue(formComponentPath(StatelessAddExpensePanel.DATE), convertDate(date));
        formTester.setValue(formComponentPath(StatelessAddExpensePanel.COST), String.valueOf(cost));
        formTester.setValue(formComponentPath(StatelessAddExpensePanel.DESCRIPTION), description);
        formTester.submit();

        // then
        ArgumentCaptor<Date> captor = ArgumentCaptor.forClass(Date.class);
        verify(expenseService, times(1)).create(captor.capture(), eq(description), eq(cost));

        assertSameDay(date, captor.getValue());
    }

    @Test
    public void testSetsCategory()
    {
        // given
        Expense expense = new Expense();
        int index = 1;
        Category c0 = new Category("Food", "Food");
        Category c1 = new Category("Utility", "Utility");
        Category c2 = new Category("Entertainment", "Entertainment");

        when(categoryService.findAll()).thenReturn(Arrays.asList(c0, c1, c2));
        when(expenseService.create(isA(Date.class), isA(String.class), isA(Integer.class))).thenReturn(expense);

        // when
        setUp();
        FormTester formTester = tester().newFormTester(StatelessAddExpensePanel.FORM);
        formTester.setValue(formComponentPath(StatelessAddExpensePanel.DATE), convertDate(new Date()));
        formTester.setValue(formComponentPath(StatelessAddExpensePanel.COST), "23");
        formTester.setValue(formComponentPath(StatelessAddExpensePanel.DESCRIPTION), "Desc");
        formTester.select(formComponentPath(StatelessAddExpensePanel.CATEGORY), index);
        formTester.submit();

        // then
        verify(expenseService, times(1)).setCategory(eq(expense), eq(c1));
    }

    private String convertDate(Date date)
    {
        return DateFormat.getDateInstance(DateFormat.SHORT, LOCALE).format(date);
    }

    private void assertFormComponent(final String id)
    {
        tester().assertComponent(path(StatelessAddExpensePanel.FORM, formComponentPath(id)), FormComponent.class);
    }

    private String formComponentPath(final String id)
    {
        return path(borderPath(id), StatelessAddExpensePanel.INPUT);
    }

    private String borderPath(String borderId)
    {
        return path(borderId, borderId + "_body");
    }
}