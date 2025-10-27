package org.hachko.poc.mapper;

import java.util.List;
import org.hachko.poc.dto.AppUserDto;
import org.hachko.poc.dto.RoleDto;
import org.hachko.poc.model.AppUser;
import org.hachko.poc.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    AppUserDto toDto(AppUser user);
    AppUser toEntity(AppUserDto userDto);

    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto); 

    List<AppUserDto> toDtoList(List<AppUser> users);
    List<AppUser> toEntityList(List<AppUserDto> userDtos);
    List<RoleDto> toRoleDtoList(List<Role> roles);
    List<Role> toRoleEntityList(List<RoleDto> roleDtos);
}
