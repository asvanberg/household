package com.svanberg.household.domain;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@MappedSuperclass
public abstract class DomainObject {
    public abstract Serializable getIdentifier();
}
