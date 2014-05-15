package com.excilys.computerdatabase.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomDateValidator implements
		ConstraintValidator<CustomDateValid, String[]> {

	@Override
	public void initialize(CustomDateValid constraintAnnotation) {
	}

	@Override
	public boolean isValid(String[] dates, ConstraintValidatorContext context) {
		if (dates.length != 2) {
			throw new IllegalArgumentException("Illegal method signature");
		}

		if (dates[0] == null || dates[1] == null) {
			return true;
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		simpleDateFormat.setLenient(false);

		try {
			Date d1 = simpleDateFormat.parse(dates[0]);
			Date d2 = simpleDateFormat.parse(dates[1]);

			if (d1.before(d2))
				return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
}
