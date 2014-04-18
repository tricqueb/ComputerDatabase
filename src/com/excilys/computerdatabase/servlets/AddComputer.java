package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(AddComputer.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("GET !");

		// CompanyList
		List<Company> cyList = CompanyService.getInstance().find("%");
		request.setAttribute("cyList", cyList);
		// forward
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/WEB-INF/addComputer.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("POST !");
		Context ctx;
		try {
			ctx = new InitialContext();

			// Gettings parameters
			String name = request.getParameter("name");
			String introducedDateString = request
					.getParameter("introducedDate");
			String discontinuedDateString = request
					.getParameter("discontinuedDate");
			String companyIdString = request.getParameter("company");
			logger.debug("Getting parameters {} {} {} {}", name,
					introducedDateString, discontinuedDateString,
					companyIdString);

			Date introducedDate = null;
			Date discontinuedDate = null;
			if (!introducedDateString.isEmpty())
				introducedDate = Date.valueOf(introducedDateString);
			if (!discontinuedDateString.isEmpty())
				discontinuedDate = Date.valueOf(discontinuedDateString);

			Long companyId = null;
			Company cy = null;
			Computer c = null;
			if (!"null".contentEquals(companyIdString)) {
				companyId = Long.parseLong(companyIdString);
				logger.debug("Getting company !");
				// Getting corresponding company
				cy = CompanyService.getInstance().find(companyId);
				logger.debug("The company : {}", cy);
			}
			c = new Computer(cy, name, introducedDate, discontinuedDate);

			logger.debug("Computer parameters : {} {} {} {}", name,
					introducedDate, discontinuedDate, companyId);

			// Adding new computer to db
			logger.debug("Ajout de {} de la companie {}", name, c);
			ComputerService.getInstance().create(c);

			// Back to main jsp
			response.sendRedirect("Dashboard");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
