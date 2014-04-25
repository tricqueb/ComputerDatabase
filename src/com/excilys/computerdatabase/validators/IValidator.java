package com.excilys.computerdatabase.validators;

import java.util.List;

public interface IValidator<T> {
	/**
	 * Validate the given dto
	 * 
	 * @return Map with error code as key and string as error message
	 */
	public List<ErrorCodes> validate(T dto);

}
