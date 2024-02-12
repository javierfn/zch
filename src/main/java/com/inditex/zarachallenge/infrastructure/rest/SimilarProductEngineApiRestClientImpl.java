package com.inditex.zarachallenge.infrastructure.rest;

import java.util.List;
import java.util.Optional;
import java.util.prefs.PreferenceChangeEvent;
import javax.xml.validation.Validator;
import org.mockserver.model.HttpStatusCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.inditex.zarachallenge.domain.exception.SimilarProductsConnectionErrorException;
import com.inditex.zarachallenge.domain.exception.SimilarProductsErrorException;
import com.inditex.zarachallenge.domain.exception.SimilarProductsNotFoundException;
import com.inditex.zarachallenge.shared.Constants;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class SimilarProductEngineApiRestClientImpl implements SimilarProductEngineApiRestClient {

  private static final int MILLISECONDS_IN_ONE_SECOND = 1000;

  private final RestTemplate restTemplate;

  private final String customSimilarProductsApiUri;

  public SimilarProductEngineApiRestClientImpl(@Value("${custom.similar.product.api.uri}") String customSimilarProductsApiUri,
      @Value("${custom.similar.product.api.timeout}") Integer customSimilarProductsApiTimeOut) {

    this.restTemplate = new RestTemplate();
    this.restTemplate.setRequestFactory(getSimpleClientHttpRequestFactory(customSimilarProductsApiTimeOut));

    this.customSimilarProductsApiUri = customSimilarProductsApiUri;
  }

  private static SimpleClientHttpRequestFactory getSimpleClientHttpRequestFactory(Integer timeOutInSeconds) {

    var timeOutInMilliseconds = timeOutInSeconds * MILLISECONDS_IN_ONE_SECOND;

    var simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
    simpleClientHttpRequestFactory.setConnectTimeout(timeOutInMilliseconds);
    simpleClientHttpRequestFactory.setReadTimeout(timeOutInMilliseconds);

    return simpleClientHttpRequestFactory;
  }

  @Override
  public List<String> getSimilarProductsIds(@NonNull final Long productId) {

    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    ResponseEntity<List<String>> response;

    try {

      var typeReference = new ParameterizedTypeReference<List<String>>() {};
      response = restTemplate.exchange(customSimilarProductsApiUri.replace("{productId}", String.valueOf(productId)),
          HttpMethod.GET, null, typeReference);

    } catch (RestClientException rce) {

      if ( rce instanceof HttpClientErrorException) {
        var statusCode = Optional.ofNullable(((HttpClientErrorException) rce).getStatusCode())
            .map(org.springframework.http.HttpStatusCode::value)
            .orElse(500);

        if ( HttpStatusCode.NOT_FOUND_404.code() == statusCode ) {
          log.error("Similar products API response HTTP 404 NOT FOUND");
          throw new SimilarProductsNotFoundException();
        }

      }

      log.info(Constants.EXCEPTION_MESSAGE, rce.getMessage());
      log.info(Constants.EXCEPTION, rce);

      throw new SimilarProductsConnectionErrorException();
    }

    if (response.getStatusCode() != HttpStatus.OK) {
      log.error("Similar products API response: {}", response.getStatusCode());
      throw new SimilarProductsErrorException();
    }

    var result = response.getBody();

    if (result == null) {
      log.error("Similar products API response with body null");
      throw new SimilarProductsErrorException();
    }

    return response.getBody();
  }

}
