package com.inditex.zarachallenge.infrastructure.persistance;


import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.inditex.zarachallenge.domain.exception.OfferNotFoundException;
import com.inditex.zarachallenge.domain.model.Offer;
import com.inditex.zarachallenge.domain.repository.OfferRepository;
import com.inditex.zarachallenge.infrastructure.mappers.OfferMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
@AllArgsConstructor
public class OfferRepositoryAdapter implements OfferRepository {

	private static final String NO_SE_HA_ENCONTRADO_OFFER_PARA_EL_PRODUCT_ID =
			"No se ha encontrado offer para el product id: {}";

	private final OfferRepositoryJpa offerRepositoryJpa;

	private final OfferMapper offerMapper;

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


}
