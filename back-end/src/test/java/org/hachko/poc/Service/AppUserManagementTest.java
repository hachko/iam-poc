package org.hachko.poc.Service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.hachko.poc.dto.AppUserDto;
import org.hachko.poc.exception.user.AppUserConflictException;
import org.hachko.poc.exception.user.AppUserDuplicateEmailException;
import org.hachko.poc.exception.user.AppUserDuplicateUserNameException;
import org.hachko.poc.exception.user.AppUserNotFoundException;
import org.hachko.poc.model.AppUser;
import org.hachko.poc.repository.UserRepository;
import org.hachko.poc.service.impl.AppUserManagement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppUserManagementTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private org.hachko.poc.mapper.UserMapper userMapper;

    @InjectMocks
    private AppUserManagement appUserManagement;

    private List<AppUser> testData = List.of(
        AppUser.builder()
            .id(1L)
            .username("JohnDoe")
            .email("john.doe@example.com")
            .build(),
        AppUser.builder()
            .id(2L)
            .username("JaneDoe")
            .email("jane.doe@example.com")
            .build()
    );

    private Optional<AppUser> getUserById(Long id) {
        return testData.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    private Optional<AppUser> getUserByUsername(String username) {
        return testData.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    private Optional<AppUser> getUserByEmail(String email) {
        return testData.stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }    
    
    private void setAllMockBehaviors() {

        when(userRepository.findById(anyLong()))
            .thenAnswer(invocation -> {
                Long id = invocation.getArgument(0);
                return getUserById(id);
            });

        when(userRepository.findByUsername(anyString()))
            .thenAnswer(invocation -> {
                String username = invocation.getArgument(0);
                return getUserByUsername(username);
            });

        when(userRepository.findByEmail(anyString()))
            .thenAnswer(invocation -> {
                String email = invocation.getArgument(0);
                return getUserByEmail(email);
            });
    }

    @Test
    void exceptionThrownWhenUserNotFound() {
        when(userRepository.findById(anyLong()))
            .thenAnswer(invocation -> {
                Long id = invocation.getArgument(0);
                return getUserById(id);
            });
        AppUserNotFoundException apusex = Assertions.assertThrows(AppUserNotFoundException.class, () -> {
            appUserManagement.getUserById(3L);
        });
        Assertions.assertDoesNotThrow(() -> {
            appUserManagement.getUserById(2L);
        });
        Assertions.assertEquals("Get : user with ID 3 not found.", apusex.getMessage());
    }    

    @Test
    void exceptionThrownWhenUserToUpdateNotFound() {
        when(userRepository.findById(anyLong()))
            .thenAnswer(invocation -> {
                Long id = invocation.getArgument(0);
                return getUserById(id);
            });
        AppUserNotFoundException apusex = Assertions.assertThrows(AppUserNotFoundException.class, () -> {
            appUserManagement.updateUser(AppUserDto.builder()
                .id(3L)
                .username("JaneDoe")
                .email("jane.doe@example.com")
                .build());
        });
        Assertions.assertEquals("Update : user with ID 3 not found.", apusex.getMessage());
    }    

    @Test
    void exceptionThrownWhenUpdateUserWithDuplicateUsername() {
        when(userRepository.findById(anyLong()))
            .thenAnswer(invocation -> {
                Long id = invocation.getArgument(0);
                return getUserById(id);
            });
        when(userRepository.findByUsername(anyString()))
            .thenAnswer(invocation -> {
                String username = invocation.getArgument(0);
                return getUserByUsername(username);
            });
        AppUserDuplicateUserNameException apusex = 
        Assertions.assertThrows(AppUserDuplicateUserNameException.class, () -> {
            appUserManagement.updateUser(AppUserDto.builder()
                .id(1L)
                .username("JaneDoe")
                .build());
        });
        Assertions.assertEquals("Update : username 'JaneDoe' is already taken.", apusex.getMessage());
    }
  
    @Test
    void exceptionThrownWhenUpdateUserWithDuplicateEmail() {
        when(userRepository.findById(anyLong()))
            .thenAnswer(invocation -> {
                Long id = invocation.getArgument(0);
                return getUserById(id);
            });
        when(userRepository.findByEmail(anyString()))
            .thenAnswer(invocation -> {
                String email = invocation.getArgument(0);
                return getUserByEmail(email);
            });
        AppUserDuplicateEmailException apusex = 
        Assertions.assertThrows(AppUserDuplicateEmailException.class, () -> {
            appUserManagement.updateUser(AppUserDto.builder()
                .id(1L)
                .email("jane.doe@example.com")
                .build());
        });
        Assertions.assertEquals("Update : email 'jane.doe@example.com' is already taken.", apusex.getMessage());
    }

    @Test
    void exceptionThrownWhenCreateUserWithDuplicateUsername() {
        when(userRepository.findByUsername(anyString()))
            .thenAnswer(invocation -> {
                String username = invocation.getArgument(0);
                return getUserByUsername(username);
            });
        AppUserDuplicateUserNameException apusex = 
        Assertions.assertThrows(AppUserDuplicateUserNameException.class, () -> {
            appUserManagement.createUser(AppUserDto.builder()
                .username("JaneDoe")
                .email("sam.smith@example.com")
                .build());
        });
        Assertions.assertEquals("Create : username 'JaneDoe' is already taken.", apusex.getMessage());
    }

    @Test
    void exceptionThrownWhenCreateUserWithDuplicateEmail() {
        when(userRepository.findByUsername(anyString()))
            .thenAnswer(invocation -> {
                String username = invocation.getArgument(0);
                return getUserByUsername(username);
            });

        when(userRepository.findByEmail(anyString()))
            .thenAnswer(invocation -> {
                String email = invocation.getArgument(0);
                return getUserByEmail(email);
            });
        AppUserDuplicateEmailException apusex = 
        Assertions.assertThrows(AppUserDuplicateEmailException.class, () -> {
            appUserManagement.createUser(AppUserDto.builder()
                .username("SamSmith")
                .email("jane.doe@example.com")
                .build());
        });
        Assertions.assertEquals("Create : email 'jane.doe@example.com' is already taken.", apusex.getMessage());
    }

    @Test
    void exceptionThrownWhenDeleteUserNotFound() {
        when(userRepository.findById(anyLong()))
            .thenAnswer(invocation -> {
                Long id = invocation.getArgument(0);
                return getUserById(id);
            });
        AppUserNotFoundException apusex = Assertions.assertThrows(AppUserNotFoundException.class, () -> {
            appUserManagement.deleteUser(3L);
        });
        Assertions.assertEquals("Delete : user with ID 3 not found.", apusex.getMessage());
    }

}
