package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/users")
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> managedRoles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roleRepository.findById(role.getId()).ifPresent(managedRoles::add);
        }
        user.setRoles(managedRoles);

        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userRepository.findById(id).orElseThrow();

        existingUser.setUsername(user.getUsername());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        Set<Role> managedRoles = new HashSet<>();
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                roleRepository.findById(role.getId()).ifPresent(managedRoles::add);
            }
        }
        existingUser.setRoles(managedRoles);

        return userRepository.save(existingUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}