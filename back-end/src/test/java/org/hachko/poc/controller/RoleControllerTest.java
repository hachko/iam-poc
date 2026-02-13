package org.hachko.poc.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hachko.poc.dto.RoleDto;
import org.hachko.poc.service.impl.RoleManagement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @MockBean
    private RoleManagement roleManagement;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void shouldGetAllRolesSuccessfully() throws Exception {
        when(roleManagement.getAllRoles()).thenReturn(List.of(
            RoleDto.builder().id(1L).name("USER").build(),
            RoleDto.builder().id(2L).name("ADMIN").build()
        ));
        mockMvc.perform(get("/api/roles/all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
    }

}
