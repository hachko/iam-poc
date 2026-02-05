package org.hachko.poc.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Set<RoleDto> roles;
}
