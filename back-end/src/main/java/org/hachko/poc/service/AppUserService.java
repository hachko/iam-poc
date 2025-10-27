package org.hachko.poc.service;

import java.util.List;
import org.hachko.poc.dto.AppUserDto;

public interface AppUserService {
    List<AppUserDto> getAllUsers();
    AppUserDto getUserById(String id);
    AppUserDto createUser(AppUserDto userDto);
    AppUserDto updateUser(AppUserDto userDto);
    void deleteUser(String id);
}
