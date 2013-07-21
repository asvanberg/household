package com.svanberg.household.service;

import com.svanberg.household.domain.Category;
import com.svanberg.household.domain.Expense;

import java.util.Date;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public interface ExpenseService extends FinderService<Expense, Long>
{
    Expense create(Date date, String description, int cost);

    /**
     * Updates the category the given expense belongs to.
     * Set to {@code null} to remove the category association.
     *
     * @param expense  expense whose category to update
     * @param category the new category
     * @return the same expense with the category updated
     */
    Expense setCategory(Expense expense, Category category);

    /**
     * Deletes the given expense.
     *
     * @param expense to delete
     */
    void delete(Expense expense);

    /**
     * Returns the total cost of all expenses.
     *
     * @return the total cost of all expenses
     */
    int totalExpenses();

    /**
     * Returns the average spent per week.
     *
     * @return the average spent per week
     */
    int averageWeeklyExpenses();
}
