package org.hachko.poc.service.impl;

import java.util.List;

import org.hachko.poc.dto.AppUserDto;
import org.hachko.poc.exception.user.AppUserDuplicateEmailException;
import org.hachko.poc.exception.user.AppUserDuplicateUserNameException;
import org.hachko.poc.exception.user.AppUserNotFoundException;
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
    public AppUserDto getUserById(Long id) throws AppUserNotFoundException {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(
            () -> new AppUserNotFoundException("Get : user with ID " + id + " not found."))
        );
    }

    @Override
    @Transactional
    public AppUserDto createUser(AppUserDto userDto) throws AppUserDuplicateUserNameException, AppUserDuplicateEmailException {
        if(userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new AppUserDuplicateUserNameException("Create : username '" + userDto.getUsername() + "' is already taken.");
        }
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new AppUserDuplicateEmailException("Create : email '" + userDto.getEmail() + "' is already taken.");
        }
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    @Override
    @Transactional
    public AppUserDto updateUser(AppUserDto userDtoBeforeUpdate, AppUserDto userDtoToUpdate) throws AppUserDuplicateUserNameException, AppUserDuplicateEmailException {        

        if(userRepository.findByUsername(userDtoToUpdate.getUsername()).isPresent() &&
        !userDtoToUpdate.getUsername().equals(userDtoBeforeUpdate.getUsername())) {
            throw new AppUserDuplicateUserNameException("Update : username '" + userDtoToUpdate.getUsername() + "' is already taken.");
        }
        if(userRepository.findByEmail(userDtoToUpdate.getEmail()).isPresent() &&
        !userDtoToUpdate.getEmail().equals(userDtoBeforeUpdate.getEmail())) {
            throw new AppUserDuplicateEmailException("Update : email '" + userDtoToUpdate.getEmail() + "' is already taken.");
        }
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDtoToUpdate)));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) throws AppUserNotFoundException {
        if(userRepository.findById(id).isEmpty()) {
            throw new AppUserNotFoundException("Delete : user with ID " + id + " not found.");
        }
        userRepository.deleteById(id);
    }
}
