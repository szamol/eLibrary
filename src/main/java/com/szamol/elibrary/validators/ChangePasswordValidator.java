package com.szamol.elibrary.validators;

import com.szamol.elibrary.models.User;
import com.szamol.elibrary.utils.ElibraryUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ChangePasswordValidator implements Validator {
    private final String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        @SuppressWarnings("unused")
        User u = (User) o;

        ValidationUtils.rejectIfEmpty(errors,"newPassword", "error.passwordChange.empty");
    }

    public void validatePassword(String newPassword, Errors errors) {
        if(!newPassword.equals("")) {
            boolean isMatch = ElibraryUtils.checkExpression(passwordPattern, newPassword);
            if(!isMatch)
                errors.rejectValue("newPassword", "error.passwordChange.invalidPassword");
        }
    }

    public void validateConfirmPassword(String newPassword, String newPasswordConfirm, Errors errors) {
        if(!newPassword.equals(newPasswordConfirm))
            errors.rejectValue("newPasswordConfirm", "error.passwordChange.notMatch");
    }
}
