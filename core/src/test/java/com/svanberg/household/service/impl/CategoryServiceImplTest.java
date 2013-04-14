package com.svanberg.household.service.impl;

import com.svanberg.household.domain.Category;
import com.svanberg.household.repository.CategoryRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {

    @Mock CategoryRepository repository;

    private CategoryServiceImpl service;

    @Before
    public void setUp() throws Exception {
        service = new CategoryServiceImpl(repository);
    }

    @Test
    public void testCreate() throws Exception {
        // given
        String name = "Food";
        String description = "Description";

        // when
        service.create(name, description);

        // then
        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(repository, times(1)).save(captor.capture());

        assertEquals("Saves wrong name", name, captor.getValue().getName());
        assertEquals("Saves wrong description", description, captor.getValue().getDescription());
    }
}
