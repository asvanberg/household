package com.svanberg.household.service.impl;

import com.svanberg.household.domain.Category;
import com.svanberg.household.domain.Expense;
import com.svanberg.household.repository.ExpenseRepository;
import com.svanberg.household.service.ExpenseService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExpenseServiceImplTest {

    @Mock ExpenseRepository repository;

    ExpenseService service;

    @Before
    public void setUp() throws Exception {
        when(repository.save(isA(Expense.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0];
            }
        });

        service = new ExpenseServiceImpl(repository);
    }

    @Test
    public void testAddExpense() throws Exception {
        // given
        String description = "New pants";
        Date date = new Date();
        int cost = 42;

        // when
        service.create(date, description, cost);

        // then
        ArgumentCaptor<Expense> captor = ArgumentCaptor.forClass(Expense.class);
        verify(repository, times(1)).save(captor.capture());

        Expense expense = captor.getValue();

        assertEquals("Saves wrong date", date, expense.getDate());
        assertEquals("Saves wrong message", description, expense.getDescription());
        assertEquals("Saves wrong cost", cost, expense.getCost());
    }

    @Test
    public void testSetCategory() throws Exception {
        // given
        Category category = new Category();
        Expense expense = new Expense();

        when(repository.save(isA(Expense.class))).then(new Answer<Object>() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0];
            }
        });

        // when
        Expense returnedExpense = service.setCategory(expense, category);

        // then
        ArgumentCaptor<Expense> captor = ArgumentCaptor.forClass(Expense.class);
        verify(repository, times(1)).save(captor.capture());

        Expense savedExpense = captor.getValue();
        assertEquals("Does not update category", category, savedExpense.getCategory());
        assertEquals("Does not return persisted expense", savedExpense, returnedExpense);
    }

    @Test(expected = NullPointerException.class)
    public void testFailsIfDateNull() throws Exception {
        // given
        Date date = null;
        String description = "Desc";
        int cost = 10;

        // when
        service.create(date, description, cost);

        // then
        fail("Should get exception");
    }

    @Test(expected = NullPointerException.class)
    public void testFailsIfDescriptionNull() throws Exception {
        // given
        Date date = new Date();
        String description = null;
        int cost = 10;

        // when
        service.create(date, description, cost);

        // then
        fail("Should get exception");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailsIfDescriptionEmpty() throws Exception {
        // given
        Date date = new Date();
        String description = "";
        int cost = 10;

        // when
        service.create(date, description, cost);

        // then
        fail("Should get IllegalArgumentException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailsIfCostLessThanOne() throws Exception {
        // given
        Date date = new Date();
        String description = "Description";
        int cost = 0;

        // when
        service.create(date, description, cost);

        // then
        fail("Should get IllegalArgumentException");
    }

    @Test
    public void testDelete() throws Exception {
        // given
        Expense expense = new Expense();

        // when
        service.delete(expense);

        // then
        verify(repository, times(1)).delete(eq(expense));
    }

    @Test
    public void testTotalExpenses() throws Exception {
        // given
        int cost1 = 7;
        int cost2 = 9;

        Expense expense1 = new Expense(new Date(), "Desc", cost1);
        Expense expense2 = new Expense(new Date(), "Desc", cost2);

        when(repository.findAll()).thenReturn(Arrays.asList(expense1, expense2));

        // when
        int totalCost = service.totalExpenses();

        // then
        assertEquals("Wrong total expense", cost1 + cost2, totalCost);
    }

    @Test
    public void testAverageWeekly() throws Exception {
        // given
        int cost1 = 232;
        int cost2 = 151;

        int days = 4;

        Date date1 = new Date();
        Date date2 = new Date(date1.getTime() - days * 86400 * 1000);

        Expense expense1 = new Expense(date1, "Desc", cost1);
        Expense expense2 = new Expense(date2, "Desc", cost2);

        int expected = (int) ((cost1 + cost2) / 4.0 * 7);

        when(repository.findAll()).thenReturn(Arrays.asList(expense1, expense2));

        // when
        int weekly = service.averageWeeklyExpenses();

        // then
        assertEquals("Wrong weekly average", expected, weekly);
    }

    @Test
    public void average_weekly_no_delta() throws Exception {
        // given
        int cost1 = 232;
        int cost2 = 151;

        Date date1 = new Date();

        Expense expense1 = new Expense(date1, "Desc", cost1);
        Expense expense2 = new Expense(date1, "Desc", cost2);

        int expected = cost1 + cost2;

        when(repository.findAll()).thenReturn(Arrays.asList(expense1, expense2));

        // when
        int weekly = service.averageWeeklyExpenses();

        // then
        assertEquals("Wrong weekly average", expected, weekly);
    }

    @Test
    public void locate() throws Exception
    {
        Expense expense = new Expense(new Date(), "desc", 78);
        long id = 42L;

        when(repository.findOne(id)).thenReturn(expense);

        assertEquals(expense, service.locate(id));
    }

    @Test
    public void count() throws Exception
    {
        long count = 89L;

        when(repository.count()).thenReturn(count);

        assertEquals(count, service.count());
    }

    @Test
    public void list() throws Exception
    {
        int pageNumber = 20;
        int size = 13;

        Expense expense = new Expense(new Date(), "desc", 78);
        Page<Expense> page = new PageImpl<>(Arrays.asList(expense));
        PageRequest pageRequest = new PageRequest(pageNumber, size);

        when(service.list(pageRequest)).thenReturn(page);

        Iterator<? extends Expense> iterator = service.list(pageRequest).iterator();

        assertTrue(iterator.hasNext());
        assertEquals(expense, iterator.next());
        assertFalse(iterator.hasNext());
    }
}
