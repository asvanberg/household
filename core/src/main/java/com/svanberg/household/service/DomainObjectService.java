package com.svanberg.household.service;

import com.svanberg.household.domain.DomainObject;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public interface DomainObjectService {

    <T extends DomainObject> T find(Class<T> clazz, Object identifier);

}
