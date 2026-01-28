package com.jhoan.novastock.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String name,
        @Size(max = 500)
        String description,
        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor a 0")
        BigDecimal price,
        @NotNull(message = "El stock es obligatorio")
        @Positive(message = "El stock no debe ser negativo")
        Integer stockQuantity,
        @NotNull(message = "La categoría es obligatoria")
        @Positive(message = "ID de categoría invalido")
        Long categoryId
) {}
