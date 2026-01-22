package com.product.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findByName(String name);
}
