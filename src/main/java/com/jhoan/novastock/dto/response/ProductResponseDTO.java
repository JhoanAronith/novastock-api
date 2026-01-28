package com.jhoan.novastock.dto.response;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String categoryName
) {}
