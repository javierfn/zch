package com.inditex.zarachallenge.controller;

import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.inditex.zarachallenge.application.SimilarProductService;
import com.inditex.zarachallenge.infrastructure.dto.ProductDetailDTO;
import com.inditex.zarachallenge.infrastructure.mappers.ProductDetailMapper;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SimilarController implements SimilarControllerInterface {

  private final SimilarProductService similarProductService;

  private final ProductDetailMapper productDetailMapper;

  @Override
  public ResponseEntity<Set<ProductDetailDTO>> productProductIdSimilarGet(String productId) {
     return ResponseEntity.ok(
         new LinkedHashSet<>(
             productDetailMapper.toDto(
                 similarProductService.findSimilarProductsByProductId(Long.valueOf(productId))
             )
         )
     );
  }

}
