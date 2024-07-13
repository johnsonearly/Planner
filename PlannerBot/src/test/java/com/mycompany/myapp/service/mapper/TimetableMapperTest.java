package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.TimetableAsserts.*;
import static com.mycompany.myapp.domain.TimetableTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimetableMapperTest {

    private TimetableMapper timetableMapper;

    @BeforeEach
    void setUp() {
        timetableMapper = new TimetableMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTimetableSample1();
        var actual = timetableMapper.toEntity(timetableMapper.toDto(expected));
        assertTimetableAllPropertiesEquals(expected, actual);
    }
}
