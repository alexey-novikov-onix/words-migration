package com.onix.wordsmigration;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidationHelper {

    public final static Validator validator = Validation.buildDefaultValidatorFactory().usingContext().getValidator();

}
