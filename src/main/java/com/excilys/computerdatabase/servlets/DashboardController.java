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
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.pages.Page;
import com.excilys.computerdatabase.pages.Pagination;
import com.excilys.computerdatabase.pages.ValidationErrorPage;
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;

@Controller
@RequestMapping("/Dashboard")
public class DashboardController {
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;

	@ModelAttribute
	ValidationErrorPage validationErrorPage() {
		return null;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView dashboard(
			@ModelAttribute ValidationErrorPage validationErrorPage) {
		logger.debug("Error received: {}", validationErrorPage);

		ModelAndView modelView = new ModelAndView("dashboard");
		return modelView;
	}

	// TODO Move to ComputerCrud as get request
	@RequestMapping("/computer/add")
	public ModelAndView add(
			@ModelAttribute ValidationErrorPage validationErrorPage) {

		// List<Company> companyList = companyList("%");
		ModelAndView modelView = new ModelAndView("addComputer");

		// modelView.addObject("companyList", companyList);
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

		int startPage = currentPage - 5;

		if (startPage < 1) {
			startPage = 1;
		}

		int nbPages = total / elementsPerPage;
		if (total % elementsPerPage > 0)
			nbPages++;

		if (currentPage > nbPages)
			currentPage = nbPages;

		if (elementsPerPage > total)
			elementsPerPage = total;

		int endPage = currentPage + 5;
		if (endPage > nbPages) {
			endPage = nbPages;
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
	private Page<Computer> dashboardPage(
			@ModelAttribute Page<Computer> page,
			@RequestParam(defaultValue = "1") Integer currentPage,
			@RequestParam(value = "elementsPerPage", defaultValue = "20") Integer elementsPerPage) {

		logger.debug("Page : {}, currentPage: {}, elementsPerPage: {}  ", page,
				currentPage, elementsPerPage);

		page.setComputerList(computerList(page.getSearch(),
				page.getOrderById(), currentPage, elementsPerPage,
				page.getOrderDirection()));
		return page;
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
	private List<Computer> computerList(
			String search,
			Long orderById,
			Integer currentPage,
			Integer elementsPerPage,
			Boolean orderDirection) {

		return computerService.find(search,
				(currentPage - 1) * elementsPerPage, elementsPerPage,
				orderById, orderDirection);
	}
}
