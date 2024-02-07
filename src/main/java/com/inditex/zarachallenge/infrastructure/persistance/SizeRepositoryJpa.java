package com.inditex.zarachallenge.infrastructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inditex.zarachallenge.infrastructure.persistance.entity.OfferEntity;
import com.inditex.zarachallenge.infrastructure.persistance.entity.SizeEntity;

public interface SizeRepositoryJpa extends JpaRepository<SizeEntity, Long> {

}
