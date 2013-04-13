package com.svanberg.household.service;

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

}
