package org.hachko.poc.controller;

import java.util.List;

import org.hachko.poc.dto.AppUserDto;
import org.hachko.poc.service.AppUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/all")    
    public List<AppUserDto> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public AppUserDto getUserById(@PathVariable Long id) {
        return appUserService.getUserById(id);
    }

    @PostMapping("/create")
    public AppUserDto createUser(@RequestBody AppUserDto userDto) {
        return appUserService.createUser(userDto);
    }

    @PutMapping("/update")
    public AppUserDto updateUser(@RequestBody AppUserDto userDto) {
        return appUserService.updateUser(userDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        appUserService.deleteUser(id);
    }
}