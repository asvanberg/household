package com.svanberg.household.web.components;

import com.svanberg.household.domain.DomainObject;
import com.svanberg.household.service.FinderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DomainModelTest
{
    @Mock
    private FinderService<DomainObject<Long>, Long> service;

    @Mock
    private DomainObject<Long> object;

    @Before
    public void setUp() throws Exception
    {
        when(service.locate(null)).thenThrow(new NullPointerException());
    }

    @Test
    public void lifecycle() throws Exception
    {
        long identifier = 42L;

        when(object.getIdentifier()).thenReturn(identifier);
        when(service.locate(identifier)).thenReturn(object);

        DomainModel <DomainObject<Long>, Long> model = new DomainModel<>(service);

        assertNull(model.getObject());

        model.setObject(object);

        assertEquals(object, model.getObject());

        model.detach();

        assertEquals(object, model.getObject());

        model.setObject(null);

        assertNull(model.getObject());

        model.detach();

        assertNull(model.getObject());

        model.setObject(object);

        assertEquals(object, model.getObject());
    }

    @Test(expected = NullPointerException.class)
    public void null_service() throws Exception
    {
        new DomainModel<DomainObject<Long>, Long>(null);
    }
}
