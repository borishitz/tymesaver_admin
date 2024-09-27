package com.tracker.recordSearch.services;


import com.tracker.recordSearch.domain.Admin;
import com.tracker.recordSearch.dto.AdminDto;

public interface AdminService {
    Admin save(AdminDto adminDto);

    Admin findByUsername(String username);
}
