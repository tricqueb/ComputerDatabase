package com.excilys.computerdatabase.page;

import java.util.Set;

import javax.validation.ConstraintViolation;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Builder;

@ToString
@Getter
@Builder
public class ValidationErrorPage<T> {
	T dto;
	Boolean modalShow;
	Set<ConstraintViolation<T>> constraintViolation;
	String hideErrors;
}
