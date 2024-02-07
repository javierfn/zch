package com.inditex.zarachallenge.infrastructure.rest.similarProductEngineAPI.client;

import com.inditex.zarachallenge.infrastructure.rest.similarProductEngineAPI.invoker.ApiClient;

import java.util.Set;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class DefaultClientApi {
    private ApiClient apiClient;

    public DefaultClientApi() {
        this(new ApiClient());
    }

    public DefaultClientApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the ids of the similar products
     * Returns the similar products to a given one ordered by similarity
     * <p><b>200</b> - OK
     * @param productId  (required)
     * @return Set&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Set<String> getProductSimilarids(String productId) throws RestClientException {
        return getProductSimilaridsWithHttpInfo(productId).getBody();
    }

    /**
     * Gets the ids of the similar products
     * Returns the similar products to a given one ordered by similarity
     * <p><b>200</b> - OK
     * @param productId  (required)
     * @return ResponseEntity&lt;Set&lt;String&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Set<String>> getProductSimilaridsWithHttpInfo(String productId) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'productId' is set
        if (productId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'productId' when calling getProductSimilarids");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("productId", productId);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<Set<String>> localReturnType = new ParameterizedTypeReference<Set<String>>() {};
        return apiClient.invokeAPI("/product/{productId}/similarids", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
}
