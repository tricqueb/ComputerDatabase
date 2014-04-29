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

import com.excilys.computerdatabase.dto.CompanyDTO;
import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.mappers.CompanyMapper;
import com.excilys.computerdatabase.mappers.ComputerMapper;
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;
import com.excilys.computerdatabase.validators.ComputerValidator;
import com.excilys.computerdatabase.validators.ErrorCodes;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/ComputerCrud")
public class ComputerCrud extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ComputerCrud.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ComputerCrud() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * 
	 *      TODO Add read (get) request
	 */
	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("GET !");

		// Getting companies list
		CompanyMapper companyMapper = new CompanyMapper();
		List<CompanyDTO> cyList = companyMapper.invert(CompanyService.getInstance()
				.find("%"));
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
	protected void doPost(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("POST !");
		Context ctx;
		String action = "Dashboard";
		try {
			ctx = new InitialContext();

			logger.debug("Parameters :{} {} {} {} {}",
					request.getParameter("create"),
					request.getParameter("update"),
					request.getParameter("updateValue"),
					request.getParameter("delete"), request.getParameter("id"));

			// -------------------------------Create-------------------------------
			// FIXME Do not create if there is an error !!!
			if (request.getParameter("create") != null) {
				// Dto build
				ComputerDTO computerdto = ComputerDTO.Builder()
						.name(request.getParameter("name"))
						.introduced(request.getParameter("introducedDate"))
						.discontinued(request.getParameter("discontinuedDate"))
						.companydto(
								CompanyDTO.Builder()
										.id(request.getParameter("company"))
										.build())
						.build();
				// Validation
				ComputerValidator computerV = new ComputerValidator();
				List<ErrorCodes> errors = computerV.validate(computerdto);

				// if there is only id error
				if (errors.size() == 1 && errors.get(0).getCode() == 1) {
					// Error handling
					// Mapping
					ComputerMapper computerMapper = new ComputerMapper();
					ComputerService.getInstance().create(
							computerMapper.map(computerdto));

				} else {
					// Error handling
					logger.warn("Error happened on create");
					request.getSession().setAttribute("computerdto",
							computerdto);
					request.getSession().setAttribute("errors", errors);
					request.getSession().setAttribute("hideErrors", "block");
					action = "ComputerCrud";
				}

			}

			// -------------------------------Update-------------------------------
			else if (request.getParameter("update") != null) {
				// Dto build
				ComputerDTO computerdto = ComputerDTO.Builder()
						.id(request.getParameter("id"))
						.name(request.getParameter("name"))
						.introduced(request.getParameter("introducedDate"))
						.discontinued(request.getParameter("discontinuedDate"))
						.companydto(
								CompanyDTO.Builder()
										.id(request.getParameter("company"))
										.build())
						.build();
				// Validation
				ComputerValidator computerV = new ComputerValidator();
				List<ErrorCodes> errors = computerV.validate(computerdto);

				if (errors.isEmpty()) {
					logger.warn("Update is valid, calling service");
					// Mapping
					ComputerMapper computerMapper = new ComputerMapper();
					ComputerService.getInstance().update(
							computerMapper.map(computerdto));
				} else {
					// Error handling
					logger.warn("Error happened on update");
					request.getSession().setAttribute("computerdto",
							computerdto);
					request.getSession().setAttribute("errors", errors);
					request.getSession().setAttribute("modalShow", "true");
					request.getSession().setAttribute("hideErrors", "block");
					action = "Dashboard";
				}

				// -------------------------------Delete-------------------------------
			} else if (request.getParameter("delete") != null) {
				// Dto build
				ComputerDTO computerdto = ComputerDTO.Builder()
						.id(request.getParameter("id"))
						.build();
				// Validation
				ComputerValidator computerV = new ComputerValidator();
				List<ErrorCodes> errors = computerV.validate(computerdto);
				Boolean wrongId = false;
				for (ErrorCodes error : errors) {
					if (error.getCode() == 1) {
						// Error handling
						logger.warn("Error happened on delete");
						request.setAttribute("errors", errors);
						action = "Dashboard";
						wrongId = true;
					}

				}
				if (!wrongId) {
					// Mapping
					ComputerMapper computerMapper = new ComputerMapper();
					ComputerService.getInstance().delete(
							computerMapper.map(computerdto));
				}

			}

		} catch (NamingException e) {
			logger.error("Context error {}", e.getMessage());
			e.printStackTrace();
		}
		response.sendRedirect(action);
	}
}
