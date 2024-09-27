package com.tracker.recordSearch.services;

import com.tracker.recordSearch.domain.Country;

import java.util.List;

public interface CountryService {
    List<Country> findAll();
}
