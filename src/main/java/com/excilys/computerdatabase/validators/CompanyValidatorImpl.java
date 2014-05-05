package com.excilys.computerdatabase.validators;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dto.impl.CompanyDTOImpl;

public class CompanyValidatorImpl implements Validator<CompanyDTOImpl> {

	// id can't start with 0, then any number
	private String idPattern = "^([1-9]\\d{0,17})$";

	@Override
	public List<ErrorCodes> validate(CompanyDTOImpl dto) {
		List<ErrorCodes> errorList = new ArrayList<>();

		if (dto.getId() != null) {
			if (!dto.getId().matches(idPattern)
					&& !dto.getId().contentEquals("null"))
				errorList.add(ErrorCodes.ID_ERROR);
		} else
			errorList.add(ErrorCodes.ID_NULL);

		return errorList;
	}
}
