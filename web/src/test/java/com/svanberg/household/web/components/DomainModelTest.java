package com.svanberg.household.web.components;

import com.svanberg.household.domain.DomainObject;
import com.svanberg.household.service.FinderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DomainModelTest
{
    @Mock
    private FinderService<DomainObject<Long>, Long> service;

    @Mock
    private DomainObject<Long> object;

    @Test
    public void testLifecycle() throws Exception
    {
        when(object.getIdentifier()).thenReturn(42L);
        when(service.locate(any(Long.class))).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable
            {
                return invocation.getArguments()[0] != null ? object : null;
            }
        });

        DomainModel<DomainObject<Long>, Long> model = new DomainModel<>(service);

        assertNull(model.getObject());

        model.setObject(object);

        assertEquals(object, model.getObject());

        model.detach();

        assertEquals(object, model.getObject());
    }
}
