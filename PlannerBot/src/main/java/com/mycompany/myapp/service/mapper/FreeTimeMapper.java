package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.FreeTime;
import com.mycompany.myapp.service.dto.FreeTimeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FreeTime} and its DTO {@link FreeTimeDTO}.
 */
@Mapper(componentModel = "spring")
public interface FreeTimeMapper extends EntityMapper<FreeTimeDTO, FreeTime> {}
