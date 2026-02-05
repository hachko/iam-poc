package org.hachko.poc.service;

import org.hachko.poc.dto.AppUserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    AppUserDto authenticate(String username, String password, HttpServletRequest request);
    void logout(HttpServletRequest request, HttpServletResponse response);
}
