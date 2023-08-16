package com.example.model.veri;

import com.example.model.repository.RoleRepository;
import com.example.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository,UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository=userRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);

        User adminUser = new User("admin", "adminPassword", new Identity());
        adminUser.setRoles(roles);
        userRepository.save(adminUser);

    }
}
