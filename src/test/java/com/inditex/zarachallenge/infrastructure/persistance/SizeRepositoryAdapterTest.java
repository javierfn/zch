package com.inditex.zarachallenge.infrastructure.persistance;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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
import com.inditex.zarachallenge.domain.exception.SizeNotFoundException;
import com.inditex.zarachallenge.infrastructure.mappers.SizeMapper;
import com.inditex.zarachallenge.infrastructure.persistance.entity.SizeEntity;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Epic("Size management")
class SizeRepositoryAdapterTest {

    EasyRandom generator;

    SizeMapper sizeMapper;

    @Mock
    SizeRepositoryJpa sizeRepositoryJpa;

    SizeRepositoryAdapter sizeRepositoryAdapter;

    @BeforeEach
    void setup() {
        generator = new EasyRandom();

        sizeMapper = Mappers.getMapper(SizeMapper.class);

        sizeRepositoryAdapter = new SizeRepositoryAdapter(sizeRepositoryJpa, sizeMapper);
    }

    @Test
    @Feature("Find size")
    @Story("Shall return error")
    void should_throw_null_pointer_exception_when_find_size_available_by_product_id_and_product_id_is_null() {
        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class,
            () -> sizeRepositoryAdapter.findSizeListByProductId(null),
            "Should throw null pointer exception");
    }

    @Test
    @Feature("Find size")
    @Story("Shall return error")
    void should_throw_size_not_found_exception_when_find_size_available_by_product_id_and_there_are_not_size_that_match() {

        var productId = 1L;

        when(sizeRepositoryJpa
            .findByProductIdOrderBySizeAsc(anyLong()))
            .thenReturn(Collections.emptyList());

        assertThrows(SizeNotFoundException.class,
            () -> sizeRepositoryAdapter.findSizeListByProductId(productId),
            "Should throw size not found exception");
    }

    @Test
    @Feature("Find size")
    @Story("Shall return size data that match")
    void should_return_size_data_when_find_size_available_by_product_id_and_there_are_size_that_match() {

        var productId = 1L;

        var sizeEntityList = generator.objects(SizeEntity.class, 5).toList();
        sizeEntityList.forEach( entity -> entity.setProductId(productId));

        when(sizeRepositoryJpa
            .findByProductIdOrderBySizeAsc(anyLong()))
            .thenReturn(sizeEntityList);

        var result = sizeRepositoryAdapter.findSizeListByProductId(productId);

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
