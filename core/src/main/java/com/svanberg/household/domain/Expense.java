package com.svanberg.household.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 * @since 0.1.0
 */
@Entity
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public String getCategoryName() {
        return getCategory() != null ? getCategory().getName() : null;
    }
}
