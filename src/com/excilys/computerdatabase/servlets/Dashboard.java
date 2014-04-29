package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.mappers.ComputerMapper;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;

/**
 * Servlet implementation class Dashboard
 */
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Dashboard.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dashboard() {
		super();
		// TODO: Decide between this and dedicated servlet in web.xml
		LoggerContext logCtx = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(logCtx);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("GET !");

		Context ctx;
		try {
			ctx = new InitialContext();

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

			Integer cListSize = null;
			if (search == null || search.isEmpty())
				cListSize = ComputerService.getInstance().count("%");
			else
				cListSize = ComputerService.getInstance().count(search);
			logger.debug("Total count result : {} ", cListSize);
			// Pagination
			int currentPage = 1;
			int perPage = 20;

			// Change 'pageNumber' to currentPage
			if (request.getParameter("pageNumber") != null)
				currentPage = Integer.parseInt(request.getParameter("pageNumber"));
			if (request.getParameter("perPage") != null)
				perPage = Integer.parseInt(request.getParameter("perPage"));

			int startPage = currentPage - 5;

			if (startPage < 1) {
				startPage = 1;
			}

			// int lastEl = ((currentPage - 1) * perPage + perPage);
			// if (lastEl > cListSize)
			// lastEl = cListSize;

			int nbPages = cListSize / perPage;
			if (cListSize % perPage > 0)
				nbPages++;

			if (currentPage > nbPages)
				currentPage = nbPages;

			if (perPage > cListSize)
				perPage = cListSize;

			List<ComputerDTO> cList;
			ComputerMapper computerMapper = new ComputerMapper();
			if (search == null || search.isEmpty())
				cList = computerMapper.invert(ComputerService.getInstance()
						.find("%", (currentPage - 1) * perPage, perPage,
								orderBy, desc));
			else
				cList = computerMapper.invert(ComputerService.getInstance()
						.find(search, (currentPage - 1) * perPage, perPage,
								orderBy, desc));

			int endPage = currentPage + 5;
			if (endPage > nbPages) {
				// startPage = startPage - (cList.size() - endPage);
				endPage = nbPages;
			}

			logger.debug("Results found : {} ", cList.size());

			logger.debug(
					"Pagination : Number of pages: {} Elements per page: {} Start/End of pagination : {} {}",
					nbPages, perPage, startPage, endPage);

			// Company list
			List<Company> cyList = CompanyService.getInstance().find("%");
			logger.debug("Company list - Result : {} ", cyList.size());

			// Servlet context attributes

			// search
			request.setAttribute("search", search);

			// order By
			request.setAttribute("orderby", orderBy);
			request.setAttribute("desc", desc);

			// pagination
			request.setAttribute("cListSize", cListSize);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);

			// computer list
			logger.debug("Results found : {} ", cListSize);
			request.setAttribute("cList", cList);
			request.setAttribute("cyList", cyList);

			// Get attributes from another servlet
			// Errors attributes
			request.setAttribute("computerdto", request.getSession()
					.getAttribute("computerdto"));
			request.setAttribute("modalShow", request.getSession()
					.getAttribute("modalShow"));
			request.setAttribute("errors",
					request.getSession().getAttribute("errors"));
			request.setAttribute("hideErrors", request.getSession()
					.getAttribute("hideErrors"));

			if (request.getSession().getAttribute("hideErrors") != null) {
				logger.debug("Removing session attributes");
				request.getSession().removeAttribute("computerdto");
				request.getSession().removeAttribute("modalShow");
				request.getSession().removeAttribute("errors");
				request.getSession().removeAttribute("hideErrors");
			}

			// forward
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/WEB-INF/dashboard.jsp");
			rd.forward(request, response);

		} catch (NamingException e) {
			logger.error("Naming {}", e.getMessage());
			e.printStackTrace();
		}
	}
}
