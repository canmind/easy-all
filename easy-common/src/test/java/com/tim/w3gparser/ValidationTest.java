package com.tim.w3gparser;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.bval.jsr.ApacheValidationProvider;

public class ValidationTest {
	public static void main(String[] args) {
		User user = new User();
		ValidatorFactory avf = Validation
				.byProvider(ApacheValidationProvider.class).configure()
				.buildValidatorFactory();
		Validator validator = avf.getValidator();
		// news.setName("dsdsa");
		Set<ConstraintViolation<User>> constraintViolations = validator
				.validate(user);

		for (ConstraintViolation<User> constraintViolation : constraintViolations) {
			System.out.println(constraintViolation.getMessage());
		}
		System.out.println(constraintViolations);
	}
}
