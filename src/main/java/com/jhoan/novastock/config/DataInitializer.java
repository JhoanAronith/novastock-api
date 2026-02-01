package com.jhoan.novastock.config;

import com.jhoan.novastock.domain.entity.Role;
import com.jhoan.novastock.domain.entity.User;
import com.jhoan.novastock.repository.RoleRepository;
import com.jhoan.novastock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByName("ROLE_CLIENT").isEmpty()) {
            Role clientRole = new Role();
            clientRole.setName("ROLE_CLIENT");
            roleRepository.save(clientRole);
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@novastock.com");
            admin.setPassword(passwordEncoder.encode("admin123"));

            Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
            admin.setRoles(Set.of(adminRole));

            userRepository.save(admin);
            System.out.println("Usuario ADMIN creado por defecto (user: admin, pass: admin123)");
        }
    }
}
