package com.jhoan.novastock.web.controller;

import com.jhoan.novastock.domain.entity.Product;
import com.jhoan.novastock.dto.request.ProductRequestDTO;
import com.jhoan.novastock.dto.request.StockAdjustmentDTO;
import com.jhoan.novastock.dto.response.ProductResponseDTO;
import com.jhoan.novastock.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Operaciones para la creación, edición, listado de productos y ajuste de stock.")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Crear un nuevo producto",
    description = "Recibe datos de un pedido como su nombre, stock y descripcion.")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO response = productService.save(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener productos",
    description = "Obtiene una lista de todos los productos registrados en la base de datos.")
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAll(
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @Operation(summary = "Editar un producto",
    description = "Recibe un producto y actualiza toda la entidad en la base de datos")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> edit(@Valid @RequestBody ProductRequestDTO dto, @PathVariable Long id) {
        ProductResponseDTO response = productService.edit(dto, id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Ajustar Stock",
    description = "Actualiza el stock de un producto según se realiza un pedido o se cancela.")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductResponseDTO> adjustStock(@Valid @RequestBody StockAdjustmentDTO dto, @PathVariable Long id) {
        ProductResponseDTO response = productService.adjustStock(dto, id);
        return ResponseEntity.ok(response);
    }

}
