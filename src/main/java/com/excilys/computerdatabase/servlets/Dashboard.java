package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.dto.impl.ComputerDTOImpl;
import com.excilys.computerdatabase.mappers.impl.ComputerMapperImpl;
import com.excilys.computerdatabase.pages.Page;
import com.excilys.computerdatabase.pages.Page.PageBuilder;
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;

/**
 * Servlet implementation class Dashboard
 */
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Dashboard.class);

	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dashboard() {
		super();
		// TODO: Decide between this and dedicated servlet in web.xml
		// LoggerContext logCtx = (LoggerContext)
		// LoggerFactory.getILoggerFactory();
		// StatusPrinter.print(logCtx);

		// ApplicationContext context = new ClassPathXmlApplicationContext(
		// "applicationContext.xml");
		// companyService = (CompanyService) context.getBean("companyService");
		// computerService = (ComputerService)
		// context.getBean("computerService");

		// logger.debug(context.getBean("computerDao").toString());

	}

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				getServletContext());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("GET !");

		// Search filter
		String search = request.getParameter("search");
		logger.debug("Search : {} ", search);

		Long orderBy = 1l;
		Boolean desc = false;

		// Orderby
		if (request.getParameter("orderby") != null)
			if (request.getParameter("orderby").matches("^[2-6]$")) {
				orderBy = Long.parseLong(request.getParameter("orderby"));
				logger.debug("Order by: {}", orderBy);
			}

		// Desc
		if (request.getParameter("desc") != null)
			if ("true".contentEquals(request.getParameter("desc")))
				desc = true;

		logger.debug("Desc ?: {}", desc);

		Integer total = null;
		if (search == null || search.isEmpty())
			total = computerService.count("%");
		else
			total = computerService.count(search);
		logger.debug("Total count result : {} ", total);
		// Pagination
		int currentPage = 1;
		int elementsPerPage = 20;

		// Change 'pageNumber' to currentPage
		if (request.getParameter("pageNumber") != null)
			currentPage = Integer.parseInt(request.getParameter("pageNumber"));
		if (request.getParameter("perPage") != null)
			elementsPerPage = Integer.parseInt(request.getParameter("perPage"));

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

		List<ComputerDTOImpl> cList;
		ComputerMapperImpl computerMapper = new ComputerMapperImpl();
		if (search == null || search.isEmpty())
			cList = computerMapper.invert(computerService.find("%",
					(currentPage - 1) * elementsPerPage, elementsPerPage,
					orderBy, desc));
		else
			cList = computerMapper.invert(computerService.find(search,
					(currentPage - 1) * elementsPerPage, elementsPerPage,
					orderBy, desc));

		int endPage = currentPage + 5;
		if (endPage > nbPages) {
			// startPage = startPage - (cList.size() - endPage);
			endPage = nbPages;
		}

		logger.debug("Results found : {} ", cList.size());

		logger.debug(
				"Pagination : Number of pages: {} Elements per page: {} Start/End of pagination : {} {}",
				nbPages, elementsPerPage, startPage, endPage);

		// Company list
		List<Company> cyList = companyService.find("%");
		logger.debug("Company list - Result : {} ", cyList.size());

		// Servlet context attributes

		// search
		request.setAttribute("search", search);

		// pagination
		PageBuilder<ComputerDTOImpl> pageBuilder = Page.builder();
		pageBuilder.cList(cList)
				.total(total)
				.currentPage(currentPage)
				.startPage(startPage)
				.endPage(endPage)
				.orderBy(orderBy)
				.desc(desc);

		request.setAttribute("page", pageBuilder.build());

		// computer list
		logger.debug("Results found : {} ", total);
		request.setAttribute("cyList", cyList);

		// Get attributes from another servlet
		// Errors attributes

		request.setAttribute("validationErrorPage", request.getSession()
				.getAttribute("validationErrorPage"));

		if (request.getSession().getAttribute("validationErrorPage") != null) {
			logger.debug("Removing session attributes");
			request.getSession().removeAttribute("validationErrorPage");
		}

		// forward
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/WEB-INF/dashboard.jsp");
		rd.forward(request, response);

	}
}
