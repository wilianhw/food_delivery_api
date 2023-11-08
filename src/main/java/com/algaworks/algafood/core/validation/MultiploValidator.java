package com.algaworks.algafood.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int numeroMultiplo;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean valido = true;

        if (value != null) {
            BigDecimal valorDecimal = BigDecimal.valueOf(value.doubleValue());
            BigDecimal multiploDecimal = BigDecimal.valueOf(numeroMultiplo);
            BigDecimal resto = valorDecimal.remainder(multiploDecimal);

            valido = BigDecimal.ZERO.compareTo(resto) == 0;
        }
        return valido;
    }
}
