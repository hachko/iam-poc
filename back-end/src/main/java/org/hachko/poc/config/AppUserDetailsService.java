package org.hachko.poc.config;

import java.util.List;
import java.util.stream.Collectors;

import org.hachko.poc.model.AppUser;
import org.hachko.poc.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser baseUser = userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("user not found : " + username)
        );
        List<GrantedAuthority> authorities = baseUser.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
        .collect(Collectors.toList());

        return User.builder()
        .username(baseUser.getUsername())
        .password(baseUser.getPassword())
        .authorities(authorities)
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
    }

}
