package com.algaworks.algafood.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Objects;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        valorField = constraintAnnotation.valorField();
        descricaoField = constraintAnnotation.descricaoField();
        descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            BigDecimal valor = (BigDecimal) Objects.requireNonNull(BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), valorField))
                    .getReadMethod().invoke(objetoValidacao);

            String descricao = (String) Objects.requireNonNull(BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), descricaoField))
                    .getReadMethod().invoke(objetoValidacao);

            if (BigDecimal.ZERO.compareTo(valor) == 0) {
                valid = descricao.toLowerCase().contains(descricaoObrigatoria.toLowerCase());
            }
            return valid;
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}
