package com.szamol.elibrary.controllers;

import com.szamol.elibrary.models.User;
import com.szamol.elibrary.services.UserService;
import com.szamol.elibrary.validators.RegisterValidator;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Locale;
import java.util.Map;

@Controller
public class RegisterController {

    private UserService userService;
    private MessageSource messageSource;

    public RegisterController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/register")
    public String showRegisterPage(Map<String, Object> model) {
        User user = new User();
        model.put("user", user);
        return "register";
    }

    @PostMapping("/addUser")
    public String registerAction(User user, BindingResult bindingResult, Model model, Locale locale) {
        String pageToReturn = null;

        User userExist = userService.findByEmail(user.getEmail());
        new RegisterValidator().validate(user, bindingResult);
        new RegisterValidator().validateEmailExists(userExist, bindingResult);

        if(bindingResult.hasErrors()) {
            pageToReturn = "register";
        } else {
            userService.saveUser(user);
            model.addAttribute("user", new User());
            model.addAttribute("success", messageSource.getMessage("success.register.message", null, locale));
            pageToReturn = "register";
        }

        return pageToReturn;

    }
}
