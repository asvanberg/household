package com.svanberg.household.service;

public interface LocatorService<T, ID>
{
    T locate(ID identifier);
}
