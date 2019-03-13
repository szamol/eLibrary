package com.szamol.elibrary.services;

import com.szamol.elibrary.models.User;
import com.szamol.elibrary.repositories.AdminRepository;
import com.szamol.elibrary.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminService {

    private UserRepository userRepository;

    private AdminRepository adminRepository;

    public AdminService(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public List<User> searchUser(String expression) {
        return adminRepository.searchUser(expression);
    }
}
