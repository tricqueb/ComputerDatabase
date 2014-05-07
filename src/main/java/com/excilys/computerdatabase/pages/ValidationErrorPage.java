package com.excilys.computerdatabase.pages;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Builder;

import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.validators.ErrorCodes;

@Getter
@Builder
public class ValidationErrorPage {
	ComputerDTO computerdto;
	Boolean modalShow;
	List<ErrorCodes> errors;
	String hideErrors;
}
