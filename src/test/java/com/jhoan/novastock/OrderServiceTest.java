package com.jhoan.novastock;

import com.jhoan.novastock.domain.entity.Order;
import com.jhoan.novastock.domain.entity.Product;
import com.jhoan.novastock.domain.entity.User;
import com.jhoan.novastock.dto.request.OrderItemRequestDTO;
import com.jhoan.novastock.dto.request.OrderRequestDTO;
import com.jhoan.novastock.dto.response.OrderResponseDTO;
import com.jhoan.novastock.repository.OrderRepository;
import com.jhoan.novastock.repository.ProductRepository;
import com.jhoan.novastock.repository.UserRepository;
import com.jhoan.novastock.service.impl.OrderServiceImpl;
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

}
