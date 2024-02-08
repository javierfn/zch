package com.inditex.zarachallenge.infrastructure.persistance;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.inditex.zarachallenge.infrastructure.persistance.entity.OfferEntity;

public interface OfferRepositoryJpa extends JpaRepository<OfferEntity, Long> {

  List<OfferEntity> findByProductIdAndValidFromLessThanEqualOrderByValidFromDesc(Long productId, LocalDateTime date);

}
