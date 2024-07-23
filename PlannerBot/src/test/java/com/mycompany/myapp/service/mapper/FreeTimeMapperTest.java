package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.FreeTimeAsserts.*;
import static com.mycompany.myapp.domain.FreeTimeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FreeTimeMapperTest {

    private FreeTimeMapper freeTimeMapper;

    @BeforeEach
    void setUp() {
        freeTimeMapper = new FreeTimeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFreeTimeSample1();
        var actual = freeTimeMapper.toEntity(freeTimeMapper.toDto(expected));
        assertFreeTimeAllPropertiesEquals(expected, actual);
    }
}
