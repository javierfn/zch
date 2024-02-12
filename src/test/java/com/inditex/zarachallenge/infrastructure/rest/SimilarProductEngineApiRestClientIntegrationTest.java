package com.inditex.zarachallenge.infrastructure.rest;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import com.inditex.zarachallenge.domain.exception.SimilarProductsNotFoundException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@SpringBootTest
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("acceptance")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Epic("Similar product management")
class SimilarProductEngineApiRestClientIntegrationTest {

    EasyRandom generator;

    @Autowired
    SimilarProductEngineApiRestClient similarProductEngineApiRestClient;

    @BeforeEach
    void setup() {
        generator = new EasyRandom();
    }

    @Test
    @Feature("Find similar products in api")
    @Story("Shall return error")
    void should_throw_NullPointerException_when_getSimilarProductsIds_and_productId_is_null() {

        assertThrows(NullPointerException.class,
            () -> similarProductEngineApiRestClient.getSimilarProductsIds(null),
            "Should throw null pointer exception");
    }

    @Test
    @Feature("Find similar products in api")
    @Story("Shall return error")
    void should_throw_SimilarProductsNotFoundException_when_getSimilarProductsIds_and_productId_not_found() {

        var productId = 1581L;

        assertThrows(SimilarProductsNotFoundException.class,
            () -> similarProductEngineApiRestClient.getSimilarProductsIds(productId),
            "Should throw similar products not found exception");
    }

    @Test
    @Feature("Find similar products in api")
    @Story("Shall return similar product id that match")
    void should_retun_a_list_when_getSimilarProductsIds_and_api_return_a_valid_product_id_list() {

        var productId = 1L;

        var result = similarProductEngineApiRestClient.getSimilarProductsIds(productId);

        assertNotNull(result, "Result should be not null");
        assertFalse(result.isEmpty(), "Result list should be not empty");
    }

}
