package com.inditex.zarachallenge.infrastructure.persistance;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
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
import com.inditex.zarachallenge.domain.exception.OfferNotFoundException;
import com.inditex.zarachallenge.domain.exception.ProductNotFoundException;
import com.inditex.zarachallenge.domain.exception.SizeNotFoundException;
import com.inditex.zarachallenge.infrastructure.mappers.OfferMapper;
import com.inditex.zarachallenge.infrastructure.mappers.ProductMapper;
import com.inditex.zarachallenge.infrastructure.mappers.SizeMapper;
import com.inditex.zarachallenge.infrastructure.persistance.entity.OfferEntity;
import com.inditex.zarachallenge.infrastructure.persistance.entity.ProductEntity;
import com.inditex.zarachallenge.infrastructure.persistance.entity.SizeEntity;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Epic("Similar product management")
class SimilarRepositoryAdapterTest {

    EasyRandom generator;
    ProductMapper productMapper;

    OfferMapper offerMapper;

    SizeMapper sizeMapper;

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
        offerMapper = Mappers.getMapper(OfferMapper.class);
        sizeMapper = Mappers.getMapper(SizeMapper.class);

        similarRepositoryAdapter = new SimilarRepositoryAdapter(productRepositoryJpa,
            offerRepositoryJpa, sizeRepositoryJpa, productMapper, offerMapper, sizeMapper);
    }

    @Test
    @Feature("Find product")
    @Story("Shall return error")
    void should_throw_null_pointer_exception_when_find_product_by_id_and_product_id_is_null() {
        assertThrows(NullPointerException.class,
            () -> similarRepositoryAdapter.findProductById(null),
            "Should throw null pointer exception");
    }

    @Test
    @Feature("Find product")
    @Story("Shall return error")
    void should_throw_product_not_found_exception_when_find_product_by_id_and_there_are_not_product_that_match() {

        var productId = 1L;

        when(productRepositoryJpa.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
            () -> similarRepositoryAdapter.findProductById(productId),
            "Should throw product not found exception");
    }

    @Test
    @Feature("Find product")
    @Story("Shall return product data that match")
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

    @Test
    @Feature("Find offer")
    @Story("Shall return error")
    void should_throw_null_pointer_exception_when_find_offer_by_product_id_and_product_id_is_null() {

        var date = LocalDateTime.now();

        assertThrows(NullPointerException.class,
            () -> similarRepositoryAdapter.findOfferByProductId(null, date),
            "Should throw null pointer exception");
    }

    @Test
    @Feature("Find offer")
    @Story("Shall return error")
    void should_throw_null_pointer_exception_when_find_offer_by_product_id_and_date_is_null() {

        var productId = 1L;

        assertThrows(NullPointerException.class,
            () -> similarRepositoryAdapter.findOfferByProductId(productId, null),
            "Should throw null pointer exception");
    }

    @Test
    @Feature("Find offer")
    @Story("Shall return error")
    void should_throw_offer_not_found_exception_when_find_offer_by_product_id_and_there_are_not_offer_that_match() {

        var productId = 1L;
        var date = LocalDateTime.now();

        when(offerRepositoryJpa
            .findByProductIdAndValidFromLessThanEqualOrderByValidFromDesc(anyLong(), any(LocalDateTime.class)))
            .thenReturn(Collections.emptyList());

        assertThrows(OfferNotFoundException.class,
            () -> similarRepositoryAdapter.findProductById(productId),
            "Should throw offer not found exception");
    }

    @Test
    @Feature("Find offer")
    @Story("Shall return offer data that match")
    void should_return_offer_data_when_find_offer_by_product_id_and_there_are_offer_that_match() {

        var productId = 1L;
        var date = LocalDateTime.now();

        var offerEntityList = generator.objects(OfferEntity.class, 5).toList();
        offerEntityList.forEach( entity -> {
            entity.setProductId(productId);
        });

        when(offerRepositoryJpa
            .findByProductIdAndValidFromLessThanEqualOrderByValidFromDesc(anyLong(), any(LocalDateTime.class)))
            .thenReturn(offerEntityList);

        var result = similarRepositoryAdapter.findOfferByProductId(productId, date);

        assertNotNull(result, "The result should not be null");
        assertAll(
            () -> assertEquals(productId, result.getProductId(), "Product Id should be equals"),
            () -> assertEquals(
                offerEntityList.stream().findFirst().get().getId(),
                result.getId(),
                "Product Id should be equals"),
            () -> assertNotNull(result.getValidFrom(), "Valid from should be not null"),
            () -> assertNotNull(result.getPrice(), "Price should be not null")
        );

    }

    @Test
    @Feature("Find size")
    @Story("Shall return error")
    void should_throw_null_pointer_exception_when_find_size_available_by_product_id_and_product_id_is_null() {
        assertThrows(NullPointerException.class,
            () -> similarRepositoryAdapter.findSizeAvailableByProductId(null),
            "Should throw null pointer exception");
    }

    @Test
    @Feature("Find size")
    @Story("Shall return error")
    void should_throw_size_not_found_exception_when_find_size_available_by_product_id_and_there_are_not_size_that_match() {

        var productId = 1L;

        when(sizeRepositoryJpa
            .findByProductIdAndAvailabilityOrderBySizeAsc(anyLong(), anyBoolean()))
            .thenReturn(Collections.emptyList());

        assertThrows(SizeNotFoundException.class,
            () -> similarRepositoryAdapter.findSizeAvailableByProductId(productId),
            "Should throw size not found exception");
    }

    @Test
    @Feature("Find size")
    @Story("Shall return size data that match")
    void should_return_size_data_when_find_size_available_by_product_id_and_there_are_size_that_match() {

        var productId = 1L;

        var sizeEntityList = generator.objects(SizeEntity.class, 5).toList();
        sizeEntityList.forEach( entity -> {
            entity.setProductId(productId);
        });

        when(sizeRepositoryJpa
            .findByProductIdAndAvailabilityOrderBySizeAsc(anyLong(), anyBoolean()))
            .thenReturn(sizeEntityList);

        var result = similarRepositoryAdapter.findSizeAvailableByProductId(productId);

        assertNotNull(result, "The result should not be null");
        assertAll(
            () -> assertEquals(sizeEntityList.size(), result.size(), "Size should be equals"),
            () -> assertEquals(0L,
                result.stream().filter(size -> size.getSize() == null).count(),
                "Size should be not null"),
            () -> assertEquals(0L,
                result.stream().filter(size -> size.getAvailability() == null).count(),
                "Availability should be not null"),
            () -> assertEquals(0L,
                result.stream().filter(size -> size.getLastUpdated() == null).count(),
                "Last updated should be not null"),
            () -> assertEquals(0L,
                result.stream().filter(size -> size.getProductId() == null).count(),
                "Product Id should be not null")

        );

    }

}
