package ru.lepescin.languages.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LanguageNameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LanguageNameConstraint {
    String message() default "Invalid language name!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
