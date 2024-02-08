package com.inditex.zarachallenge.infrastructure.persistance;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.inditex.zarachallenge.domain.exception.OfferNotFoundException;
import com.inditex.zarachallenge.domain.exception.ProductNotFoundException;
import com.inditex.zarachallenge.domain.exception.SizeNotFoundException;
import com.inditex.zarachallenge.domain.model.Offer;
import com.inditex.zarachallenge.domain.model.Product;
import com.inditex.zarachallenge.domain.model.Size;
import com.inditex.zarachallenge.domain.repository.SimilarRepository;
import com.inditex.zarachallenge.infrastructure.mappers.OfferMapper;
import com.inditex.zarachallenge.infrastructure.mappers.ProductMapper;
import com.inditex.zarachallenge.infrastructure.mappers.SizeMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
@AllArgsConstructor
public class SimilarRepositoryAdapter implements SimilarRepository {

	private static final String NO_SE_HA_ENCONTRADO_OFFER_PARA_EL_PRODUCT_ID =
			"No se ha encontrado offer para el product id: {}";

	private static final String NO_SE_HA_ENCONTRADO_SIZE_PARA_EL_PRODUCT_ID =
			"No se ha encontrado size para el product id: {}";

	private final ProductRepositoryJpa productRepositoryJpa;

	private final OfferRepositoryJpa offerRepositoryJpa;

	private final SizeRepositoryJpa sizeRepositoryJpa;

	private final ProductMapper productMapper;

	private final OfferMapper offerMapper;

	private final SizeMapper sizeMapper;


	@Override
	public Product findProductById(@NonNull final Long productId) {
		return productMapper.toDomain(productRepositoryJpa
				.findById(productId)
				.orElseThrow(ProductNotFoundException::new));
	}

	@Override
	public Offer findOfferByProductId(@NonNull final Long productId,
			@NonNull final LocalDateTime date) {

		var offerList = offerRepositoryJpa.
				findByProductIdAndValidFromLessThanEqualOrderByValidFromDesc(productId, date);

		if ( offerList.isEmpty() ) {
			log.error(NO_SE_HA_ENCONTRADO_OFFER_PARA_EL_PRODUCT_ID, productId);
			throw new OfferNotFoundException();
		}

		return offerMapper.toDomain(offerList.stream().findFirst().get());
	}

	@Override
	public List<Size> findSizeAvailableByProductId(@NonNull final Long productId) {

		var sizeEntityList = sizeRepositoryJpa
				.findByProductIdAndAvailabilityOrderBySizeAsc(productId, Boolean.TRUE);


		if ( sizeEntityList.isEmpty()) {
			log.error(NO_SE_HA_ENCONTRADO_SIZE_PARA_EL_PRODUCT_ID, productId);
			throw new SizeNotFoundException();
		}

		return sizeMapper.toDomain(sizeEntityList);
	}

}
