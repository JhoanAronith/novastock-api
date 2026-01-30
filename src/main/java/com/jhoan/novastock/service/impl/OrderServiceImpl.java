package com.jhoan.novastock.service.impl;

import com.jhoan.novastock.domain.entity.Order;
import com.jhoan.novastock.domain.entity.OrderItem;
import com.jhoan.novastock.domain.entity.Product;
import com.jhoan.novastock.dto.request.OrderItemRequestDTO;
import com.jhoan.novastock.dto.request.OrderRequestDTO;
import com.jhoan.novastock.dto.response.OrderItemResponseDTO;
import com.jhoan.novastock.dto.response.OrderResponseDTO;
import com.jhoan.novastock.repository.OrderItemRepository;
import com.jhoan.novastock.repository.OrderRepository;
import com.jhoan.novastock.repository.ProductRepository;
import com.jhoan.novastock.service.OrderService;
import com.jhoan.novastock.web.exception.InsufficientStockException;
import com.jhoan.novastock.web.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        BigDecimal totalPedido = BigDecimal.ZERO;
        List<OrderItem> items = new ArrayList<>();

        for (OrderItemRequestDTO itemDto : dto.items()) {
            Product product = productRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado ID: " + itemDto.productId()));

            if (product.getStockQuantity() < itemDto.quantity()) {
                throw new InsufficientStockException("Stock insuficiente para: " + product.getName());
            }

            BigDecimal subtotal = product.getPrice().multiply(new BigDecimal(itemDto.quantity()));
            totalPedido = totalPedido.add(subtotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.quantity());
            orderItem.setUnitPrice(product.getPrice());

            items.add(orderItem);

            product.setStockQuantity(product.getStockQuantity() - itemDto.quantity());

        }

        order.setTotalAmount(totalPedido);
        order.setItems(items);

        Order savedOrder = orderRepository.save(order);

        return mapToResponseDTO(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Pedido no encontrado"));
        return mapToResponseDTO(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));

        order.setStatus(status.toUpperCase());
        Order updateOrder = orderRepository.save(order);

        return mapToResponseDTO(updateOrder);
    }

    @Override
    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));

        if ("CANCELLED".equals(order.getStatus())) {
            throw new IllegalStateException("El pedido ya se encuentra cancelado");
        }

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            int newStock = product.getStockQuantity() + item.getQuantity();
            product.setStockQuantity(newStock);
        }

        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }

    private OrderResponseDTO mapToResponseDTO(Order order) {
        List<OrderItemResponseDTO> itemDTOs = order.getItems().stream()
                .map(this::mapItemResponseDTO)
                .toList();
        return new OrderResponseDTO(
                order.getId(),
                order.getOrderDate(),
                order.getStatus(),
                order.getTotalAmount(),
                itemDTOs
        );
    }

    private OrderItemResponseDTO mapItemResponseDTO(OrderItem item) {
        BigDecimal subtotal = item.getUnitPrice().multiply(new BigDecimal(item.getQuantity()));
        return new OrderItemResponseDTO(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                subtotal
        );
    }

}
