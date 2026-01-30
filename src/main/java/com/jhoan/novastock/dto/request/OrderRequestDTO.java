package com.jhoan.novastock.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequestDTO(
        @NotEmpty(message = "El pedido debe tener al menos un pedido")
        List<OrderItemRequestDTO> items
) { }
