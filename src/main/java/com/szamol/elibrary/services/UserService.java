package com.szamol.elibrary.services;

import com.szamol.elibrary.models.Role;
import com.szamol.elibrary.models.User;
import com.szamol.elibrary.repositories.RoleRepository;
import com.szamol.elibrary.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);

        Role role = roleRepository.findByRoleName("ROLE_USER");
        user.setRole(new HashSet<Role>(Arrays.asList(role)));

        userRepository.save(user);
    }

    public void changePassword(String newPassword, String email) {
        userRepository.changePassword(bCryptPasswordEncoder.encode(newPassword), email);
    }


}
