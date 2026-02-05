package org.hachko.poc.service.impl;

import org.hachko.poc.dto.AppUserDto;
import org.hachko.poc.exception.user.AppUserNotFoundException;
import org.hachko.poc.mapper.UserMapper;
import org.hachko.poc.repository.UserRepository;
import org.hachko.poc.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationManagement implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authManager;
    private final SecurityContextLogoutHandler logoutHandler;
    
    @Override
    public AppUserDto authenticate(String username, String password, HttpServletRequest request) 
    throws BadCredentialsException, AppUserNotFoundException {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
        
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext
        );

        return this.getUserByUsername(username);
    }

    private AppUserDto getUserByUsername(String username) throws AppUserNotFoundException {
        // remove password after mapping for security reasons
        return userMapper.toDto(userRepository.findByUsername(username).orElseThrow(
            () -> new AppUserNotFoundException("user with username : " + username + " not found")
        )).toBuilder().password(null).build();
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    }

}
