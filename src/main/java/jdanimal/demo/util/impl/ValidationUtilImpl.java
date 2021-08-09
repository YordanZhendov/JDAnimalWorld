package jdanimal.demo.util.impl;

import jdanimal.demo.util.ValidationUtil;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;

@Service
public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator;

    public ValidationUtilImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).isEmpty();
    }
}
