package com.tracker.recordSearch.impl;

import com.tracker.recordSearch.domain.Country;
import com.tracker.recordSearch.repository.CountryRepository;
import com.tracker.recordSearch.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }
}
