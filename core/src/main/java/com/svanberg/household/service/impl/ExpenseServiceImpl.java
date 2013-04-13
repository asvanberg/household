package com.svanberg.household.service.impl;

import com.svanberg.household.domain.Expense;
import com.svanberg.household.repository.ExpenseRepository;
import com.svanberg.household.service.ExpenseService;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository repository;

    @Inject
    public ExpenseServiceImpl(ExpenseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addExpense(Date date, String description, int cost) {
        Expense expense = new Expense(date, description, cost);
        repository.save(expense);
    }
}
