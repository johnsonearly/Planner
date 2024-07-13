package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Timetable;
import com.mycompany.myapp.service.dto.TimetableDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Timetable} and its DTO {@link TimetableDTO}.
 */
@Mapper(componentModel = "spring")
public interface TimetableMapper extends EntityMapper<TimetableDTO, Timetable> {}
