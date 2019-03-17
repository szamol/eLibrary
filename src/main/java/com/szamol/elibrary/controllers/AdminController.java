package com.szamol.elibrary.controllers;

import com.szamol.elibrary.models.User;
import com.szamol.elibrary.services.AdminService;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@Secured("ROLE_ADMIN")
public class AdminController {

    private AdminService adminService;
    private MessageSource messageSource;

    public AdminController(AdminService adminService, MessageSource messageSource) {
        this.adminService = adminService;
        this.messageSource = messageSource;
    }

    @GetMapping("/admin/usersList/{pageNumber}")
    public String showUsersListPage(@PathVariable("pageNumber") int pageNumber, Model model) {
        Page<User> pages = getAllUsersPageable(pageNumber - 1);
        List<User> userList = pages.getContent();
        Map<Integer, String> roleMap = prepareRoleMap();


        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("currentPage", pages.getNumber() + 1);
        model.addAttribute("users", userList);
        model.addAttribute("roles", roleMap);
        return "admin/usersList";
    }

    @GetMapping("/admin/usersList/search/{expression}")
    public String showSearchUserPage(@PathVariable("expression") String expression, Model model) {
        List<User> resultList = adminService.searchUser(expression);
        Map<Integer, String> roleMap = prepareRoleMap();

        for (User user : resultList) {
            int roleNumber = user.getRole().iterator().next().getId();
            user.setRoleNumber(roleNumber);
        }

        model.addAttribute("totalPages", 1);
        model.addAttribute("currentPage", 1);
        model.addAttribute("users", resultList);
        model.addAttribute("roles", roleMap);

        return "admin/usersList";
    }

    @PostMapping("/admin/usersList/changeRole")
    public String changeRoleAction(@RequestParam("userId") int userId, @RequestParam("roleId") int roleId) {
        adminService.changeRole(userId, roleId);
        return "redirect:/admin/usersList/1";
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

    private Map<Integer, String> prepareRoleMap() {
        Locale locale = Locale.getDefault();
        Map<Integer, String> roleMap = new HashMap<>();
        roleMap.put(1, messageSource.getMessage("roles.admin", null, locale));
        roleMap.put(2, messageSource.getMessage("roles.regular", null, locale));
        roleMap.put(3, messageSource.getMessage("roles.worker", null, locale));
        return roleMap;
    }
}
