package com.szamol.elibrary.controllers;

import com.szamol.elibrary.models.User;
import com.szamol.elibrary.services.UserService;
import com.szamol.elibrary.utils.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserProfileController {

    private UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        String userEmail = UserUtils.getLoggedUser();

        User user = userService.findByEmail(userEmail);
        int roleNumber = user.getRole().iterator().next().getId();
        user.setRoleNumber(roleNumber);

        model.addAttribute("user", user);

        return "profile";
    }
}
