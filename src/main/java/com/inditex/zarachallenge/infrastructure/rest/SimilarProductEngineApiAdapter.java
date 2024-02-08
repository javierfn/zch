package com.inditex.zarachallenge.infrastructure.rest;

import java.util.List;

public interface SimilarProductEngineApiAdapter {

  List<Long> findSimilarProductsByProductId(final Long productId);

}
