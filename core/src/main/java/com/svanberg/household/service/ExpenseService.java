package com.svanberg.household.service;

import java.util.Date;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public interface ExpenseService {
    void addExpense(Date date, String description, int cost);
}
