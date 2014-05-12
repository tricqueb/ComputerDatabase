package com.excilys.computerdatabase.pages;

import java.util.List;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Builder;

import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.validators.ErrorCodes;

@ToString
@Getter
@Builder
public class ValidationErrorPage {
	ComputerDTO computerDto;
	Boolean modalShow;
	List<ErrorCodes> errors;
	String hideErrors;
}
