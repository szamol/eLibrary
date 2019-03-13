package com.szamol.elibrary.controllers;

import com.szamol.elibrary.models.User;
import com.szamol.elibrary.services.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@Secured("ROLE_ADMIN")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin/usersList/{pageNumber}")
    public String showUsersListPage(@PathVariable("pageNumber") int pageNumber, Model model) {
        Page<User> pages = getAllUsersPageable(pageNumber - 1);
        List<User> userList = pages.getContent();

        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("currentPage", pages.getNumber() + 1);
        model.addAttribute("users", userList);

        return "admin/usersList";
    }

    @GetMapping("/admin/usersList/search/{expression}")
    public String showSearchUserPage(@PathVariable("expression") String expression, Model model) {
        List<User> resultList = adminService.searchUser(expression);

        for (User user : resultList) {
            int roleNumber = user.getRole().iterator().next().getId();
            user.setRoleNumber(roleNumber);
        }

        model.addAttribute("totalPages", 1);
        model.addAttribute("currentPage", 1);

        model.addAttribute("users", resultList);

        return "admin/usersList";
    }

    private Page<User> getAllUsersPageable(int pageNumber) {
        int rowsInPage = 5;

        Page<User> page = adminService.findAllUsers(PageRequest.of(pageNumber, rowsInPage));

        for (User user : page) {
            int roleNumber = user.getRole().iterator().next().getId();
            user.setRoleNumber(roleNumber);
        }

        return page;
    }
}
