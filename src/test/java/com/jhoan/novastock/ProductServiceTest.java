package com.jhoan.novastock;

import com.jhoan.novastock.domain.entity.Category;
import com.jhoan.novastock.domain.entity.Product;
import com.jhoan.novastock.dto.request.ProductRequestDTO;
import com.jhoan.novastock.dto.request.StockAdjustmentDTO;
import com.jhoan.novastock.dto.response.ProductResponseDTO;
import com.jhoan.novastock.repository.CategoryRepository;
import com.jhoan.novastock.repository.ProductRepository;
import com.jhoan.novastock.service.impl.ProductServiceImpl;
import com.jhoan.novastock.web.exception.AlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Teclado Mecánico");
        product.setPrice(new BigDecimal("150.00"));
        product.setStockQuantity(10);
    }

    @Test
    void saveProduct_ShouldReturnSavedProduct() {

        ProductRequestDTO requestDTO = new ProductRequestDTO(
                "Teclado Mecánico",
                "Teclado RGB",
                new BigDecimal("150.00"),
                10,
                1L
        );

        Category category = new Category();
        category.setId(1L);
        category.setName("Periféricos");

        product.setCategory(category);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);


        ProductResponseDTO result = productService.save(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Teclado Mecánico");

    }

    @Test
    void editProduct_ShouldReturnEditedProduct() {

        Long productId = 1L;

        ProductRequestDTO requestDTO = new ProductRequestDTO(
                "Teclado Mecánico Pro",
                "Teclado RGB",
                new BigDecimal("120.00"),
                15,
                1L
        );

        Category category = new Category();
        category.setId(1L);
        category.setName("Periféricos");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(productRepository.existsByName((requestDTO.name()))).thenReturn(false);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO result = productService.edit(requestDTO, productId);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Teclado Mecánico Pro");
        assertThat(result.price()).isEqualTo(new BigDecimal("120.00"));

        verify(productRepository).findById(productId);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void editProduct_WhenNameAlreadyExists_ShouldThrowException() {

        ProductRequestDTO requestDTO = new ProductRequestDTO("Nombre Repetido", "Desc", BigDecimal.ONE, 1, 1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        when(productRepository.existsByName("Nombre Repetido")).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> {
            productService.edit(requestDTO, 1L);
        });

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void adjustStock_WhenQuantityIsValid_ShouldUpdateStock() {

        Long productId = 1L;

        StockAdjustmentDTO adjustmentDTO = new StockAdjustmentDTO(5);

        Category category = new Category();
        category.setName("Electrónica");
        product.setCategory(category);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.adjustStock(adjustmentDTO, productId);

        assertThat(result.stockQuantity()).isEqualTo(15);
        verify(productRepository).findById(productId);
    }

    @Test
    void adjustStock_WhenResultIsNegative_ShouldThrowException() {

        Long productId = 1L;
        StockAdjustmentDTO adjustmentDTO = new StockAdjustmentDTO(-15);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.adjustStock(adjustmentDTO, productId);
        });

        assertThat(exception.getMessage()).contains("Operación inválida");
        assertThat(product.getStockQuantity()).isEqualTo(10);
    }

}
