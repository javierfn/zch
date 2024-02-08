package com.inditex.zarachallenge.infrastructure.persistance;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.inditex.zarachallenge.domain.exception.OfferNotFoundException;
import com.inditex.zarachallenge.infrastructure.mappers.OfferMapper;
import com.inditex.zarachallenge.infrastructure.persistance.entity.OfferEntity;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Epic("Offer management")
class OfferRepositoryAdapterTest {

    EasyRandom generator;
    OfferMapper offerMapper;

    @Mock
    OfferRepositoryJpa offerRepositoryJpa;

    OfferRepositoryAdapter offerRepositoryAdapter;

    @BeforeEach
    void setup() {
        generator = new EasyRandom();

        offerMapper = Mappers.getMapper(OfferMapper.class);

        offerRepositoryAdapter = new OfferRepositoryAdapter(offerRepositoryJpa, offerMapper);
    }

    @Test
    @Feature("Find offer")
    @Story("Shall return error")
    void should_throw_null_pointer_exception_when_find_offer_by_product_id_and_product_id_is_null() {

        var date = LocalDateTime.now();

        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class,
            () -> offerRepositoryAdapter.findOfferByProductId(null, date),
            "Should throw null pointer exception");
    }

    @Test
    @Feature("Find offer")
    @Story("Shall return error")
    void should_throw_null_pointer_exception_when_find_offer_by_product_id_and_date_is_null() {

        var productId = 1L;

        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class,
            () -> offerRepositoryAdapter.findOfferByProductId(productId, null),
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
            () -> offerRepositoryAdapter.findOfferByProductId(productId, date),
            "Should throw offer not found exception");
    }

    @Test
    @Feature("Find offer")
    @Story("Shall return offer data that match")
    void should_return_offer_data_when_find_offer_by_product_id_and_there_are_offer_that_match() {

        var productId = 1L;
        var date = LocalDateTime.now();

        var offerEntityList = generator.objects(OfferEntity.class, 5).toList();
        offerEntityList.forEach( entity -> entity.setProductId(productId));

        when(offerRepositoryJpa
            .findByProductIdAndValidFromLessThanEqualOrderByValidFromDesc(anyLong(), any(LocalDateTime.class)))
            .thenReturn(offerEntityList);

        var result = offerRepositoryAdapter.findOfferByProductId(productId, date);

        assertNotNull(result, "The result should not be null");
        //noinspection OptionalGetWithoutIsPresent
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

}
