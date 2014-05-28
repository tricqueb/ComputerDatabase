package com.excilys.computerdatabase.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

@Slf4j
public class DateFormatValidator implements
		ConstraintValidator<DateFormat, String> {

	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	private String dateFormat;

	@Override
	public void initialize(DateFormat constraintAnnotation) {
		if (messageSource == null) {
			ResourceBundleMessageSource resBundle = new ResourceBundleMessageSource();
			resBundle.setBasename("messages");
			messageSource = resBundle;
			log.error("MESSAGE SOURCE NULL !!");
		}
		dateFormat = messageSource.getMessage("js.format.date",
				new Object[] {}, LocaleContextHolder.getLocale());
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		log.debug("Matching {} with regex {}", value, dateFormat);
		if (Pattern.matches(dateFormat, value) || value == "")
			return true;
		return false;
	}

}
