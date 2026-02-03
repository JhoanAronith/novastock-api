package com.jhoan.novastock.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        LocalDateTime orderDate,
        String status,
        BigDecimal totalAmount,
        UserResponseDTO user,
        List<OrderItemResponseDTO> items
) { }
