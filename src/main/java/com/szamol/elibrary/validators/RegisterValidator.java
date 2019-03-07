package com.szamol.elibrary.validators;

import com.szamol.elibrary.models.User;
import com.szamol.elibrary.utils.ElibraryUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class RegisterValidator implements Validator {

    private final String emailPattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private final String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User u = (User) o;

        ValidationUtils.rejectIfEmpty(errors, "firstName", "error.register.firstName.empty");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "error.register.lastName.empty");
        ValidationUtils.rejectIfEmpty(errors, "email", "error.register.email.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "error.register.password.empty");

        if(!u.getEmail().equals("")) {
            boolean isMatch = ElibraryUtils.checkExpression(emailPattern, u.getEmail());
            if(!isMatch)
                errors.rejectValue("email", "error.register.invalidEmail");
        }

        if(!u.getPassword().equals("")) {
            boolean isMatch = ElibraryUtils.checkExpression(passwordPattern, u.getPassword());
            if(!isMatch)
                errors.rejectValue("password", "error.register.invalidPassword");
        }
    }

    public void validateEmailExists(User user, Errors errors) {
        if(user != null)
            errors.rejectValue("email", "error.register.emailExists");
    }
}
