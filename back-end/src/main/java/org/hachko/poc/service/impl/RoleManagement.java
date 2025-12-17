package org.hachko.poc.service.impl;

import java.util.List;
import org.hachko.poc.dto.RoleDto;
import org.hachko.poc.mapper.UserMapper;
import org.hachko.poc.repository.RoleRepository;
import org.hachko.poc.service.RoleService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleManagement implements RoleService {

    private final RoleRepository roleRepository;

    // TODO regularly check if not better to have Role own mapper
    private final UserMapper userMapper;

    @Override
    public List<RoleDto> getAllRoles() {
        return userMapper.toRoleDtoList(roleRepository.findAll());
    }

}
