package com.svanberg.household.repository;

import com.svanberg.household.domain.Expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
