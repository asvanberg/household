package com.svanberg.household.domain;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@MappedSuperclass
public abstract class DomainObject<ID extends Serializable>
{
    public abstract ID getIdentifier();
}
