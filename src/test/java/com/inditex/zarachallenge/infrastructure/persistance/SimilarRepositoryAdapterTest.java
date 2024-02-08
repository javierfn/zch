package com.inditex.zarachallenge.infrastructure.persistance;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.inditex.zarachallenge.domain.exception.ProductNotFoundException;
import com.inditex.zarachallenge.infrastructure.mappers.ProductMapper;
import com.inditex.zarachallenge.infrastructure.persistance.entity.ProductEntity;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Epic("Similar product management")
class SimilarRepositoryAdapterTest {

    EasyRandom generator;
    ProductMapper productMapper;
    @Mock
    ProductRepositoryJpa productRepositoryJpa;

    @Mock
    OfferRepositoryJpa offerRepositoryJpa;

    @Mock
    SizeRepositoryJpa sizeRepositoryJpa;

    SimilarRepositoryAdapter similarRepositoryAdapter;

    @BeforeEach
    void setup() {
        generator = new EasyRandom();

        productMapper = Mappers.getMapper(ProductMapper.class);
        similarRepositoryAdapter = new SimilarRepositoryAdapter(productRepositoryJpa,
            offerRepositoryJpa, sizeRepositoryJpa, productMapper);
    }

    @Test
    @Feature("Find")
    @Story("Shall return error")
    void should_throw_null_pointer_exception_when_find_product_by_id_and_product_id_is_null() {
        assertThrows(NullPointerException.class,
            () -> similarRepositoryAdapter.findProductById(null),
            "Should throw null pointer exception");
    }


    @Test
    @Feature("Find")
    @Story("Shall return error")
    void should_throw_product_not_found_exception_when_find_product_by_id_and_there_are_not_product_that_match() {

        var productId = 1L;

        when(productRepositoryJpa.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
            () -> similarRepositoryAdapter.findProductById(productId),
            "Should throw product not found exception");
    }

    @Test
    @Feature("Find")
    @Story("Shall return price data with that match")
    void should_return_product_data_when_find_product_by_id_and_there_are_product_that_match() {

        var productId = 1L;

        var productEntity = generator.nextObject(ProductEntity.class);
        productEntity.setId(productId);

        when(productRepositoryJpa.findById(anyLong())).thenReturn(Optional.of(productEntity));

        var result = similarRepositoryAdapter.findProductById(productId);

        assertNotNull(result, "The result should not be null");
        assertAll(
                () -> assertEquals(productId, result.getId(), "Id should be equals"),
                () -> assertNotNull(result.getName(), "Name should be not null")
        );

    }

}
