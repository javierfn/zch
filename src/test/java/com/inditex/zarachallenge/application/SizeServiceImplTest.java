package com.inditex.zarachallenge.application;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import com.inditex.zarachallenge.domain.model.Size;
import com.inditex.zarachallenge.domain.repository.SizeRepository;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Epic("Size management")
class SizeServiceImplTest {

    EasyRandom generator;

    @Spy
    SizeRepository sizeRepository;

    @InjectMocks
    SizeServiceImpl sizeService;

    @BeforeEach
    void setup() {
        generator = new EasyRandom();
    }

    @Test
    @Feature("Update size")
    @Story("Shall update size data")
    void should_update_size_availability_data_when_updateAvailability() {

        var sizeId = 1L;
        var availability = Boolean.TRUE;
        var update = Timestamp.valueOf(LocalDateTime.now());

        var size = generator.nextObject(Size.class);
        size.setSizeId(sizeId);

        when(sizeRepository.updateAvailability(anyLong(), anyBoolean(), any(Timestamp.class)))
            .thenReturn(size);

        var result = sizeRepository.updateAvailability(sizeId,availability,update);

        assertNotNull(result, "The result should not be null");

    }


}
