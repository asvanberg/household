package com.svanberg.household.web.components;

import com.svanberg.household.domain.Expense;
import com.svanberg.household.service.ExpenseService;
import org.apache.wicket.model.IModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DomainProviderTest
{
    @Mock ExpenseService service;

    private DomainProvider<Expense, Long> provider;

    @Before
    public void setUp()
    {
        provider = new DomainProvider<>(service);
    }

    @Test
    public void testIterator() throws Exception
    {
        // given
        int offset = 20;
        int size = 13;
        Expense expense = new Expense();
        List<Expense> expenses = Arrays.asList(expense);

        when(service.list(any(Pageable.class))).thenReturn(expenses);

        // when
        Iterator<? extends Expense> iterator = provider.iterator(offset, size);

        // then
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(service).list(captor.capture());

        assertEquals(size, captor.getValue().getPageSize());
        assertEquals(offset, captor.getValue().getOffset());

        assertTrue(iterator.hasNext());
        assertEquals(expense, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testSize() throws Exception
    {
        long size = 77;

        when(service.count()).thenReturn(size);

        long providedSize = provider.size();

        assertEquals(size, providedSize);
    }

    @Test
    public void testModel() throws Exception
    {
        Expense expense = new Expense();

        IModel<Expense> model = provider.model(expense);

        assertEquals(expense, model.getObject());
    }
}
