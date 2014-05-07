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
import com.excilys.computerdatabase.pages.Page.PageBuilder;
import com.excilys.computerdatabase.pages.Pagination;
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

	// TODO Create dashboard model
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView dashboard(
			@ModelAttribute(value = "search") String search,
			@RequestParam(value = "orderById", required = false, defaultValue = "2") Long orderById,
			@RequestParam(value = "orderDirection", required = false, defaultValue = "false") Boolean orderDirection,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
			@RequestParam(value = "elementsPerPage", required = false, defaultValue = "20") Integer elementsPerPage) {

		logger.debug(
				"Search : {}, Order by: {}, Direction(ACS>false): {}, currentPage: {},elementsPerPage: {}  ",
				search, orderById, orderDirection, currentPage, elementsPerPage);

		Integer total = computerService.count(search);

		List<Computer> computerList = computerList(search, currentPage,
				elementsPerPage, orderById, orderDirection);

		List<Company> companyList = companyList("%");

		Pagination pagination = pagination(currentPage, elementsPerPage, total);

		Page<Computer> dashboardPage = dashboardPageBuilder(computerList,
				total, pagination, orderById, orderDirection);

		// TODO ValidationErrorPage coming from DashboardCrud (flash attribute
		// ?)

		ModelAndView modelView = new ModelAndView("dashboard");

		// modelView.addObject("search", search);
		modelView.addObject("companyList", companyList);
		modelView.addObject("page", dashboardPage);

		return modelView;
	}

	private List<Computer> computerList(
			String search,
			int currentPage,
			int elementsPerPage,
			Long orderById,
			Boolean orderDirection) {

		return computerService.find(search,
				(currentPage - 1) * elementsPerPage, elementsPerPage,
				orderById, orderDirection);

	}

	private List<Company> companyList(String search) {
		return companyService.find(search);
	}

	// TODO clean that shit
	private Pagination pagination(
			int currentPage,
			int elementsPerPage,
			int total) {

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

	private Page<Computer> dashboardPageBuilder(
			List<Computer> computerList,
			int total,
			Pagination pagination,
			Long orderById,
			Boolean orderDirection) {

		PageBuilder<Computer> pageBuilder = Page.builder();
		pageBuilder.computerList(computerList)
				.total(total)
				.pagination(pagination)
				.orderById(orderById)
				.orderDirection(orderDirection);
		return pageBuilder.build();
	}

	@RequestMapping("/computer/add")
	public ModelAndView add() {

		List<Company> companyList = companyList("%");
		ModelAndView modelView = new ModelAndView("addComputer");

		modelView.addObject("companyList", companyList);
		return modelView;
	}
}
