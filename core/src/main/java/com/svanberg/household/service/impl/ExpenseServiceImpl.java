package com.svanberg.household.service.impl;

import com.svanberg.household.domain.Category;
import com.svanberg.household.domain.Expense;
import com.svanberg.household.repository.ExpenseRepository;
import com.svanberg.household.service.ExpenseService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.Objects;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@Service
@Transactional(readOnly = true)
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository repository;

    @Inject
    public ExpenseServiceImpl(ExpenseRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = false)
    public Expense create(Date date, String description, int cost) {
        date = Objects.requireNonNull(date, "Date must not be null");
        description = Objects.requireNonNull(description, "Description must not be null");

        if (description.isEmpty()) {
            throw new IllegalArgumentException("Description must not be empty");
        }

        if (cost <= 0) {
            throw new IllegalArgumentException("Cost must be greater than zero");
        }

        Expense expense = new Expense(date, description, cost);
        return repository.save(expense);
    }

    @Override
    public Page<Expense> findAll(final Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    @Transactional(readOnly = false)
    public Expense setCategory(final Expense expense, final Category category) {
        expense.setCategory(category);
        return repository.save(expense);
    }

    @Override
    @Transactional
    public void delete(final Iterable<Expense> expenses) {
        repository.delete(expenses);
    }

    @Override
    public int totalExpenses() {
        int total = 0;
        for (Expense expense : repository.findAll()) {
            total += expense.getCost();
        }
        return total;
    }
}
