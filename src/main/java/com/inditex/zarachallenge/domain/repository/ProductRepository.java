package com.inditex.zarachallenge.domain.repository;

import com.inditex.zarachallenge.domain.model.Product;


public interface ProductRepository {

  Product findProductById(final Long productId);

}
