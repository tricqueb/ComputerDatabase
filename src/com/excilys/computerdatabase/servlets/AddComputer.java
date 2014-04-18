package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		Context ctx;
		try {
			ctx = new InitialContext();

			// Gettings parameters
			String name = request.getParameter("name");
			Date introducedDate = Date.valueOf(request
					.getParameter("introducedDate"));
			Date discontinuedDate = Date.valueOf(request
					.getParameter("discontinuedDate"));
			Long companyId = Long.parseLong(request.getParameter("company"));

			// Getting corresponding company
			Company cy = CompanyService.getInstance().find(companyId);
			Computer c = new Computer(cy, name, introducedDate,
					discontinuedDate);

			// Adding new computer to db
			System.out.println("###Dashboard - Ajout de " + name
					+ " de la companie " + c.getCompany().getName());
			ComputerService.getInstance().create(c);

			// Back to main jsp
			response.sendRedirect("Dashboard");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
