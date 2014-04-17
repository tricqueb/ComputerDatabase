package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;

/**
 * Servlet implementation class Dashboard
 */
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dashboard() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Context ctx;
		try {
			ctx = new InitialContext();

			// CompanyList
			List<Company> cyList = CompanyService.getInstance().find("%");

			// Search filter
			String search = request.getParameter("search");
			if (search == null)
				search = "%";
			System.out.println("Search: " + search);
			List<Computer> cList = ComputerService.getInstance().find(search);

			System.out.println("Computers List " + cList);

			// Servlet context attributes
			getServletContext().setAttribute("cList", cList);
			getServletContext().setAttribute("cyList", cyList);

			// forward
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/dashboard.jsp");
			rd.forward(request, response);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
			String name = request.getParameter("name");
			Date introducedDate = Date.valueOf(request
					.getParameter("introducedDate"));
			Date discontinuedDate = Date.valueOf(request
					.getParameter("discontinuedDate"));
			Long companyId = Long.parseLong(request.getParameter("company"));

			Company cy = CompanyService.getInstance().find(companyId);

			Computer c = new Computer(cy, name, introducedDate,
					discontinuedDate);
			System.out.println("Dashboard - Ajout de " + name
					+ " de la companie " + c.getCompany().getName());
			ComputerService.getInstance().create(c);

			// RequestDispatcher rd =
			// getServletContext().getRequestDispatcher("/computer-database/Dashboard");

			// rd.forward(request, response);
			doGet(request, response);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
