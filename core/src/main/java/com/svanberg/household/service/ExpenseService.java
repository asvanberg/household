package com.svanberg.household.service;

import com.svanberg.household.domain.Category;
import com.svanberg.household.domain.Expense;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public interface ExpenseService {

    void addExpense(Date date, String description, int cost);
    Page<Expense> findAll(Pageable pageable);
    long count();

    /**
     * Updates the category the given expense belongs to.
     * Set to {@code null} to remove the category association.
     *
     * @param expense  expense whose category to update
     * @param category the new category
     * @return the same expense with the category updated
     */
    Expense setCategory(Expense expense, Category category);

}
