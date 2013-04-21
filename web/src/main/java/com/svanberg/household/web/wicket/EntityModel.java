package com.svanberg.household.web.wicket;

import com.svanberg.household.domain.DomainObject;
import com.svanberg.household.service.DomainObjectService;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.Serializable;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class EntityModel<T extends DomainObject> implements IModel<T> {
    private static final long serialVersionUID = -6617614098524238401L;

    transient @SpringBean DomainObjectService domainService;

    private final Class<T> clazz;
    private Serializable identifier;
    private transient T entity;

    public EntityModel(Class<T> clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    public EntityModel(T entity) {
        this.clazz = (Class<T>) entity.getClass();

        setObject(entity);
    }

    @Override
    public T getObject() {
        if (entity == null) {
            load();
        }

        return entity;
    }

    @Override
    public void setObject(final T object) {
        identifier = object.getIdentifier();
        entity = object;
    }

    @Override
    public void detach() {
        domainService = null;
        entity = null;
    }

    private void load() {
        Injector.get().inject(this);
        entity = domainService.find(clazz, identifier);
    }
}
