package br.com.vr.avaliacaointermediario.annotations.validators;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.vr.avaliacaointermediario.annotations.ApenasDuasCasasDecimais;

public class ApenasDuasCasasDecimaisValidator implements ConstraintValidator<ApenasDuasCasasDecimais, BigDecimal> {
    
    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }
        
        int decimals = Math.max(0, value.stripTrailingZeros().scale());

        if (decimals > 2) {
            return false;
        }

        return true;

    }

}
