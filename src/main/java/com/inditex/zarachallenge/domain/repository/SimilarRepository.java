package com.inditex.zarachallenge.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import com.inditex.zarachallenge.domain.model.Offer;
import com.inditex.zarachallenge.domain.model.Product;
import com.inditex.zarachallenge.domain.model.Size;
import lombok.NonNull;


public interface SimilarRepository {

  Product findProductById(final Long productId);

  Offer findOfferByProductId(final Long productId, final LocalDateTime date);

  List<Size> findSizeAvailableByProductId(final Long productId);

}
