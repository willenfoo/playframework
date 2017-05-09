package org.apache.playframework.validator;

import java.lang.reflect.Method;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.playframework.annotations.ValidCode;

public class CodeValidator implements ConstraintValidator<ValidCode, String> {

    private Class<?> validatorClass;
  
    public void initialize(ValidCode constraintAnnotation) {
        this.validatorClass = constraintAnnotation.value();
    }

    public boolean isValid(String value, ConstraintValidatorContext constraintContext) {
    	if (value == null) {
			return true;
		}
		try {
			Method isExist = validatorClass.getMethod("isExist", String.class);
			return (Boolean)isExist.invoke(validatorClass, value);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
    }

}