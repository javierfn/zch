package com.inditex.zarachallenge.infrastructure.persistance;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.inditex.zarachallenge.infrastructure.persistance.entity.OfferEntity;
import com.inditex.zarachallenge.infrastructure.persistance.entity.SizeEntity;

public interface SizeRepositoryJpa extends JpaRepository<SizeEntity, Long> {

  List<SizeEntity> findByProductIdOrderBySizeAsc(Long productId);

}
