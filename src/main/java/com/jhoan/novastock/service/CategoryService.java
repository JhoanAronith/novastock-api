package com.jhoan.novastock.service;

import com.jhoan.novastock.domain.entity.Category;
import com.jhoan.novastock.dto.request.CategoryRequestDTO;

public interface CategoryService {

    Category save(CategoryRequestDTO dto);
}
