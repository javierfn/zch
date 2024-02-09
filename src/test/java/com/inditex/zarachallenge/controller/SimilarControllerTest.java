package com.inditex.zarachallenge.controller;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.model.HttpStatusCode;
import com.inditex.zarachallenge.application.ProductServiceImpl;
import com.inditex.zarachallenge.application.SimilarProductService;
import com.inditex.zarachallenge.domain.exception.OfferNotFoundException;
import com.inditex.zarachallenge.domain.exception.ProductNotFoundException;
import com.inditex.zarachallenge.domain.exception.SizeNotFoundException;
import com.inditex.zarachallenge.domain.model.Offer;
import com.inditex.zarachallenge.domain.model.Product;
import com.inditex.zarachallenge.domain.model.ProductDetail;
import com.inditex.zarachallenge.domain.model.Size;
import com.inditex.zarachallenge.domain.repository.OfferRepository;
import com.inditex.zarachallenge.domain.repository.ProductRepository;
import com.inditex.zarachallenge.domain.repository.SizeRepository;
import com.inditex.zarachallenge.infrastructure.mappers.OfferMapper;
import com.inditex.zarachallenge.infrastructure.mappers.ProductDetailMapper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Epic("Product management")
class SimilarControllerTest {

    EasyRandom generator;

    ProductDetailMapper productDetailMapper;

    @Mock
    SimilarProductService similarProductService;

    SimilarController similarController;

    @BeforeEach
    void setup() {
        generator = new EasyRandom();

        productDetailMapper = Mappers.getMapper(ProductDetailMapper.class);

        similarController = new SimilarController(similarProductService, productDetailMapper);
    }

    @Test
    @Feature("Find similar products")
    @Story("Shall return error")
    void should_throw_number_format_exception_when_productProductIdSimilarGet_and_product_id_is_not_valid_string_number() {

        var productId = "productIdError";

        assertThrows(NumberFormatException.class,
            () -> similarController.productProductIdSimilarGet(productId),
        "Should throw number format exception");
    }

    @Test
    @Feature("Find similar products")
    @Story("Shall return product detail data")
    void should_return_http_ok_with_empty_list_when_productProductIdSimilarGet_and_there_are_not_similar_products() {

        var productId = "1";

        var productDetailList = new ArrayList<ProductDetail>();

        when(similarProductService.findSimilarProductsByProductId(anyLong()))
            .thenReturn(productDetailList);

        var result = similarController.productProductIdSimilarGet(productId);

        assertNotNull(result, "Result should be not null");
        assertEquals(HttpStatusCode.OK_200.code(), result.getStatusCode().value(), "Http status code should be 200 OK");
        assertNotNull(result.getBody(), "Body should be not null");
        assertTrue(result.getBody().isEmpty(), "Result set should be empty");
    }

    @Test
    @Feature("Find similar products")
    @Story("Shall return product detail data")
    void should_return_http_ok_with_a_list_when_productProductIdSimilarGet_and_there_are_similar_products() {

        var productId = "1";

        var productDetailList = generator.objects(ProductDetail.class,5)
            .collect(Collectors.toList());

        when(similarProductService.findSimilarProductsByProductId(anyLong()))
            .thenReturn(productDetailList);

        var result = similarController.productProductIdSimilarGet(productId);

        assertNotNull(result, "Result should be not null");
        assertEquals(HttpStatusCode.OK_200.code(),
            result.getStatusCode().value(),
            "Http status code should be 200 OK");
        assertNotNull(result.getBody(), "Body should be not null");
        assertFalse(result.getBody().isEmpty(), "Result set should be not empty");
        assertEquals(productDetailList.size(),
            result.getBody().size(),
            "Result set size should be equals");
    }

}
