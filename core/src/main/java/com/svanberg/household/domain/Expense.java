package com.svanberg.household.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 * @since 0.1.0
 */
@Entity
@Data
public class Expense extends DomainObject<Long>
{
    @Id
    @GeneratedValue
    private long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Basic
    private String description;

    @Basic
    private int cost;

    @ManyToOne
    private Category category;

    public Expense() {

    }

    public Expense(Date date, String description, int cost) {
        this.date = date;
        this.description = description;
        this.cost = cost;
    }

    @Override
    public Long getIdentifier() {
        return id;
    }
}
