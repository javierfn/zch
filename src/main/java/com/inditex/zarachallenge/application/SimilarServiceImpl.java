package com.inditex.zarachallenge.application;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inditex.zarachallenge.domain.model.ProductDetail;
import com.inditex.zarachallenge.domain.repository.SimilarRepository;
import com.inditex.zarachallenge.infrastructure.rest.SimilarProductEngineApiAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class SimilarServiceImpl implements SimilarService {

	private final SimilarRepository similarRepository;

	private final SimilarProductEngineApiAdapter similarProductEngineApiAdapter;

	public List<ProductDetail> findSimilarProductsByProductId (Long productId) {

		var product = similarRepository.findProductById(productId);

		var offer = similarRepository.findOfferByProductId(productId, LocalDateTime.now());

	}

}
