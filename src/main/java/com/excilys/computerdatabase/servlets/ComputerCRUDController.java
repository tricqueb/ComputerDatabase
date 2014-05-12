package com.excilys.computerdatabase.servlets;

import java.util.List;

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
import com.excilys.computerdatabase.validators.ComputerValidatorImpl;
import com.excilys.computerdatabase.validators.ErrorCodes;
import com.excilys.computerdatabase.validators.Validator;

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

	// TODO Perform a search on the new element after adding it ?
	@RequestMapping(value = "/Create", method = RequestMethod.POST)
	public String create(
			@ModelAttribute("computerDto") ComputerDTOImpl computerDto,
			@RequestParam("companyId") String companyId,
			RedirectAttributes redirectAttribute) {

		computerDto.setCompany(CompanyDTOImpl.Builder().id(companyId).build());
		// Validation
		Validator<ComputerDTO> computerV = new ComputerValidatorImpl();
		List<ErrorCodes> errors = computerV.validate(computerDto);

		// if there is only id error which is not relevant
		// TODO Improve error test
		if (errors.size() == 1 && errors.get(0).getCode() == 1) {
			// Mapping
			ComputerMapperImpl computerMapper = new ComputerMapperImpl();
			computerService.create(computerMapper.map(computerDto));

		} else {
			// Error handling
			logger.warn("Error happened on create");
			// TODO Change modalShow=null to false && hiderErrors=Block
			ValidationErrorPage validationErrorPage = validationErrorPage(
					computerDto, null, errors, "block");
			redirectAttribute.addFlashAttribute(validationErrorPage);
			return "redirect:/Dashboard/computer/add";
		}

		redirectAttribute.addAttribute("search", computerDto.getName());
		return "redirect:/Dashboard";
	}

	@RequestMapping(value = "/Update", method = RequestMethod.POST)
	public String update(
			@ModelAttribute("computerDto") ComputerDTOImpl computerDto,
			@RequestParam("companyId") String companyId,
			RedirectAttributes redirectAttribute) {

		logger.debug("computerDto : {}", computerDto);
		computerDto.setCompany(CompanyDTOImpl.Builder().id(companyId).build());

		Validator<ComputerDTO> computerV = new ComputerValidatorImpl();
		List<ErrorCodes> errors = computerV.validate(computerDto);

		if (errors.isEmpty()) {
			logger.warn("Update is valid, calling service");

			// Mapping
			ComputerMapperImpl computerMapper = new ComputerMapperImpl();
			computerService.update(computerMapper.map(computerDto));

		} else {
			// Error handling
			logger.warn("Error happened on update");
			ValidationErrorPage validationErrorPage = validationErrorPage(
					computerDto, true, errors, "block");
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

		Validator<ComputerDTO> computerV = new ComputerValidatorImpl();
		List<ErrorCodes> errors = computerV.validate(computerDto);

		// Error can't be empty
		if (errors.get(0).getCode() != 1) {
			logger.warn("Delete is valid, calling service");

			// Mapping
			ComputerMapperImpl computerMapper = new ComputerMapperImpl();
			computerService.delete(computerMapper.map(computerDto));

		} else {
			// Error handling
			logger.warn("Error happened on Delete");

			ValidationErrorPage validationErrorPage = validationErrorPage(
					computerDto, null, errors, "none");
			redirectAttribute.addFlashAttribute(validationErrorPage);

		}
		return "redirect:/Dashboard";
	}

	private ValidationErrorPage validationErrorPage(
			ComputerDTO computerDto,
			Boolean modalShow,
			List<ErrorCodes> errors,
			String hideErrors) {

		return ValidationErrorPage.builder()
				.computerDto(computerDto)
				.modalShow(null)
				.errors(errors)
				.hideErrors("block")
				.build();
	}
}
