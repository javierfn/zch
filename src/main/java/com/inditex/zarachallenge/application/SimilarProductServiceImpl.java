package com.inditex.zarachallenge.application;

import java.util.ArrayList;
import java.util.List;
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
public class SimilarProductServiceImpl implements SimilarProductService {

	private final ProductService productService;

	private final SimilarProductEngineApiRepository similarProductEngineApiRepository;

	@Override
	public List<ProductDetail> findSimilarProductsByProductId (final Long productId) {

		productService.findProductById(productId);

		var similarProductIdList = similarProductEngineApiRepository.findSimilarProductsByProductId(productId);

		var result = new ArrayList<ProductDetail>();

		similarProductIdList.forEach(similarProductId -> {
			productService.getProductDetailByProductId(similarProductId).map(result::add);
		});

		return result;
	}

}
