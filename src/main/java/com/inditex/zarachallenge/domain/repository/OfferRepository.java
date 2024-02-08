package com.inditex.zarachallenge.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import com.inditex.zarachallenge.domain.model.Offer;
import com.inditex.zarachallenge.domain.model.Product;
import com.inditex.zarachallenge.domain.model.Size;


public interface OfferRepository {

  Offer findOfferByProductId(final Long productId, final LocalDateTime date);

}
