package com.excilys.computerdatabase.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.mapper.Mapper;
import com.excilys.computerdatabase.mapper.impl.ComputerMapperImpl;
import com.excilys.computerdatabase.page.Page;
import com.excilys.computerdatabase.page.Pagination;
import com.excilys.computerdatabase.page.ValidationErrorPage;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

@Controller
@RequestMapping({ "/", "/Dashboard" })
public class DashboardController {
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;

	@ModelAttribute
	ValidationErrorPage<ComputerDTO> validationErrorPage() {
		return null;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView dashboard(
			@ModelAttribute ValidationErrorPage<ComputerDTO> validationErrorPage) {
		logger.debug("Error received: {}", validationErrorPage);

		ModelAndView modelView = new ModelAndView("dashboard");
		return modelView;
	}

	// TODO Move to ComputerCrud as get request and define a call to render
	// modal form
	@RequestMapping("/computer/add")
	public ModelAndView add(
			@ModelAttribute ValidationErrorPage<ComputerDTO> validationErrorPage) {

		ModelAndView modelView = new ModelAndView("addComputer");

		return modelView;
	}

	/**
	 * Define the company list
	 * 
	 * @param search
	 * @return
	 */
	@ModelAttribute("companyList")
	private List<Company> companyList() {
		return companyService.find("");
	}

	@ModelAttribute("total")
	private Integer total(@RequestParam(defaultValue = "") String search) {

		// @RequestParam(required = false) String search
		logger.debug("Total");
		return computerService.count(search);

	}

	/**
	 * Define pagination state of computer dashboard
	 * 
	 * @param currentPage
	 * @param elementsPerPage
	 * @param search
	 * @return
	 */
	// TODO clean that shit
	// Define setter and getter in pagination ?
	@ModelAttribute
	private Pagination pagination(
			@RequestParam(defaultValue = "1") Integer currentPage,
			@RequestParam(value = "elementsPerPage", defaultValue = "20") Integer elementsPerPage,
			@RequestParam(defaultValue = "") String search) {
		logger.debug("Pagination build");
		Integer total = computerService.count(search);

		int startPage = currentPage - 4;
		int nbPages = total / elementsPerPage;
		int endPage = currentPage + 4;

		if (elementsPerPage > total) {
			currentPage = 1;
			startPage = 1;
			endPage = 1;

		} else {

			if (startPage < 1) {
				endPage += Math.abs(startPage) + 1;
				startPage = 1;
			}

			if (total % elementsPerPage > 0) {
				nbPages++;
			}

			if (currentPage > nbPages) {
				currentPage = nbPages;
				endPage = nbPages;

			} else if (endPage > nbPages) {
				endPage = nbPages;
			}
		}

		return Pagination.builder()
				.currentPage(currentPage)
				.elementsPerPage(elementsPerPage)
				.endPage(endPage)
				.nbPages(nbPages)
				.startPage(startPage)
				.build();
	}

	/**
	 * Populate a page into the dashboard
	 * 
	 * @param page
	 * @param currentPage
	 * @param elementsPerPage
	 * @return
	 */
	@ModelAttribute("page")
	private Page<ComputerDTO> dashboardPage(
			@ModelAttribute Page<ComputerDTO> page,
			@RequestParam(defaultValue = "1") Integer currentPage,
			@RequestParam(value = "elementsPerPage", defaultValue = "20") Integer elementsPerPage) {

		logger.debug("Page : {}, currentPage: {}, elementsPerPage: {}  ", page,
				currentPage, elementsPerPage);

		page.setComputerList(computerList(page.getSearch(),
				page.getOrderById(), currentPage, elementsPerPage,
				page.getOrderDirection()));
		return page;
	}

	@ExceptionHandler({ NoHandlerFoundException.class,
			NoSuchRequestHandlingMethodException.class })
	public String handleIOException() {
		logger.debug("Bouh");
		return "error";
	}

	/**
	 * Service request to obtain computerList
	 * 
	 * @param search
	 * @param orderById
	 * @param currentPage
	 * @param elementsPerPage
	 * @param orderDirection
	 * @return
	 */
	private List<ComputerDTO> computerList(
			String search,
			Long orderById,
			Integer currentPage,
			Integer elementsPerPage,
			Boolean orderDirection) {

		Mapper<ComputerDTO, Computer> computerMapper = new ComputerMapperImpl();
		return computerMapper.invert(computerService.find(search,
				(currentPage - 1) * elementsPerPage, elementsPerPage,
				orderById, orderDirection));
	}
}
