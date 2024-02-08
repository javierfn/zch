package com.inditex.zarachallenge.infrastructure.rest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import com.inditex.zarachallenge.domain.exception.SimilarProductsErrorException;
import com.inditex.zarachallenge.infrastructure.rest.similarProductEngineAPI.client.DefaultClientApi;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Epic("Similar product management")
class SimilarProductEngineApiRepositoryTest {

    EasyRandom generator;

    @Mock
    DefaultClientApi similarProductEngineClientApi;

    SimilarProductEngineApiRepository similarProductEngineApiAdapter;

    @BeforeEach
    void setup() {
        generator = new EasyRandom();

        similarProductEngineApiAdapter =
            new SimilarProductEngineApiRepository(similarProductEngineClientApi);
    }

    @Test
    @Feature("Find similar products in api")
    @Story("Shall return error")
    void should_throw_null_pointer_exception_when_find_similar_products_by_product_id_and_product_id_is_null() {
        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class,
            () -> similarProductEngineApiAdapter.findSimilarProductsByProductId(null),
            "Should throw null pointer exception");
    }

    @Test
    @Feature("Find similar products in api")
    @Story("Shall return error")
    void should_throw_similar_products_error_exception_when_find_similar_products_by_product_id_and_api_return_null() {

        var productId = 1L;

        when(similarProductEngineClientApi.getProductSimilaridsWithHttpInfo(anyString()))
            .thenReturn(null);

        assertThrows(SimilarProductsErrorException.class,
            () -> similarProductEngineApiAdapter.findSimilarProductsByProductId(productId),
            "Should throw similar products error exception");
    }

    @Test
    @Feature("Find similar products in api")
    @Story("Shall return error")
    void should_throw_similar_products_error_exception_when_find_similar_products_by_product_id_and_api_return_not_valid_product_id() {

        var productId = 1L;

        Set<String> similarProductsIdList = new HashSet<>();
        similarProductsIdList.add("1");
        similarProductsIdList.add("2A");
        similarProductsIdList.add("3");

        when(similarProductEngineClientApi.getProductSimilaridsWithHttpInfo(anyString()))
            .thenReturn(ResponseEntity.ok(similarProductsIdList));

        assertThrows(SimilarProductsErrorException.class,
            () -> similarProductEngineApiAdapter.findSimilarProductsByProductId(productId),
            "Should throw similar products error exception");
    }

    @Test
    @Feature("Find similar products in api")
    @Story("Shall return similar product id that match")
    void should_return_empty_list_when_find_similar_products_by_product_id_and_api_return_http_200_with_product_id_empty_list() {

        var productId = 1L;

        Set<String> similarProductsIdList = new HashSet<>();

        when(similarProductEngineClientApi.getProductSimilaridsWithHttpInfo(anyString()))
            .thenReturn(ResponseEntity.ok(similarProductsIdList));

        var result = similarProductEngineApiAdapter.findSimilarProductsByProductId(productId);

        assertNotNull(result, "The result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty");
    }

    @Test
    @Feature("Find similar products in api")
    @Story("Shall return similar product id that match")
    void should_return_list_when_find_similar_products_by_product_id_and_api_return_http_200_with_a_product_id_list() {

        var productId = 1L;

        Set<String> similarProductsIdList = new HashSet<>();
        similarProductsIdList.add("1");
        similarProductsIdList.add("2");
        similarProductsIdList.add("3");

        when(similarProductEngineClientApi.getProductSimilaridsWithHttpInfo(anyString()))
            .thenReturn(ResponseEntity.ok(similarProductsIdList));

        var result = similarProductEngineApiAdapter.findSimilarProductsByProductId(productId);

        assertNotNull(result, "The result should not be null");
        assertFalse(result.isEmpty(), "Result should be not empty");
        assertEquals(2L, result.size(), "Result size should be 3");
        assertEquals(1L, result.get(0), "Result index 0 should equals");
        assertEquals(2L, result.get(1), "Result index 1 should equals");
        assertEquals(3L, result.get(2), "Result index 2 should equals");
    }

}
