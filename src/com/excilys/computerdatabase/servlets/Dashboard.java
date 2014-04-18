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

import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabase.services.ComputerService;

/**
 * Servlet implementation class Dashboard
 */
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(Dashboard.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dashboard() {
		super();
		// TODO: Decide between this and dedicated servlet in web.xml
		LoggerContext logCtx = (LoggerContext) LoggerFactory
				.getILoggerFactory();
		StatusPrinter.print(logCtx);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("GET !");

		Context ctx;
		try {
			ctx = new InitialContext();

			// Search filter
			String search = request.getParameter("search");

			List<Computer> cList;
			if (search == null)
				cList = ComputerService.getInstance().find();
			else
				cList = ComputerService.getInstance().find(search);
			logger.debug("Parameters : {} ", search);
			logger.debug("Result : {} ", cList);

			// Servlet context attributes
			request.setAttribute("cList", cList);

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
