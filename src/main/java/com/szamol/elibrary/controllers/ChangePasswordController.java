package com.szamol.elibrary.controllers;

import com.szamol.elibrary.models.User;
import com.szamol.elibrary.services.UserService;
import com.szamol.elibrary.utils.UserUtils;
import com.szamol.elibrary.validators.ChangePasswordValidator;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Locale;

@Controller
public class ChangePasswordController {

    private UserService userService;
    private MessageSource messageSource;

    public ChangePasswordController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/changePassword")
    public String showChangePasswordPage(Model model) {
        String userEmail = UserUtils.getLoggedUser();

        User user = userService.findByEmail(userEmail);

        model.addAttribute("user", user);

        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(User user, BindingResult bindingResult, Model model, Locale locale) {
        String pageToReturn = null;

        new ChangePasswordValidator().validate(user, bindingResult);
        new ChangePasswordValidator().validatePassword(user.getNewPassword(), bindingResult);
        new ChangePasswordValidator().validateConfirmPassword(user.getNewPassword(), user.getNewPasswordConfirm(), bindingResult);

        if(bindingResult.hasErrors()) {
            pageToReturn = "changePassword";
        } else {
            userService.changePassword(user.getNewPassword(), user.getEmail());
            model.addAttribute("success", messageSource.getMessage("passwordChange.success", null, locale));
            pageToReturn = "changePassword";
        }
        return pageToReturn;
    }
}
