package org.hachko.poc.controller;

import org.hachko.poc.dto.AppUserDto;
import org.hachko.poc.dto.AuthLoginRequest;
import org.hachko.poc.exception.user.AppUserNotFoundException;
import org.hachko.poc.service.AppUserService;
import org.hachko.poc.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class AuthController {

    private final AuthService authenticationService;    
    
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AppUserDto login(@RequestBody AuthLoginRequest request, HttpServletRequest servletRequest) {        
        try {            
            return authenticationService.authenticate(
                request.getUsername(), request.getPassword(), servletRequest
            );
        } catch (BadCredentialsException | AppUserNotFoundException authEx) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        }
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authenticationService.logout(request, response);
    }

    
}
