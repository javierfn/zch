package com.inditex.zarachallenge.infrastructure.persistance;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.inditex.zarachallenge.domain.exception.ProductNotFoundException;
import com.inditex.zarachallenge.domain.model.Product;
import com.inditex.zarachallenge.domain.repository.ProductRepository;
import com.inditex.zarachallenge.infrastructure.mappers.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
@AllArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

	private final ProductRepositoryJpa productRepositoryJpa;

	private final ProductMapper productMapper;

	@Override
	public Product findProductById(@NonNull final Long productId) {
		return productMapper.toDomain(productRepositoryJpa
				.findById(productId)
				.orElseThrow(ProductNotFoundException::new));
	}

}
