package com.infinitiessoft.btrs.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

@Target({FIELD, METHOD})
@Retention(RUNTIME)
@Documented
@ValidatorClass(UniqueValidator.class)
public @interface Unique {
	String message() default "#{messages['validation.unique']}";

	String entityName();

	String fieldName();
	
	String idProvider();
}
