package com.excilys.computerdatabase.servlets;

import java.io.IOException;
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
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/ComputerCrud")
public class ComputerCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(ComputerCrud.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ComputerCrud() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * 
	 *      TODO Add read (get) request
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

			logger.debug("Parameters :{} {} {} {} {}",
					request.getParameter("create"),
					request.getParameter("update"),
					request.getParameter("updateValue"),
					request.getParameter("delete"),
					request.getParameter("deleteValue"));

			// -------------------------------Create-------------------------------
			if (request.getParameter("create") != null) {
				// Gettings parameters
				if (!ComputerService.getInstance().create(
						request.getParameter("name"),
						request.getParameter("introducedDate"),
						request.getParameter("discontinuedDate"),
						request.getParameter("company")))
					response.sendError(404, "Error on creation");

			}
			// -------------------------------Update-------------------------------
			else if (request.getParameter("update") != null) {
				if (!ComputerService.getInstance().update(
						request.getParameter("id"),
						request.getParameter("name"),
						request.getParameter("introducedDate"),
						request.getParameter("discontinuedDate"),
						request.getParameter("company")))
					response.sendError(404, "Error on deletion");

			} else
			// -------------------------------Delete-------------------------------
			// FIXME SendError not working - Cant make a sendRedirect after a
			// sendError
			if (request.getParameter("delete") != null) {
				if (!ComputerService.getInstance().delete(
						request.getParameter("deleteValue")))
					response.sendError(404, "Error on deletion");
			}

		} catch (NamingException e) {
			logger.error("Context error {}", e.getMessage());
			e.printStackTrace();
		}
		response.sendRedirect("Dashboard");
	}
}
