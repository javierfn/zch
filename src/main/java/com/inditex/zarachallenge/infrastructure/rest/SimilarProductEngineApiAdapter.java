package com.inditex.zarachallenge.infrastructure.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.mockserver.model.HttpStatusCode;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import com.inditex.zarachallenge.domain.exception.SimilarProductsConnectionErrorException;
import com.inditex.zarachallenge.domain.exception.SimilarProductsErrorException;
import com.inditex.zarachallenge.domain.exception.SimilarProductsNotFoundException;
import com.inditex.zarachallenge.domain.repository.SimilarProductEngineApiRepository;
import com.inditex.zarachallenge.infrastructure.rest.similarProductEngineAPI.client.DefaultClientApi;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
public class SimilarProductEngineApiAdapter implements SimilarProductEngineApiRepository {

  private static final String
      ERROR_AL_CONSULTAR_LA_API_DE_PRODUCTOS_SIMILARES_PARA_EL_ID_DE_PRODUCTO =
      "Se ha producido un error al consultar la API de productos similares para el id de producto: {}";

  private final DefaultClientApi similarProductEngineClientApi;

  public SimilarProductEngineApiAdapter() {
    this.similarProductEngineClientApi = new DefaultClientApi();
  }

  public SimilarProductEngineApiAdapter(final DefaultClientApi similarProductEngineClientApi) {
    this.similarProductEngineClientApi = similarProductEngineClientApi;
  }

  @Override
  @Cacheable(cacheNames="similarProductsEngineApi", key="'findSimilarProductsByProductId_productId_'+ #productId",  unless = "#result.isEmpty() || #exception != null")
  public List<Long> findSimilarProductsByProductId(@NonNull final Long productId) {

    ResponseEntity<Set<String>> response;
    try {

      response = similarProductEngineClientApi
          .getProductSimilaridsWithHttpInfo(String.valueOf(productId));

    } catch (Exception e) {

      if ( e instanceof HttpClientErrorException) {
        var statusCode = Optional.ofNullable(((HttpClientErrorException) e).getStatusCode())
            .map(org.springframework.http.HttpStatusCode::value)
            .orElse(500);

        if ( HttpStatusCode.NOT_FOUND_404.code() == statusCode ) {
          throw new SimilarProductsNotFoundException();
        }

      }

      log.error(
          ERROR_AL_CONSULTAR_LA_API_DE_PRODUCTOS_SIMILARES_PARA_EL_ID_DE_PRODUCTO,
          productId);

      log.error("Exception message: {}", e.getMessage());
      log.error("Exception: ",e);

      throw new SimilarProductsConnectionErrorException();
    }

    if ( response == null ) {
      log.error(
          ERROR_AL_CONSULTAR_LA_API_DE_PRODUCTOS_SIMILARES_PARA_EL_ID_DE_PRODUCTO,
          productId);
      log.error("Similar products API responde null");;

      throw new SimilarProductsErrorException();
    }

    if ( HttpStatus.NOT_FOUND.value() == response.getStatusCode().value() ) {
      throw new SimilarProductsNotFoundException();
    }

    if ( ! (HttpStatus.OK.value() == response.getStatusCode().value()) ) {

      log.error(
          ERROR_AL_CONSULTAR_LA_API_DE_PRODUCTOS_SIMILARES_PARA_EL_ID_DE_PRODUCTO,
          productId);

      log.error("HTTP STATUS: {}", response.getStatusCode());

      throw new SimilarProductsErrorException();

    }

    if ( response.getBody().isEmpty() ) {
      log.error("La respuesta de la API de productos similares es una lista vacía");
      throw new SimilarProductsNotFoundException();
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
