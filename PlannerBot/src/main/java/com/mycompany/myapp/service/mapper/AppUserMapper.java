package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.AppUser;
import com.mycompany.myapp.service.dto.AppUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {}
