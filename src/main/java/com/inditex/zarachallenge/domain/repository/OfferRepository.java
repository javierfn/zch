package com.inditex.zarachallenge.domain.repository;

import java.time.LocalDateTime;
import com.inditex.zarachallenge.domain.model.Offer;


public interface OfferRepository {

  Offer findOfferByProductId(final Long productId, final LocalDateTime date);

}
