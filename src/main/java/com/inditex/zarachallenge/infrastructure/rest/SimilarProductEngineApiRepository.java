package com.inditex.zarachallenge.infrastructure.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mockserver.model.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.inditex.zarachallenge.domain.exception.SimilarProductsErrorException;
import com.inditex.zarachallenge.infrastructure.rest.similarProductEngineAPI.client.DefaultClientApi;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
public class SimilarProductEngineApiRepository
    implements com.inditex.zarachallenge.domain.repository.SimilarProductEngineApiRepository {

  private static final String
      ERROR_AL_CONSULTAR_LA_API_DE_PRODUCTOS_SIMILARES_PARA_EL_ID_DE_PRODUCTO =
      "Se ha producido un error al consultar la API de productos similares para el id de producto: {}";

  private final DefaultClientApi similarProductEngineClientApi;

  public SimilarProductEngineApiRepository() {
    this.similarProductEngineClientApi = new DefaultClientApi();
  }

  public SimilarProductEngineApiRepository(DefaultClientApi similarProductEngineClientApi) {
    this.similarProductEngineClientApi = similarProductEngineClientApi;
  }

  @Override
  public List<Long> findSimilarProductsByProductId(@NonNull final Long productId) {

    var response = Optional.ofNullable(
            similarProductEngineClientApi.getProductSimilaridsWithHttpInfo(String.valueOf(productId)))
        .orElseThrow( () -> {
          log.error(
              ERROR_AL_CONSULTAR_LA_API_DE_PRODUCTOS_SIMILARES_PARA_EL_ID_DE_PRODUCTO,
              productId);
          log.error("La API responde null");;

          throw new SimilarProductsErrorException();
        });

    if ( ! (HttpStatusCode.OK_200.equals(response.getStatusCode()))) {

      log.error(
          ERROR_AL_CONSULTAR_LA_API_DE_PRODUCTOS_SIMILARES_PARA_EL_ID_DE_PRODUCTO,
          productId);

      log.error("HTTP STATUS: {}", response.getStatusCode());

      throw new SimilarProductsErrorException();

    }

    var productIdResultList = Optional
        .ofNullable(response.getBody().stream().toList())
        .orElse(new ArrayList<>());

    var result = new ArrayList<Long>();

    productIdResultList.forEach(strProductId -> {
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
