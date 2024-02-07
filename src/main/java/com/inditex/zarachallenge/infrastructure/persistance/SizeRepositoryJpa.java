package com.inditex.zarachallenge.infrastructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inditex.zarachallenge.infrastructure.persistance.entity.OfferEntity;

public interface SizeRepositoryJpa extends JpaRepository<SizeRepositoryJpa, Long> {

}
