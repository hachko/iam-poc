package org.hachko.poc.controller;

import java.util.List;

import org.hachko.poc.dto.AppUserDto;
import org.hachko.poc.service.AppUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    public List<AppUserDto> getAllUsers() {
        return appUserService.getAllUsers();
    }

    public AppUserDto getUserById(String id) {
        return appUserService.getUserById(id);
    }

    public AppUserDto createUser(AppUserDto userDto) {
        return appUserService.createUser(userDto);
    }

    public AppUserDto updateUser(AppUserDto userDto) {
        return appUserService.updateUser(userDto);
    }

    public void deleteUser(String id) {
        appUserService.deleteUser(id);
    }
}