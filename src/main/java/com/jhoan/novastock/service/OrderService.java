package com.jhoan.novastock.service;

import com.jhoan.novastock.domain.entity.Order;
import com.jhoan.novastock.dto.request.OrderRequestDTO;
import com.jhoan.novastock.dto.response.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO dto);
    OrderResponseDTO getOrderById(Long id);
    OrderResponseDTO updateOrderStatus(Long id, String status);
    void cancelOrder(Long id);

}
