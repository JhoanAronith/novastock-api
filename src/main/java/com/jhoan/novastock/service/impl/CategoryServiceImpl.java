package com.jhoan.novastock.service.impl;

import com.jhoan.novastock.domain.entity.Category;
import com.jhoan.novastock.dto.request.CategoryRequestDTO;
import com.jhoan.novastock.repository.CategoryRepository;
import com.jhoan.novastock.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category save(CategoryRequestDTO dto) {

        Category category = new Category();
        category.setName(dto.name());

        return categoryRepository.save(category);

    }
}
