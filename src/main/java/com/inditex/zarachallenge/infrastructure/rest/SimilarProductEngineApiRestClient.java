package com.inditex.zarachallenge.infrastructure.rest;

import java.util.List;

public interface SimilarProductEngineApiRestClient {

  List<String> getSimilarProductsIds(Long productId);

}
