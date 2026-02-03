package com.jhoan.novastock;

import com.jhoan.novastock.domain.entity.Category;
import com.jhoan.novastock.domain.entity.Product;
import com.jhoan.novastock.dto.request.ProductRequestDTO;
import com.jhoan.novastock.dto.response.ProductResponseDTO;
import com.jhoan.novastock.repository.CategoryRepository;
import com.jhoan.novastock.repository.ProductRepository;
import com.jhoan.novastock.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

}
