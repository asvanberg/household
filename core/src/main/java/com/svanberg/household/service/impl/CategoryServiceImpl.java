package com.svanberg.household.service.impl;

import com.svanberg.household.domain.Category;
import com.svanberg.household.repository.CategoryRepository;
import com.svanberg.household.service.CategoryService;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Inject
    public CategoryServiceImpl(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(final String name, final String description) {
        Category category = new Category(name, description);
        repository.save(category);
    }
}
