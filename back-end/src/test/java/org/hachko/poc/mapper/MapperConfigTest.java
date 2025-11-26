package org.hachko.poc.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.hachko.poc.dto.AppUserDto;
import org.hachko.poc.dto.RoleDto;
import org.hachko.poc.model.AppUser;
import org.hachko.poc.model.Role;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class MapperConfigTest {
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void rolesAreMappedWithinAppUserWhenEntityToDto() {
        AppUser user = AppUser.builder()
        .id(1L)
        .username("joeSmith")
        .email("joeSmith@example.com")
        .password("somePassword")
        .roles(Set.of(
            Role.builder()
            .id(1L)
            .name("user")
            .build(),
            Role.builder()
            .id(2L)
            .name("admin")
            .build())            
        )
        .build();
        AppUserDto userDto = userMapper.toDto(user);
        assertEquals(2, userDto.getRoles().size());
        assertTrue(
            userDto.getRoles().contains(RoleDto.builder().id(1L).name("user").build())
        );
        assertTrue(
            userDto.getRoles().contains(RoleDto.builder().id(2L).name("admin").build())
        );
    }
}
