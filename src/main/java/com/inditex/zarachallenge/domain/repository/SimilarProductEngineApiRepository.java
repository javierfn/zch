package com.inditex.zarachallenge.domain.repository;

import java.util.List;

public interface SimilarProductEngineApiRepository {

  List<Long> findSimilarProductsByProductId(final Long productId);

}
