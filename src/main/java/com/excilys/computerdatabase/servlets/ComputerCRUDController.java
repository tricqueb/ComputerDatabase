package com.excilys.computerdatabase.servlets;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.computerdatabase.connections.ConnectionManager;
import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.dto.impl.CompanyDTOImpl;
import com.excilys.computerdatabase.dto.impl.ComputerDTOImpl;
import com.excilys.computerdatabase.mappers.impl.ComputerMapperImpl;
import com.excilys.computerdatabase.pages.ValidationErrorPage;
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;

@Controller
@RequestMapping("/Computer")
public class ComputerCRUDController {
	private static final Logger logger = LoggerFactory.getLogger(ComputerCRUDController.class);

	@Autowired
	private ConnectionManager cm;
	@Autowired
	private CompanyService companyService;
	@Autowired
	ComputerService computerService;

	@ModelAttribute
	private ComputerDTO computerDto() {
		return new ComputerDTOImpl();
	}

	// TODO Perform a search on the new element after adding it ?
	@RequestMapping(value = "/Create", method = RequestMethod.POST)
	public String create(
			@ModelAttribute ComputerDTO computerDto,
			@RequestParam("companyId") String companyId,

			RedirectAttributes redirectAttribute) {

		computerDto.setCompany(CompanyDTOImpl.Builder().id(companyId).build());

		// Validation
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ComputerDTO>> constraintViolations = validator.validateProperty(
				computerDto, "name");

		constraintViolations.addAll(validator.validateProperty(computerDto,
				"companyDto"));
		// ------------
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
			redirectAttribute.addFlashAttribute(validationErrorPage);

		}
		return "redirect:/Dashboard";
	}

	@RequestMapping(value = "/Delete", method = RequestMethod.POST)
	public String delete(
			@RequestParam("id") Long id,
			RedirectAttributes redirectAttribute) {

		// FIXME weak type conversion
		ComputerDTO computerDto = ComputerDTOImpl.Builder()
				.id(id.toString())
				.build();

		Validator validator = validator();
		Set<ConstraintViolation<ComputerDTO>> constraintViolations = validator.validateProperty(
				computerDto, "id");

		// Error can't be empty
		if (constraintViolations.isEmpty()) {
			logger.warn("Delete is valid, calling service");

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
				.modalShow(null)
				.constraintViolation(contraintViolations)
				.hideErrors("block")
				.build();
	}

	private Validator validator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		return factory.getValidator();
	}
}
