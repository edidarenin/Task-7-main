package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, UserService userService) {
        return args -> {
            if (roleRepository.count() == 0) {
                Role adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
                Role userRole  = roleRepository.save(new Role("ROLE_USER"));

                userService.save(new User("admin", "admin", Set.of(adminRole, userRole)));
                userService.save(new User("user",  "user",  Set.of(userRole)));

                System.out.println(">>> Users initialized: admin/admin, user/user");
            }
        };
    }
}