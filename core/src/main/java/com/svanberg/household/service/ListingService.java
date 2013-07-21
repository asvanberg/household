package com.svanberg.household.service;

import org.springframework.data.domain.Pageable;

public interface ListingService<T>
{
    Iterable<T> list(Pageable page);
    long count();
}
