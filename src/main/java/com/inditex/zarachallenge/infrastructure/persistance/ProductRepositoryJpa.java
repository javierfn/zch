package com.inditex.zarachallenge.infrastructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inditex.zarachallenge.infrastructure.persistance.entity.ProductEntity;

public interface ProductRepositoryJpa extends JpaRepository<ProductEntity, Long> {

}
