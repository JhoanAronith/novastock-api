package com.jhoan.novastock;

import com.jhoan.novastock.domain.entity.Order;
import com.jhoan.novastock.domain.entity.OrderItem;
import com.jhoan.novastock.domain.entity.Product;
import com.jhoan.novastock.domain.entity.User;
import com.jhoan.novastock.dto.request.OrderItemRequestDTO;
import com.jhoan.novastock.dto.request.OrderRequestDTO;
import com.jhoan.novastock.dto.response.OrderResponseDTO;
import com.jhoan.novastock.repository.OrderRepository;
import com.jhoan.novastock.repository.ProductRepository;
import com.jhoan.novastock.repository.UserRepository;
import com.jhoan.novastock.service.impl.OrderServiceImpl;
import com.jhoan.novastock.web.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrder_WhenStockIsAvailable_ShouldReturnOrderResponseDTO() {
        User user = new User();
        user.setId(1L);
        user.setUsername("jhoan_user");

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Product product = new Product();
        product.setId(1L);
        product.setStockQuantity(10);
        product.setPrice(new BigDecimal("100.00"));

        OrderItemRequestDTO itemRequest = new OrderItemRequestDTO(1L, 2);
        OrderRequestDTO requestDto = new OrderRequestDTO(List.of(itemRequest));

        when(userRepository.findByUsername("jhoan_user")).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order o = invocation.getArgument(0);
            o.setId(100L);
            return o;
        });

        OrderResponseDTO response = orderService.createOrder(requestDto);

        assertThat(response).isNotNull();
        assertThat(product.getStockQuantity()).isEqualTo(8);
        verify(orderRepository).save(any(Order.class));

    }

    @Test
    void getOrderById_WhenOrderExists_ShouldReturnOrderResponseDTO() {

        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        order.setStatus("PENDING");
        order.setItems(new ArrayList<>());
        order.setUser(new User());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        OrderResponseDTO response = orderService.getOrderById(orderId);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(orderId);
        verify(orderRepository).findById(orderId);
    }


    @Test
    void getOrderById_WhenOrderDoesNotExist_ShouldThrowResourceNotFoundException() {

        Long orderId = 99L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> orderService.getOrderById(orderId)
        );

        assertThat(exception.getMessage()).isEqualTo("Pedido no encontrado");
        verify(orderRepository).findById(orderId);
    }

    @Test
    void updateOrderStatus() {
        Long orderId = 1L;
        String newStatus = "CANCELLED";

        Order order = new Order();
        order.setId(orderId);
        order.setStatus("PENDING");
        order.setItems(new ArrayList<>());
        order.setUser(new User());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderResponseDTO result = orderService.updateOrderStatus(orderId, newStatus);

        assertThat(result).isNotNull();
        assertThat(order.getStatus()).isEqualTo("CANCELLED");

        verify(orderRepository).findById(orderId);
        verify(orderRepository).save(any(Order.class));

    }

    @Test
    void cancelOrder_WhenOrderIsPending_ShouldRestoreStockAndChangeStatus() {
        Long orderId = 1L;

        Product product = new Product();
        product.setStockQuantity(10);

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(5);

        Order order = new Order();
        order.setId(orderId);
        order.setStatus("PENDING");
        order.setItems(List.of(item));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        orderService.cancelOrder(orderId);

        assertThat(order.getStatus()).isEqualTo("CANCELLED");
        assertThat(product.getStockQuantity()).isEqualTo(15);

        verify(orderRepository).save(order);
    }

}
