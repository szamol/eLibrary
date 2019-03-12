package com.szamol.elibrary.controllers;

import com.szamol.elibrary.models.User;
import com.szamol.elibrary.services.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin/usersList")
    public String showUsersListPage(Model model) {
        model.addAttribute("users", getAllUsers());

        return "admin/usersList";
    }

    private List<User> getAllUsers() {
        List<User> usersList = adminService.findAllUsers();
        for(User user : usersList) {
            int roleNumber = user.getRole().iterator().next().getId();
            user.setRoleNumber(roleNumber);
        }
        return usersList;
    }
}
