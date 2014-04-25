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

			List<ComputerDTO> cList;
			ComputerMapper computerMapper = new ComputerMapper();
			if (search == null)
				cList = computerMapper.invert(ComputerService.getInstance()
						.find());
			else
				cList = computerMapper.invert(ComputerService.getInstance()
						.find(search));

			logger.debug("Results found : {} ", cList.size());

			// Pagination
			int pageNumber = 1;
			int perPage = 20;

			int nbPages = cList.size() / perPage;
			if (cList.size() % perPage > 0)
				nbPages++;

			if (request.getParameter("pageNumber") != null)
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			if (request.getParameter("perPage") != null)
				perPage = Integer.parseInt(request.getParameter("perPage"));

			int lastEl = ((pageNumber - 1) * perPage + perPage - 1);
			if (lastEl > cList.size())
				lastEl = cList.size();

			logger.debug("Pagination : {} {}", pageNumber, perPage);

			// Company list
			List<Company> cyList = CompanyService.getInstance().find("%");
			logger.debug("Result : {} ", cyList.size());

			// Servlet context attributes
			request.setAttribute("cListSize", cList.size());
			request.setAttribute("nbPages", nbPages);

			request.setAttribute("cList",
					cList.subList((pageNumber - 1) * perPage, lastEl));
			request.setAttribute("cyList", cyList);

			// Get attributes from another servlet
			request.setAttribute("computerdto", request.getSession()
					.getAttribute("computerdto"));
			request.setAttribute("modalShow", request.getSession()
					.getAttribute("modalShow"));
			request.setAttribute("errors",
					request.getSession().getAttribute("errors"));
			request.setAttribute("hideErrors", request.getSession()
					.getAttribute("hideErrors"));

			request.getSession().removeAttribute("computerdto");
			request.getSession().removeAttribute("modalShow");
			request.getSession().removeAttribute("errors");
			request.getSession().removeAttribute("hideErrors");

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
