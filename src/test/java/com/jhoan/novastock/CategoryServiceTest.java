package com.jhoan.novastock;

import com.jhoan.novastock.domain.entity.Category;
import com.jhoan.novastock.dto.request.CategoryRequestDTO;
import com.jhoan.novastock.repository.CategoryRepository;
import com.jhoan.novastock.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void createCategory() {

        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Electrónica");

        Category categorySaved = new Category();
        categorySaved.setId(1L);
        categorySaved.setName("Electrónica");

        when(categoryRepository.save(any(Category.class))).thenReturn(categorySaved);

        Category result = categoryService.save(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Electrónica");
        verify(categoryRepository).save(any(Category.class));

    }

}
