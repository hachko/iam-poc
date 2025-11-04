package org.hachko.poc.service.impl;

import java.util.List;

import org.hachko.poc.dto.AppUserDto;
import org.hachko.poc.mapper.UserMapper;
import org.hachko.poc.repository.UserRepository;
import org.hachko.poc.service.AppUserService;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class AppUserManagement implements AppUserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public List<AppUserDto> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public AppUserDto getUserById(Long id) {
        return userMapper.toDto(userRepository.findById(Long.valueOf(id)).orElse(null));
    }

    @Override
    public AppUserDto createUser(AppUserDto userDto) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    @Override
    public AppUserDto updateUser(AppUserDto userDto) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
