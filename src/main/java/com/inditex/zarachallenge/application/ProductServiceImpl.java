package com.inditex.zarachallenge.application;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inditex.zarachallenge.domain.model.Product;
import com.inditex.zarachallenge.domain.model.ProductDetail;
import com.inditex.zarachallenge.domain.repository.OfferRepository;
import com.inditex.zarachallenge.domain.repository.ProductRepository;
import com.inditex.zarachallenge.domain.repository.SizeRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	private final OfferRepository offerRepository;

	private final SizeRepository sizeRepository;

	@Override
	public Product findProductById(final Long productId) {
		return productRepository.findProductById(productId);
	}

	@Override
	public Optional<ProductDetail> getProductDetailByProductId(@NonNull final Long productId) {

		Optional<ProductDetail> result;

		try {
			var product = productRepository.findProductById(productId);
			var offer = offerRepository.findOfferByProductId(productId, LocalDateTime.now());
			var availability = !sizeRepository.findSizeAvailableByProductId(productId)
									.stream()
									.filter(size -> Boolean.TRUE.equals(size.getAvailability()))
									.collect(Collectors.toList())
									.isEmpty();

			var productDetail = ProductDetail.builder()
								.id(String.valueOf(product.getId()))
								.name(product.getName())
								.price(BigDecimal.valueOf(Double.valueOf(offer.getPrice())))
								.availability(availability)
								.build();

			result = Optional.of(productDetail);

		} catch (Exception e) {
			log.error("Exception message: {}", e.getMessage());
			log.error("Exception:", e);

			result = Optional.empty();
		}

		return result;
	}

}
