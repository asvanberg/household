package com.svanberg.household.web.spring;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.Iterator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PageAdaterTest
{
    public static final long OFFSET = 40L;
    public static final long COUNT = 20L;
    public static final long PAGE = OFFSET / COUNT;
    public static final String PROPERTY = "property";
    public static final boolean ASCENDING = true;

    @Mock
    private SortParam<String> sortParam;

    private PageAdater page;

    @Before
    public void setUp() throws Exception
    {
        when(sortParam.getProperty()).thenReturn(PROPERTY);
        when(sortParam.isAscending()).thenReturn(ASCENDING);

        page = new PageAdater(OFFSET, COUNT, sortParam);
    }

    @Test
    public void page_number() throws Exception
    {
        assertEquals(PAGE, page.getPageNumber());
    }

    @Test
    public void page_size() throws Exception
    {
        assertEquals(COUNT, page.getPageSize());
    }

    @Test
    public void offset() throws Exception
    {
        assertEquals(OFFSET, page.getOffset());
    }

    @Test
    public void sort() throws Exception
    {
        Sort sort = page.getSort();

        Iterator<Sort.Order> iterator = sort.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(PROPERTY, iterator.next().getProperty());
        assertFalse(iterator.hasNext());

        assertEquals(ASCENDING, sort.getOrderFor(PROPERTY).isAscending());
    }

    @Test
    public void null_sort() throws Exception
    {
        page = new PageAdater(OFFSET, COUNT, null);

        assertNull(page.getSort());
    }
}
