package com.inditex.zarachallenge.application;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
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
import org.springframework.beans.factory.annotation.Autowired;
import com.inditex.zarachallenge.domain.exception.OfferNotFoundException;
import com.inditex.zarachallenge.domain.exception.ProductNotFoundException;
import com.inditex.zarachallenge.domain.exception.SizeNotFoundException;
import com.inditex.zarachallenge.domain.model.Offer;
import com.inditex.zarachallenge.domain.model.Product;
import com.inditex.zarachallenge.domain.model.ProductDetail;
import com.inditex.zarachallenge.domain.repository.OfferRepository;
import com.inditex.zarachallenge.domain.repository.ProductRepository;
import com.inditex.zarachallenge.domain.repository.SizeRepository;
import com.inditex.zarachallenge.infrastructure.mappers.OfferMapper;
import com.inditex.zarachallenge.infrastructure.persistance.OfferRepositoryAdapter;
import com.inditex.zarachallenge.infrastructure.persistance.OfferRepositoryJpa;
import com.inditex.zarachallenge.infrastructure.persistance.ProductRepositoryJpa;
import com.inditex.zarachallenge.infrastructure.persistance.SizeRepositoryAdapter;
import com.inditex.zarachallenge.infrastructure.persistance.entity.OfferEntity;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Epic("Product management")
class ProductServiceTest {

    EasyRandom generator;

    @Spy
    ProductRepository productRepository;

    @Spy
    OfferRepository offerRepository;

    @Spy
    SizeRepository sizeRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @BeforeEach
    void setup() {
        generator = new EasyRandom();
    }

    @Test
    @Feature("Find product")
    @Story("Shall return product data that match")
    void should_return_product_data_when_find_offer_by_product_id_and_there_are_offer_that_match() {

        var productId = 1L;

        var product = generator.nextObject(Product.class);

        when(productRepository.findProductById(anyLong())).thenReturn(product);

        var result = productService.findProductById(productId);

        assertNotNull(result, "The result should not be null");

    }

    @Test
    @Feature("Find product details")
    @Story("Shall return error")
    void should_throw_null_pointer_exception_when_get_product_detail_by_product_id_and_product_id_is_null() {
        assertThrows(NullPointerException.class,
            () -> productService.getProductDetailByProductId(null),
            "Should throw null pointer exception");
    }

    @Test
    @Feature("Find product details")
    @Story("Shall return product detail data")
    void should_return_optional_empty_when_get_product_detail_by_product_id_and_product_not_found() {

        var productId = 1L;

        when(productRepository.findProductById(anyLong())).thenThrow(new ProductNotFoundException());

        var result = productService.getProductDetailByProductId(productId);

        assertNotNull(result, "Result should be not null");
        assertTrue(result.isEmpty(), "Result should be empty");
    }

    @Test
    @Feature("Find product details")
    @Story("Shall return product detail data")
    void should_return_optional_empty_when_get_product_detail_by_product_id_and_offer_not_found() {

        var productId = 1L;

        var product = generator.nextObject(Product.class);
        product.setId(productId);

        when(productRepository.findProductById(anyLong()))
            .thenReturn(product);
        when(offerRepository.findOfferByProductId(anyLong(), any(LocalDateTime.class)))
            .thenThrow(new OfferNotFoundException());

        var result = productService.getProductDetailByProductId(productId);

        assertNotNull(result, "Result should be not null");
        assertTrue(result.isEmpty(), "Result should be empty");
    }

    @Test
    @Feature("Find product details")
    @Story("Shall return product detail data")
    void should_return_optional_empty_when_get_product_detail_by_product_id_and_size_not_found() {

        var productId = 1L;

        var product = generator.nextObject(Product.class);
        product.setId(productId);

        var offer = generator.nextObject(Offer.class);
        offer.setProductId(productId);

        when(productRepository.findProductById(anyLong()))
            .thenReturn(product);
        when(offerRepository.findOfferByProductId(anyLong(), any(LocalDateTime.class)))
            .thenReturn(offer);
        when(sizeRepository.findSizeAvailableByProductId(anyLong()))
            .thenThrow(new SizeNotFoundException());

        var result = productService.getProductDetailByProductId(productId);

        assertNotNull(result, "Result should be not null");
        assertTrue(result.isEmpty(), "Result should be empty");
    }

}
