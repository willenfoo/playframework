package org.apache.playframework.validator;

import java.lang.reflect.Method;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.playframework.annotations.ValidCodeText;

public class CodeTextValidator implements ConstraintValidator<ValidCodeText, String> {

    private Class<?> validatorClass;
  
    public void initialize(ValidCodeText constraintAnnotation) {
        this.validatorClass = constraintAnnotation.value();
    }

    public boolean isValid(String value, ConstraintValidatorContext constraintContext) {
    	if (value == null) {
			return true;
		}
		try {
			Method getInstance = validatorClass.getMethod("getInstance", null);
			Object obj = getInstance.invoke(validatorClass, null);
			Method isExist = obj.getClass().getMethod("textIsExist", String.class);
			return (Boolean)isExist.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
    }


}