package com.svanberg.household.service.impl;

import com.svanberg.household.domain.Category;
import com.svanberg.household.repository.CategoryRepository;
import com.svanberg.household.service.CategoryService;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

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
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public void create(final String name, final String description) {
        Category category = new Category(name, description);
        repository.save(category);
    }

    @Override
    public Category locate(Long identifier)
    {
        return repository.findOne(identifier);
    }
}
