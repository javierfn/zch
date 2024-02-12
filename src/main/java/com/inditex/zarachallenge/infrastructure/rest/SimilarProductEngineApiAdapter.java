package com.inditex.zarachallenge.infrastructure.rest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.inditex.zarachallenge.domain.exception.SimilarProductsConnectionErrorException;
import com.inditex.zarachallenge.domain.exception.SimilarProductsErrorException;
import com.inditex.zarachallenge.domain.exception.SimilarProductsNotFoundException;
import com.inditex.zarachallenge.domain.repository.SimilarProductEngineApiRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
@Transactional
public class SimilarProductEngineApiAdapter implements SimilarProductEngineApiRepository {

  private static final String
      ERROR_AL_CONSULTAR_LA_API_DE_PRODUCTOS_SIMILARES_PARA_EL_ID_DE_PRODUCTO =
      "Se ha producido un error al consultar la API de productos similares para el id de producto: {}";

  private final SimilarProductEngineApiRestClient similarProductEngineApiRestClient;


  @Override
  @Cacheable(cacheNames="similarProductsEngineApi", key="'findSimilarProductsByProductId_productId_'+ #productId",  unless = "#result.isEmpty() || #exception != null")
  public List<Long> findSimilarProductsByProductId(final Long productId) {

    var response = similarProductEngineApiRestClient.getSimilarProductsIds(productId);

    if ( response.isEmpty()) {
      throw new SimilarProductsNotFoundException();
    }

    var result = new ArrayList<Long>();

    response.forEach(strProductId -> {
      try {
        result.add(Long.valueOf(strProductId));
      } catch (NumberFormatException e) {
        log.error("Se ha producido un error al convertir a long la cadena de texto: {}", strProductId);
        throw new SimilarProductsErrorException();
      }
    });

    return result;
  }

}
