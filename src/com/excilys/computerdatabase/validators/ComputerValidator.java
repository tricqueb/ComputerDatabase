package com.excilys.computerdatabase.validators;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dto.ComputerDTO;

//TODO add name length verification
public class ComputerValidator implements IValidator<ComputerDTO> {
	private static final Logger logger = LoggerFactory.getLogger(ComputerValidator.class);

	// id can't start with 0, then any number
	private String idPattern = "^[1-9]\\d{0,17}$";
	private String datePattern = "(\\d{4})-(([0][0-9])|([1][0-2]))-(([0-2][0-9])|(3[0-1]))";

	@Override
	public List<ErrorCodes> validate(ComputerDTO dto) {
		logger.debug("Id pattern : {}", idPattern);
		logger.debug("Date pattern : {}", datePattern);
		logger.debug("Dto to validate: {}", dto);
		List<ErrorCodes> errorList = new ArrayList<>();

		if (dto.getId() != null) {
			if (!dto.getId().matches(idPattern))
				errorList.add(ErrorCodes.ID_ERROR);
		} else
			errorList.add(ErrorCodes.ID_NULL);

		if (dto.getName() != null) {
			if (dto.getName().isEmpty())
				errorList.add(ErrorCodes.NAME_EMPTY);
			if (dto.getName().length() > 255)
				errorList.add(ErrorCodes.NAME_LENGTH);
		} else
			errorList.add(ErrorCodes.NAME_NULL);

		if (dto.getIntroduced() != null) {
			if (!dto.getIntroduced().isEmpty())
				if (!dto.getIntroduced().matches(datePattern))
					errorList.add(ErrorCodes.INTRODUCED_FORMAT);
		}

		if (dto.getDiscontinued() != null) {
			if (!dto.getDiscontinued().isEmpty())
				if (!dto.getDiscontinued().matches(datePattern))
					errorList.add(ErrorCodes.DISCONTINUED_FORMAT);
			if (dto.getIntroduced() == null || (dto.getIntroduced().isEmpty()))
				errorList.add(ErrorCodes.INTRODUCED_MISSING);
		}

		if (dto.getCompany() != null) {
			CompanyValidator companyV = new CompanyValidator();
			errorList.addAll(companyV.validate(dto.getCompany()));
		}

		return errorList;
	}
}
