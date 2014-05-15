package com.excilys.computerdatabase.controller;

import java.util.Set;

import javax.sql.DataSource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.dto.impl.CompanyDTOImpl;
import com.excilys.computerdatabase.dto.impl.ComputerDTOImpl;
import com.excilys.computerdatabase.mapper.impl.ComputerMapperImpl;
import com.excilys.computerdatabase.page.ValidationErrorPage;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

@Controller
@RequestMapping("/Computer")
public class ComputerCRUDController {
	private static final Logger logger = LoggerFactory.getLogger(ComputerCRUDController.class);

	@Autowired
	@Qualifier("DataSource")
	private DataSource datasource;
	@Autowired
	private CompanyService companyService;
	@Autowired
	ComputerService computerService;

	@ModelAttribute
	private ComputerDTO computerDto() {
		return new ComputerDTOImpl();
	}

	@RequestMapping(value = "/Create", method = RequestMethod.POST)
	public String create(
			@ModelAttribute ComputerDTO computerDto,
			@RequestParam("companyId") String companyId,
			RedirectAttributes redirectAttribute) {

		computerDto.setCompany(CompanyDTOImpl.Builder().id(companyId).build());
		logger.debug("Validation of {}", computerDto);

		// Validation
		Validator validator = validator();

		// TODO No company validation done
		Set<ConstraintViolation<ComputerDTO>> constraintViolations = validator.validateProperty(
				computerDto, "name");

		constraintViolations.addAll(validator.validateProperty(computerDto,
				"introduced"));

		constraintViolations.addAll(validator.validateProperty(computerDto,
				"discontinued"));

		if (constraintViolations.isEmpty()) {
			ComputerMapperImpl computerMapper = new ComputerMapperImpl();
			computerService.create(computerMapper.map(computerDto));
		} else { // Error handling
			logger.warn("Error happened on create");
			// TODO Change modalShow=null to false && hiderErrors=Block
			ValidationErrorPage<ComputerDTO> validationErrorPage = validationErrorPage(
					computerDto, null, constraintViolations, "block");
			redirectAttribute.addFlashAttribute(validationErrorPage);
			return "redirect:/Dashboard/computer/add";
		}

		redirectAttribute.addAttribute("search", computerDto.getName());
		return "redirect:/Dashboard";
	}

	@RequestMapping(value = "/Update", method = RequestMethod.POST)
	public String update(
			@ModelAttribute ComputerDTO computerDto,
			@RequestParam("companyId") String companyId,
			RedirectAttributes redirectAttribute) {

		logger.debug("computerDto : {}", computerDto);
		computerDto.setCompany(CompanyDTOImpl.Builder().id(companyId).build());

		// Validation
		Validator validator = validator();
		Set<ConstraintViolation<ComputerDTO>> constraintViolations = validator.validate((ComputerDTO) computerDto);

		if (constraintViolations.isEmpty()) {
			logger.warn("Update is valid, calling service");

			// Mapping
			ComputerMapperImpl computerMapper = new ComputerMapperImpl();
			computerService.update(computerMapper.map(computerDto));

		} else {
			// Error handling
			logger.warn("Error happened on update");
			ValidationErrorPage<ComputerDTO> validationErrorPage = validationErrorPage(
					computerDto, true, constraintViolations, "block");
			logger.debug("Validation error: " + validationErrorPage);
			redirectAttribute.addFlashAttribute(validationErrorPage);

		}
		return "redirect:/Dashboard";
	}

	@RequestMapping(value = "/Delete", method = RequestMethod.POST)
	public String delete(
			@RequestParam("id") String id,
			RedirectAttributes redirectAttribute) {

		ComputerDTO computerDto = ComputerDTOImpl.Builder().id(id).build();

		Validator validator = validator();
		Set<ConstraintViolation<ComputerDTO>> constraintViolations = validator.validateProperty(
				computerDto, "id");

		if (constraintViolations.isEmpty()) {
			logger.info("Delete is valid, calling service");

			// Mapping
			ComputerMapperImpl computerMapper = new ComputerMapperImpl();
			computerService.delete(computerMapper.map(computerDto));

		} else {
			// Error handling
			logger.warn("Error happened on Delete");

			ValidationErrorPage<ComputerDTO> validationErrorPage = validationErrorPage(
					computerDto, null, constraintViolations, "none");

			redirectAttribute.addFlashAttribute(validationErrorPage);

		}
		return "redirect:/Dashboard";
	}

	private ValidationErrorPage<ComputerDTO> validationErrorPage(
			ComputerDTO computerDto,
			Boolean modalShow,
			Set<ConstraintViolation<ComputerDTO>> contraintViolations,
			String hideErrors) {

		return ValidationErrorPage.<ComputerDTO> builder()
				.dto(computerDto)
				.modalShow(modalShow)
				.constraintViolation(contraintViolations)
				.hideErrors(hideErrors)
				.build();
	}

	private Validator validator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		return factory.getValidator();
	}
}
