package com.svanberg.household.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class Category extends DomainObject<Long>
{
    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic
    private String description;

    public Category() {
    }

    public Category(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public Long getIdentifier() {
        return id;
    }
}
