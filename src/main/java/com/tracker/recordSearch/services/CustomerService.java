package com.tracker.recordSearch.services;


import com.tracker.recordSearch.domain.Customer;
import com.tracker.recordSearch.dto.CustomerDto;

public interface CustomerService {
    Customer save(CustomerDto customerDto);

    Customer findByUsername(String username);

    Customer update(CustomerDto customerDto);

    Customer changePass(CustomerDto customerDto);

    CustomerDto getCustomer(String username);
}
