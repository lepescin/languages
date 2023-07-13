package ru.lepescin.languages.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class LanguageNameValidator implements ConstraintValidator<LanguageNameConstraint, String> {

    private static final List<String> ALLOWED_LANGUAGES = List.of("Java", "JavaScript", "C#", "C++", "Python");

    @Override
    public void initialize(LanguageNameConstraint name) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext cxt) {
        return ALLOWED_LANGUAGES.contains(name);
    }

}
