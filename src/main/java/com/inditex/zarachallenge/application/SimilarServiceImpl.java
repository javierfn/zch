package com.inditex.zarachallenge.application;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inditex.zarachallenge.domain.model.ProductDetail;
import com.inditex.zarachallenge.domain.repository.SimilarProductEngineApiRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class SimilarServiceImpl implements SimilarService {

	private final ProductService productService;

	private final SimilarProductEngineApiRepository similarProductEngineApiRepository;

	@Override
	public List<ProductDetail> findSimilarProductsByProductId (Long productId) {

		productService.findProductById(productId);

		var similarProductIdList = similarProductEngineApiRepository.findSimilarProductsByProductId(productId);

		var result = new ArrayList<ProductDetail>();

		similarProductIdList.forEach(similarProductId -> {
			Optional.ofNullable(productService.getProductDetailByProductId(similarProductId)).map(result::add);
		});

		return result;
	}

}
