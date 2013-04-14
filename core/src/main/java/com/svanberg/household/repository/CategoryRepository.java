package com.svanberg.household.repository;

import com.svanberg.household.domain.Category;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
