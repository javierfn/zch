package com.inditex.zarachallenge.infrastructure.persistance;


import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.inditex.zarachallenge.domain.exception.SizeNotFoundException;
import com.inditex.zarachallenge.domain.model.Size;
import com.inditex.zarachallenge.domain.repository.SizeRepository;
import com.inditex.zarachallenge.infrastructure.mappers.SizeMapper;
import com.inditex.zarachallenge.infrastructure.persistance.entity.SizeEntity;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
@AllArgsConstructor
public class SizeRepositoryAdapter implements SizeRepository {

	private static final String NO_SE_HA_ENCONTRADO_SIZE_PARA_EL_PRODUCT_ID =
			"No se ha encontrado size para el product id: {}";

	private static final String SIZE_ID_IS_NULL = "Size id is null";

	private final SizeRepositoryJpa sizeRepositoryJpa;

	private final SizeMapper sizeMapper;

	@Override
	public List<Size> findSizeListByProductId(@NonNull final Long productId) {

		var sizeEntityList = sizeRepositoryJpa.findByProductIdOrderBySizeAsc(productId);

		if ( sizeEntityList.isEmpty()) {
			log.error(NO_SE_HA_ENCONTRADO_SIZE_PARA_EL_PRODUCT_ID, productId);
			throw new SizeNotFoundException();
		}

		return sizeMapper.toDomain(sizeEntityList);
	}

	@Override
	public Size updateAvailability(@NonNull Long sizeId,
			@NonNull Boolean availability,
			@NonNull Timestamp update) {

		var sizeEntity = sizeRepositoryJpa.findById(sizeId)
				.orElseThrow(SizeNotFoundException::new);

		sizeEntity.setAvailability(availability);
		sizeEntity.setLastUpdated(update.toLocalDateTime());

		return sizeMapper.toDomain(sizeRepositoryJpa.save(sizeEntity));
	}

}
