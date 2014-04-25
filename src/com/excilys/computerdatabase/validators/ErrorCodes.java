package com.excilys.computerdatabase.validators;

public enum ErrorCodes {
	ID_ERROR(1, "Error on id"), ID_NULL(1, "id is missing"), NAME_NULL(2,
			"Name is missing"), NAME_EMPTY(2, "Name can't be empty"), NAME_LENGTH(
			2, "Name length too long"), INTRODUCED_FORMAT(3,
			"Date format must be: YYYY-MM-DD"), DISCONTINUED_FORMAT(4,
			"Date format must be: YYYY-MM-DD"), INTRODUCED_MISSING(5,
			"Introduced date is needed"), COMPANY_ERROR(6, "Error on id");

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	private final Integer code;
	private final String message;

	ErrorCodes(Integer code, String error) {
		this.code = code;
		this.message = error;
	}

}
