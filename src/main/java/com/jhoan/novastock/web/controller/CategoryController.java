package com.jhoan.novastock.web.controller;

import com.jhoan.novastock.domain.entity.Category;
import com.jhoan.novastock.dto.request.CategoryRequestDTO;
import com.jhoan.novastock.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categorias", description = "Operaciones para la creación de categorías")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Crear una categoría",
    description = "Recibe un string con el nombre de la nueva categoría.")
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CategoryRequestDTO dto) {
        Category savedCategory = categoryService.save(dto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

}
