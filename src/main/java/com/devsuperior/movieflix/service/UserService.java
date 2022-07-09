package com.devsuperior.movieflix.service;

import com.devsuperior.movieflix.dto.UserDto;
import com.devsuperior.movieflix.entities.Role;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.exceptions.UnauthorizedException;
import com.devsuperior.movieflix.repositories.RoleRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AuthService authService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authService = authService;
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {

        validateSelfOrAdmin(id);

        return userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(() -> new ResourceNotFoundException("Resource id: %d not found".formatted(id)));
    }

    private void validateSelfOrAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource id: %d not found".formatted(userId)));

        User authenticated = authService.authenticated();
//&& !authenticated.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")
        if (!user.getId().equals(authenticated.getId() )) {
            throw new UnauthorizedException("Usuário não autorizado");
        }
    }
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)  {
        User user =  userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        initRoles(user);

        return user;
    }

    private void initRoles(User user) {
        List<Role> roles = user.getRoles().stream()
                .map(role -> roleRepository.findById(role.getId())
                        .orElseThrow(() -> new RuntimeException("Role not found")))
                .toList();
        user.getRoles().clear();
        user.getRoles().addAll(roles);
    }

    public UserDto profile() {
        User authenticated = authService.authenticated();

        return userRepository.findById(authenticated.getId())
                .map(UserDto::new)
                .orElseThrow(() -> new ResourceNotFoundException("Resource id: %d not found".formatted(authenticated.getId())));
    }
}
