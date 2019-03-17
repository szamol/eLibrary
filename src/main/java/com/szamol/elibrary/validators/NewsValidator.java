package com.szamol.elibrary.validators;

import com.szamol.elibrary.models.News;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class NewsValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return  News.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "title", "error.news.title.empty");
        ValidationUtils.rejectIfEmpty(errors, "content", "error.news.content.empty");
    }
}
