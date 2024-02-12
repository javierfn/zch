package com.inditex.zarachallenge.application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
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
@Transactional
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	private final OfferRepository offerRepository;

	private final SizeRepository sizeRepository;

	private final String date;

	public ProductServiceImpl(ProductRepository productRepository, OfferRepository offerRepository,
			SizeRepository sizeRepository, @Value("${date}") String date) {
		this.productRepository = productRepository;
		this.offerRepository = offerRepository;
		this.sizeRepository = sizeRepository;
		this.date = date;
	}

	@Override
	public Product findProductById(final Long productId) {
		return productRepository.findProductById(productId);
	}

	@Override
	public Optional<ProductDetail> getProductDetailByProductId(@NonNull final Long productId) {

		Optional<ProductDetail> result;

		try {
			var product = productRepository.findProductById(productId);
			var offer = offerRepository.findOfferByProductId(productId,
					LocalDateTime.parse(date,
							DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
			var availability = !sizeRepository.findSizeListByProductId(productId)
									.stream()
									.filter(size -> Boolean.TRUE.equals(size.getAvailability()))
									.toList()
									.isEmpty();

			var productDetail = ProductDetail.builder()
								.id(productId)
								.name(product.getName())
								.price(offer.getPrice())
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
