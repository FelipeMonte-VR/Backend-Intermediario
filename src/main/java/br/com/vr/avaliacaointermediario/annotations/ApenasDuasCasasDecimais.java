package br.com.vr.avaliacaointermediario.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.vr.avaliacaointermediario.annotations.validators.ApenasDuasCasasDecimaisValidator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ApenasDuasCasasDecimaisValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface ApenasDuasCasasDecimais {
    String message() default "Only values with up to two decimal places are accepted.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
