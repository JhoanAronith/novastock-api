package com.jhoan.novastock.repository;

import com.jhoan.novastock.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
