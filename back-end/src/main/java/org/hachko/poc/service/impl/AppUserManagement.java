package org.hachko.poc.service.impl;

import java.util.List;

import org.hachko.poc.dto.AppUserDto;
import org.hachko.poc.service.AppUserService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserManagement implements AppUserService {
    @Override
    public List<AppUserDto> getAllUsers() {
        return null;
    }

    @Override
    public AppUserDto getUserById(String id) {
        return null;
    }

    @Override
    public AppUserDto createUser(AppUserDto userDto) {
        return null;
    }

    @Override
    public AppUserDto updateUser(AppUserDto userDto) {
        return null;
    }

    @Override
    public void deleteUser(String id) {

    }
}
