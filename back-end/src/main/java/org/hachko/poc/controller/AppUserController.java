package org.hachko.poc.controller;

import java.util.List;

import org.hachko.poc.dto.AppUserDto;
import org.hachko.poc.exception.user.AppUserConflictException;
import org.hachko.poc.exception.user.AppUserNotFoundException;
import org.hachko.poc.service.AppUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/all")    
    public List<AppUserDto> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public AppUserDto getUserById(@PathVariable Long id) {
        try{ 
            return appUserService.getUserById(id);
        } catch (AppUserNotFoundException apusex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, apusex.getMessage());
        }
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)    
    public AppUserDto createUser(@RequestBody AppUserDto userDto) {
        if(userDto.getId() != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "New user cannot already have an ID."
            );
        }
        try {
            return appUserService.createUser(userDto);
        } catch (AppUserConflictException apusex) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, apusex.getMessage()
            );
        }        
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public AppUserDto updateUser(@RequestBody AppUserDto userDto) {
        if(userDto.getId() == null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Updated user must have an ID."
            );
        }
        try {
            return appUserService.updateUser(userDto);
        } catch (AppUserConflictException apusex) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, apusex.getMessage()
            );
        } catch (AppUserNotFoundException apusex) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, apusex.getMessage()
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        try {
            appUserService.deleteUser(id);
        } catch (AppUserNotFoundException apusex) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, apusex.getMessage()
            );
        }
    }
}