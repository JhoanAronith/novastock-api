package com.jhoan.novastock;

import com.jhoan.novastock.config.security.AuthService;
import com.jhoan.novastock.config.security.JwtService;
import com.jhoan.novastock.domain.entity.*;
import com.jhoan.novastock.dto.request.UserLoginRequestDTO;
import com.jhoan.novastock.dto.request.UserRegisterRequestDTO;
import com.jhoan.novastock.dto.response.UserAuthResponseDTO;
import com.jhoan.novastock.repository.RoleRepository;
import com.jhoan.novastock.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_ShouldCreateUserAndReturnToken() {

        UserRegisterRequestDTO request = new UserRegisterRequestDTO("jhoan", "jhoan@test.com", "123456");

        Role role = new Role();
        role.setName("ROLE_CLIENT");

        when(passwordEncoder.encode("123456")).thenReturn("encoded_password");
        when(roleRepository.findByName("ROLE_CLIENT")).thenReturn(Optional.of(role));
        when(jwtService.generatedToken(any(User.class))).thenReturn("fake-jwt-token");

        UserAuthResponseDTO response = authService.register(request);

        assertThat(response.token()).isEqualTo("fake-jwt-token");
        assertThat(response.username()).isEqualTo("jhoan");

        verify(userRepository).save(argThat(user ->
                user.getPassword().equals("encoded_password") &&
                        user.getRoles().contains(role)
        ));
    }

    @Test
    void login_WhenCredentialsAreValid_ShouldReturnToken() {

        UserLoginRequestDTO request = new UserLoginRequestDTO("jhoan", "123456");
        User user = new User();
        user.setUsername("jhoan");

        when(userRepository.findByUsername("jhoan")).thenReturn(Optional.of(user));
        when(jwtService.generatedToken(user)).thenReturn("login-token");

        UserAuthResponseDTO response = authService.login(request);

        assertThat(response.token()).isEqualTo("login-token");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}