package com.excilys.computerdatabase.validator;

import java.util.List;

public interface Validator<T> {
	/**
	 * Validate the given dto
	 * 
	 * @return Map with error code as key and string as error message
	 */
	public List<ErrorCodes> validate(T dto);

}
