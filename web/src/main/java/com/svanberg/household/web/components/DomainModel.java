package com.svanberg.household.web.components;

import com.svanberg.household.domain.DomainObject;
import com.svanberg.household.service.LocatorService;
import org.apache.wicket.model.LoadableDetachableModel;

import java.io.Serializable;

public class DomainModel<T extends DomainObject<ID>, ID extends Serializable> extends LoadableDetachableModel<T>
{
    private final LocatorService<T, ID> service;
    private ID identifier;

    public DomainModel(LocatorService<T, ID> service)
    {
        this.service = service;
    }

    @Override
    protected T load()
    {
        return service.locate(identifier);
    }

    @Override
    public void setObject(T object)
    {
        super.setObject(object);

        identifier = object.getIdentifier();
    }
}
