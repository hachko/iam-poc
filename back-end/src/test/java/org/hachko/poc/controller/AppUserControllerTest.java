package org.hachko.poc.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;
import java.util.Set;

import org.hachko.poc.dto.AppUserDto;
import org.hachko.poc.exception.user.AppUserConflictException;
import org.hachko.poc.exception.user.AppUserNotFoundException;
import org.hachko.poc.service.impl.AppUserManagement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(AppUserController.class)
public class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserManagement appUserManagement;

    @Test
    @WithMockUser(username = "JohnDoe", roles = {"ADMIN"})
    void shouldCreateUserSuccessfully() throws Exception {
        when(appUserManagement.createUser(any())).thenReturn(AppUserDto.builder().build());
        mockMvc.perform(
            post("/api/users/create")
            .with(csrf())        
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"testuser\", \"email\": \"testuser@example.com\", \"password\": \"password123\"}"))
            .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "JohnDoe", roles = {"ADMIN"})
    void shouldNotCreateUserWhenIdProvided() throws Exception {
        mockMvc.perform(
            post("/api/users/create")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": 1, \"username\": \"testuser\", \"email\": \"testuser@example.com\", \"password\": \"password123\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "JohnDoe", roles = {"ADMIN"})
    void shoulNotCreateUserWhenExceptionThrownFromService() throws Exception {
        when(appUserManagement.createUser(any())).thenThrow(new AppUserConflictException("Service error"));
        mockMvc.perform(
            post("/api/users/create")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"testuser\", \"email\": \"testuser@example.com\", \"password\": \"password123\"}"))
            .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "JohnDoe", roles = {"ADMIN"})
    void shouldGetUserByIdSuccessfully() throws Exception {
        when(appUserManagement.getUserById(anyLong())).thenReturn(
            AppUserDto.builder()
            .build()
        );
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "JohnDoe", roles = {"ADMIN"})
    void shouldGetErrorWhenUserNotFoundById() throws Exception {
        when(appUserManagement.getUserById(anyLong())).thenThrow(new AppUserNotFoundException("User not found"));
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "JohnDoe", roles = {"ADMIN"})
    void shouldUpdateUserSuccessfully() throws Exception {
        when(appUserManagement.updateUser(any(), any())).thenReturn(
            AppUserDto.builder()
            .build()
        );
        mockMvc.perform(
            put("/api/users/update/1")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": 1, \"username\": \"updateduser\", \"email\": \"test.user@example.com\"}"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "JohnDoe", roles = {"ADMIN"})
    void shouldGetErrorWhenUserToUpdateNotFound() throws Exception {
        when(appUserManagement.updateUser(any(), any())).thenThrow(new AppUserNotFoundException("User to update not found"));
        mockMvc.perform(
            put("/api/users/update/1")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": 1, \"username\": \"updateduser\", \"email\": \"test.user@example.com\"}"))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "JohnDoe", roles = {"ADMIN"})
    void shouldGetErrorWhenExceptionThrownFromService() throws Exception {
        when(appUserManagement.updateUser(any(), any())).thenThrow(new AppUserConflictException("Service error"));        
        mockMvc.perform(
            put("/api/users/update/1")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": 1, \"username\": \"updateduser\", \"email\": \"test.user@example.com\"}"))
            .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "JohnDoe", roles = {"ADMIN"})
    void shouldDeleteUserSuccessfully() throws Exception {
        mockMvc.perform(
            delete("/api/users/delete/1")
            .with(csrf()))            
            .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "JohnDoe", roles = {"ADMIN"})
    void shouldGetErrorWhenDeleteUserNotFound() throws Exception {
        doThrow(AppUserNotFoundException.class).when(appUserManagement).deleteUser(anyLong());
        mockMvc.perform(
            delete("/api/users/delete/1")
            .with(csrf()))            
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "JohnDoe", roles = {"ADMIN"})
    void shouldGetAllUsersSuccessfully() throws Exception {
        when(appUserManagement.getAllUsers()).thenReturn(List.of(
            new AppUserDto(1L, "JohnDoe", "John.Doe@example.com", "", Set.of())
        ));
        mockMvc.perform(get("/api/users/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].username").value("JohnDoe"))
            .andExpect(jsonPath("$[0].email").value("John.Doe@example.com"));
    }
}