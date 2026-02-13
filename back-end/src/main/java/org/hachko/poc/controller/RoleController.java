package org.hachko.poc.controller;

import java.util.List;

import org.hachko.poc.dto.RoleDto;
import org.hachko.poc.service.RoleService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/roles/")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class RoleController {
    
    private final RoleService roleService;

    @GetMapping("all")
    public List<RoleDto> getAllRoles() {
        return roleService.getAllRoles();
    }
}
