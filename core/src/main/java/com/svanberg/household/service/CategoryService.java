package com.svanberg.household.service;

import com.svanberg.household.domain.Category;

import java.util.List;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public interface CategoryService extends LocatorService<Category, Long> {

    /**
     * Returns all categories.
     *
     * @return all categories
     */
    List<Category> findAll();

    void create(String name, String description);
}
