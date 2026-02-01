package com.jhoan.novastock.config.security;

import com.jhoan.novastock.domain.entity.Role;
import com.jhoan.novastock.domain.entity.User;
import com.jhoan.novastock.dto.request.UserLoginRequestDTO;
import com.jhoan.novastock.dto.request.UserRegisterRequestDTO;
import com.jhoan.novastock.dto.response.UserAuthResponseDTO;
import com.jhoan.novastock.repository.RoleRepository;
import com.jhoan.novastock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserAuthResponseDTO register(UserRegisterRequestDTO request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        Role clientRole = roleRepository.findByName("ROLE_CLIENT")
                .orElseThrow(() -> new RuntimeException("Error: El rol ROLE_CLIENT no existe en la base de datos."));
        user.setRoles(Set.of(clientRole));

        userRepository.save(user);
        String token = jwtService.generatedToken(user);

        return new UserAuthResponseDTO(token, user.getUsername(), "Usuario registrado exitosamente");
    }

    public UserAuthResponseDTO login(UserLoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado tras autenticación."));

        String token = jwtService.generatedToken(user);

        return new UserAuthResponseDTO(token, user.getUsername(), "Sesión iniciada correctamente");
    }

}

