package com.inditex.zarachallenge.infrastructure.rest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.inditex.zarachallenge.domain.exception.SimilarProductsConnectionErrorException;
import com.inditex.zarachallenge.domain.exception.SimilarProductsErrorException;
import com.inditex.zarachallenge.domain.exception.SimilarProductsNotFoundException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Epic("Similar product management")
class SimilarProductEngineApiAdapterTest {

    EasyRandom generator;

    @Mock
    SimilarProductEngineApiRestClient similarProductEngineApiRestClient;

    SimilarProductEngineApiAdapter similarProductEngineApiAdapter;

    @BeforeEach
    void setup() {
        generator = new EasyRandom();

        similarProductEngineApiAdapter =
            new SimilarProductEngineApiAdapter(similarProductEngineApiRestClient);
    }

    @Test
    @Feature("Find similar products in api")
    @Story("Shall return error")
    void should_throw_SimilarProductsNotFoundException_when_getSimilarProductsIds_and_client_throw_SimilarProductsNotFoundException() {

        var productId = 1L;

        when(similarProductEngineApiRestClient.getSimilarProductsIds(anyLong()))
            .thenThrow(new SimilarProductsNotFoundException());

        assertThrows(SimilarProductsNotFoundException.class,
            () -> similarProductEngineApiAdapter.findSimilarProductsByProductId(productId),
            "Should throw similar products not found exception");
    }

    @Test
    @Feature("Find similar products in api")
    @Story("Shall return error")
    void should_throw_SimilarProductsConnectionErrorException_when_getSimilarProductsIds_and_client_throw_SimilarProductsConnectionErrorException() {

        var productId = 1L;

        when(similarProductEngineApiRestClient.getSimilarProductsIds(anyLong()))
            .thenThrow(new SimilarProductsConnectionErrorException());

        assertThrows(SimilarProductsConnectionErrorException.class,
            () -> similarProductEngineApiAdapter.findSimilarProductsByProductId(productId),
            "Should throw similar products connection error exception");
    }

    @Test
    @Feature("Find similar products in api")
    @Story("Shall return error")
    void should_throw_SimilarProductsErrorException_when_getSimilarProductsIds_and_client_throw_SimilarProductsErrorException() {

        var productId = 1L;

        when(similarProductEngineApiRestClient.getSimilarProductsIds(anyLong()))
            .thenThrow(new SimilarProductsErrorException());

        assertThrows(SimilarProductsErrorException.class,
            () -> similarProductEngineApiAdapter.findSimilarProductsByProductId(productId),
            "Should throw similar products error exception");
    }

    @Test
    @Feature("Find similar products in api")
    @Story("Shall return error")
    void should_throw_SimilarProductsNotFoundException_when_getSimilarProductsIds_and_api_return_an_empty_list() {

        var productId = 1L;

        List<String> similarProductsIdList = new ArrayList<>();

        when(similarProductEngineApiRestClient.getSimilarProductsIds(anyLong()))
            .thenReturn(similarProductsIdList);

        assertThrows(SimilarProductsNotFoundException.class,
            () -> similarProductEngineApiAdapter.findSimilarProductsByProductId(productId),
            "Should throw similar products not found exception");
    }

    @Test
    @Feature("Find similar products in api")
    @Story("Shall return error")
    void should_throw_SimilarProductsErrorException_when_getSimilarProductsIds_and_api_return_not_valid_product_id() {

        var productId = 1L;

        List<String> similarProductsIdList = new ArrayList<>();
        similarProductsIdList.add("1");
        similarProductsIdList.add("2A");
        similarProductsIdList.add("3");

        when(similarProductEngineApiRestClient.getSimilarProductsIds(anyLong()))
            .thenReturn(similarProductsIdList);

        assertThrows(SimilarProductsErrorException.class,
            () -> similarProductEngineApiAdapter.findSimilarProductsByProductId(productId),
            "Should throw similar products error exception");
    }



    @Test
    @Feature("Find similar products in api")
    @Story("Shall return similar product id that match")
    void should_retun_a_list_when_getSimilarProductsIds_and_api_return_a_valid_product_id_list() {

        var productId = 1L;

        List<String> similarProductsIdList = new ArrayList<>();
        similarProductsIdList.add("1");
        similarProductsIdList.add("2");
        similarProductsIdList.add("3");

        when(similarProductEngineApiRestClient.getSimilarProductsIds(anyLong()))
            .thenReturn(similarProductsIdList);

        var result = similarProductEngineApiAdapter.findSimilarProductsByProductId(productId);

        assertNotNull(result, "The result should not be null");
        assertFalse(result.isEmpty(), "Result should be not empty");
        assertEquals(3L, result.size(), "Result size should be 3");
        assertEquals(1L, result.get(0), "Result index 0 should equals");
        assertEquals(2L, result.get(1), "Result index 1 should equals");
        assertEquals(3L, result.get(2), "Result index 2 should equals");
    }

}
