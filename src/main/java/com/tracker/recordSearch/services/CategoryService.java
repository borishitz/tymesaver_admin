package com.tracker.recordSearch.services;


import com.tracker.recordSearch.domain.Category;
import com.tracker.recordSearch.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category save(Category category);

    Category update(Category category);

    List<Category> findAllByActivatedTrue();

    List<Category> findALl();

    Optional<Category> findById(Long id);

    void deleteById(Long id);

    void enableById(Long id);

    List<CategoryDto> getCategoriesAndSize();
}
