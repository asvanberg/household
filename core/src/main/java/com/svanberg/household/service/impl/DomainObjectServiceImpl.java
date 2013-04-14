package com.svanberg.household.service.impl;

import com.svanberg.household.domain.DomainObject;
import com.svanberg.household.service.DomainObjectService;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@Service
public class DomainObjectServiceImpl implements DomainObjectService {

    @PersistenceContext
    EntityManager em;

    @Override
    public <T extends DomainObject> T find(final Class<T> clazz, final Object identifier) {
        return em.find(clazz, identifier);
    }
}
