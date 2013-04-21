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

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
