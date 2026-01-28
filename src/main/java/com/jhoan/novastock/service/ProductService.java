package com.jhoan.novastock.service;

import com.jhoan.novastock.dto.request.ProductRequestDTO;
import com.jhoan.novastock.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponseDTO save(ProductRequestDTO dto);
    Page<ProductResponseDTO> findAll(Pageable pageable);
}
