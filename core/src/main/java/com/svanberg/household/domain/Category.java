package com.svanberg.household.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@Entity
public class Category extends DomainObject {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic
    private String description;

    @OneToMany(mappedBy = "category")
    private Collection<Expense> expenses;

    public Category() {
    }

    public Category(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public Serializable getIdentifier() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Collection<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(final Collection<Expense> expenses) {
        this.expenses = expenses;
    }
}
