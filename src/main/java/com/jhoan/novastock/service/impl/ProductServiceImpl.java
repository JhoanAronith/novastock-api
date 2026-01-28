package com.jhoan.novastock.service.impl;

import com.jhoan.novastock.domain.entity.Category;
import com.jhoan.novastock.domain.entity.Product;
import com.jhoan.novastock.dto.request.ProductRequestDTO;
import com.jhoan.novastock.dto.response.ProductResponseDTO;
import com.jhoan.novastock.repository.CategoryRepository;
import com.jhoan.novastock.repository.ProductRepository;
import com.jhoan.novastock.service.ProductService;
import com.jhoan.novastock.web.exception.AlreadyExistsException;
import com.jhoan.novastock.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductResponseDTO save(ProductRequestDTO dto) {

        if (productRepository.existsByName(dto.name())) {
            throw new AlreadyExistsException("Ya existe un producto con ese nombre");
        }

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return new ProductResponseDTO(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getStockQuantity(),
                savedProduct.getCategory().getName()
        );
    }

}
