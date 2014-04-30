package com.excilys.computerdatabase.pages;

import java.util.List;

import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.validators.ErrorCodes;

public class ValidationErrorPage {
	ComputerDTO computerdto;
	Boolean modalShow;
	List<ErrorCodes> errors;
	Boolean hideErrors;
}
