package com.svanberg.household.web.components;

import com.svanberg.household.domain.DomainObject;
import com.svanberg.household.service.FinderService;
import com.svanberg.household.web.spring.PageAdater;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;

import java.io.Serializable;
import java.util.Iterator;

public class DomainProvider<T extends DomainObject<ID>, ID extends Serializable> extends SortableDataProvider<T, String>
{
    private final FinderService<T, ID> service;

    public DomainProvider(FinderService<T, ID> service)
    {
        this.service = service;
    }

    @Override
    public Iterator<? extends T> iterator(long first, long count)
    {
        return service.list(new PageAdater(first, count, getSort())).iterator();
    }

    @Override
    public long size()
    {
        return service.count();
    }

    @Override
    public IModel<T> model(T object)
    {
        DomainModel<T, ID> model = new DomainModel<>(service);
        model.setObject(object);
        return model;
    }

}
