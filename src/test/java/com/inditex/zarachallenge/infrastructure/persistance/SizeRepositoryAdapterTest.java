package com.inditex.zarachallenge.infrastructure.persistance;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
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
    void should_throw_null_pointer_exception_when_findSizeListByProductId_and_product_id_is_null() {
        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class,
            () -> sizeRepositoryAdapter.findSizeListByProductId(null),
            "Should throw null pointer exception");
    }

    @Test
    @Feature("Find size")
    @Story("Shall return error")
    void should_throw_size_not_found_exception_when_findSizeListByProductId_and_there_are_not_size_that_match() {

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
    void should_return_size_data_when_findSizeListByProductId_and_there_are_size_that_match() {

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

    @Test
    @Feature("Update size")
    @Story("Shall return error")
    void should_throw_null_pointer_exception_when_updateAvailability_and_size_id_is_null() {

        var availability = Boolean.TRUE;
        var update = Timestamp.valueOf(LocalDateTime.now());

        assertThrows(NullPointerException.class,
            () -> sizeRepositoryAdapter.updateAvailability(null, availability, update),
            "Should throw null pointer exception");
    }

    @Test
    @Feature("Update size")
    @Story("Shall return error")
    void should_throw_null_pointer_exception_when_updateAvailability_and_availability_is_null() {

        var sizeId = 1L;
        var update = Timestamp.valueOf(LocalDateTime.now());

        assertThrows(NullPointerException.class,
            () -> sizeRepositoryAdapter.updateAvailability(sizeId, null, update),
            "Should throw null pointer exception");
    }

    @Test
    @Feature("Update size")
    @Story("Shall return error")
    void should_throw_null_pointer_exception_when_updateAvailability_and_update_is_null() {

        var sizeId = 1L;
        var availability = Boolean.TRUE;

        assertThrows(NullPointerException.class,
            () -> sizeRepositoryAdapter.updateAvailability(sizeId, availability, null),
            "Should throw null pointer exception");
    }

    @Test
    @Feature("Update size")
    @Story("Shall return error")
    void should_throw_size_not_found_exception_when_updateAvailability_and_size_id_not_found() {

        var sizeId = 1L;
        var availability = Boolean.TRUE;
        var update = Timestamp.valueOf(LocalDateTime.now());

        when(sizeRepositoryJpa.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(SizeNotFoundException.class,
            () -> sizeRepositoryAdapter.updateAvailability(sizeId, availability, update),
            "Should throw size not found exception");
    }

    @Test
    @Feature("Update size")
    @Story("Shall update size data")
    void should_update_size_availability_data_when_updateAvailability() {

        var sizeId = 1L;
        var availability = Boolean.TRUE;
        var update = Timestamp.valueOf(LocalDateTime.now());

        var sizeEntity = generator.nextObject(SizeEntity.class);
        sizeEntity.setSizeId(sizeId);
        sizeEntity.setAvailability(Boolean.FALSE);

        when(sizeRepositoryJpa.findById(anyLong())).thenReturn(Optional.of(sizeEntity));
        when(sizeRepositoryJpa.save(any(SizeEntity.class))).thenReturn(sizeEntity);

        var result = sizeRepositoryAdapter.updateAvailability(sizeId, availability, update);

        assertNotNull(result, "Result should be not null");
        assertEquals(sizeId, result.getSizeId(), "Size id should be equals");
        assertTrue(result.getAvailability(), "Availability should be true");
        assertEquals(update.toLocalDateTime(), result.getLastUpdated(), "LastUpdated should be equals");
    }

}
