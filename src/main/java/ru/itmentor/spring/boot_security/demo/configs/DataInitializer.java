package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {

            if (roleRepository.count() == 0) {
                Role adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
                Role userRole = roleRepository.save(new Role("ROLE_USER"));

                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin")); // Пароль: admin
                admin.setRoles(Set.of(adminRole, userRole));
                userRepository.save(admin);

                User user = new User();
                user.setUsername("user");
                user.setPassword(encoder.encode("user")); // Пароль: user
                user.setRoles(Set.of(userRole));
                userRepository.save(user);
            }
        };
    }
}