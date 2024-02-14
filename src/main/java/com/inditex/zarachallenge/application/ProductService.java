package com.inditex.zarachallenge.application;

import java.util.Optional;
import com.inditex.zarachallenge.domain.model.Product;
import com.inditex.zarachallenge.domain.model.ProductDetail;

public interface ProductService {

  Product findProductById(final Long productId);

  Optional<ProductDetail> getProductDetailByProductId(final Long productId);

}
