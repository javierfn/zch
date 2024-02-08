package com.inditex.zarachallenge.application;

import java.util.List;
import com.inditex.zarachallenge.domain.model.ProductDetail;

public interface SimilarService {

  List<ProductDetail> findSimilarProductsByProductId (Long productId);

}
