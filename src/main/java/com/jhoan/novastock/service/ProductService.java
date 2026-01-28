package com.jhoan.novastock.service;

import com.jhoan.novastock.domain.entity.Product;
import com.jhoan.novastock.dto.request.ProductRequestDTO;
import com.jhoan.novastock.dto.response.ProductResponseDTO;

public interface ProductService {

    ProductResponseDTO save(ProductRequestDTO dto);

}
