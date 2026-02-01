package com.jhoan.novastock.web.controller;

import com.jhoan.novastock.dto.request.OrderRequestDTO;
import com.jhoan.novastock.dto.response.OrderResponseDTO;
import com.jhoan.novastock.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
@Tag(name = "Pedidos", description = "Operaciones para la creación, consulta y gestión de pedidos y stock.")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Crear un pedido nuevo",
    description = "Recibe una lista de productos y cantidades. Valida el stock actual, calcula el total y descuenta el inventario automáticamente.")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO dto) {
        OrderResponseDTO response = orderService.createOrder(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un pedido",
    description = "Obtiene un pedido específico según su ID. Devuelve datos como la productos y cantidades del pedido, su estado y precio total.")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Cambia el estado de un pedido",
    description = "Recibe un estado como parámetro y lo actualiza en el pedido")
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Long id, @RequestBody String status) {
        return ResponseEntity.ok((orderService.updateOrderStatus(id, status)));
    }

    @Operation(summary = "Cancela un pedido",
    description = "Cambia el estado de un pedido a CANCELLED y devuelve el stock al inventario")
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> cancel(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

}
