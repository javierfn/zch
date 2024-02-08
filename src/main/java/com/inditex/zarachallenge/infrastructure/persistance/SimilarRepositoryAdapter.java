package com.inditex.zarachallenge.infrastructure.persistance;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.inditex.zarachallenge.domain.exception.ProductNotFoundException;
import com.inditex.zarachallenge.domain.model.Product;
import com.inditex.zarachallenge.domain.repository.SimilarRepository;
import com.inditex.zarachallenge.infrastructure.mappers.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
@AllArgsConstructor
public class SimilarRepositoryAdapter implements SimilarRepository {
													
	private ProductRepositoryJpa productRepositoryJpa;

	private OfferRepositoryJpa offerRepositoryJpa;

	private SizeRepositoryJpa sizeRepositoryJpa;

	private ProductMapper productMapper;

	@Override
	public Product findProductById(@NonNull Long productId) {
		return productMapper.toDomain(productRepositoryJpa
				.findById(productId)
				.orElseThrow(ProductNotFoundException::new));
	}

}
