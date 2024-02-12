package com.inditex.zarachallenge.infrastructure.rest;

import java.util.List;
import lombok.NonNull;

public interface SimilarProductEngineApiRestClient {

  List<String> getSimilarProductsIds(Long productId);

}
