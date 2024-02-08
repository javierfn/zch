package com.inditex.zarachallenge.domain.repository;

import com.inditex.zarachallenge.domain.model.Product;


public interface SimilarRepository {

  Product findProductById(Long productId);

}
