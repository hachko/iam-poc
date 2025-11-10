package org.hachko.poc.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import org.hachko.poc.dto.AppUserDto;
import org.hachko.poc.service.impl.AppUserManagement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AppUserManagement appUserManagement;

    void shouldCreateUserSuccessfully() {
        // Test implementation goes here
    }

    void shouldGetUserByIdSuccessfully() {
        // Test implementation goes here
    }

    void shouldUpdateUserSuccessfully() {
        // Test implementation goes here
    }

    void shouldDeleteUserSuccessfully() {
        // Test implementation goes here
    }

    @Test
    void shouldGetAllUsersSuccessfully() throws Exception {
        when(appUserManagement.getAllUsers()).thenReturn(List.of(
            new AppUserDto(1L, "JohnDoe", "John.Doe@example.com", "")
        ));
        mockMvc.perform(get("/api/users/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].username").value("JohnDoe"))
            .andExpect(jsonPath("$[0].email").value("John.Doe@example.com"));
    }
}