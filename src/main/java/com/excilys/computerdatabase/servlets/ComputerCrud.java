package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dto.impl.CompanyDTOImpl;
import com.excilys.computerdatabase.dto.impl.ComputerDTOImpl;
import com.excilys.computerdatabase.mappers.impl.CompanyMapperImpl;
import com.excilys.computerdatabase.mappers.impl.ComputerMapperImpl;
import com.excilys.computerdatabase.pages.ValidationErrorPage;
import com.excilys.computerdatabase.services.impl.CompanyServiceImpl;
import com.excilys.computerdatabase.services.impl.ComputerServiceImpl;
import com.excilys.computerdatabase.validators.ComputerValidatorImpl;
import com.excilys.computerdatabase.validators.ErrorCodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		CompanyMapperImpl companyMapper = new CompanyMapperImpl();
		List<CompanyDTOImpl> cyList = companyMapper.invert(CompanyServiceImpl.getInstance()
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
		String action = "Dashboard";

		logger.debug("Parameters :{} {} {} {} {}",
				request.getParameter("create"), request.getParameter("update"),
				request.getParameter("updateValue"),
				request.getParameter("delete"), request.getParameter("id"));

		// -------------------------------Create-------------------------------
		// FIXME Do not create if there is an error !!!
		if (request.getParameter("create") != null) {
			// Dto build
			ComputerDTOImpl computerdto = ComputerDTOImpl.Builder()
					.name(request.getParameter("name"))
					.introduced(request.getParameter("introducedDate"))
					.discontinued(request.getParameter("discontinuedDate"))
					.companydto(
							CompanyDTOImpl.Builder()
									.id(request.getParameter("company"))
									.build())
					.build();
			// Validation
			ComputerValidatorImpl computerV = new ComputerValidatorImpl();
			List<ErrorCodes> errors = computerV.validate(computerdto);

			// if there is only id error which is not relevant
			if (errors.size() == 1 && errors.get(0).getCode() == 1) {
				// Mapping
				ComputerMapperImpl computerMapper = new ComputerMapperImpl();
				ComputerServiceImpl.getInstance().create(
						computerMapper.map(computerdto));

			} else {
				// Error handling
				logger.warn("Error happened on create");
				ValidationErrorPage validationErrorPage = ValidationErrorPage.builder()
						.computerdto(computerdto)
						.modalShow(null)
						.errors(errors)
						.hideErrors("block")
						.build();
				request.getSession().setAttribute("ValidationErrorPage",
						validationErrorPage);
				action = "ComputerCrud";
			}

		}

		// -------------------------------Update-------------------------------
		else if (request.getParameter("update") != null) {
			// Dto build
			ComputerDTOImpl computerdto = ComputerDTOImpl.Builder()
					.id(request.getParameter("id"))
					.name(request.getParameter("name"))
					.introduced(request.getParameter("introducedDate"))
					.discontinued(request.getParameter("discontinuedDate"))
					.companydto(
							CompanyDTOImpl.Builder()
									.id(request.getParameter("company"))
									.build())
					.build();
			// Validation
			ComputerValidatorImpl computerV = new ComputerValidatorImpl();
			List<ErrorCodes> errors = computerV.validate(computerdto);

			if (errors.isEmpty()) {
				logger.warn("Update is valid, calling service");
				// Mapping
				ComputerMapperImpl computerMapper = new ComputerMapperImpl();
				ComputerServiceImpl.getInstance().update(
						computerMapper.map(computerdto));
			} else {
				// Error handling
				logger.warn("Error happened on update");
				ValidationErrorPage validationErrorPage = ValidationErrorPage.builder()
						.computerdto(computerdto)
						.modalShow(true)
						.errors(errors)
						.hideErrors("block")
						.build();
				request.getSession().setAttribute("ValidationErrorPage",
						validationErrorPage);
				action = "Dashboard";
			}

			// -------------------------------Delete-------------------------------
		} else if (request.getParameter("delete") != null) {
			// Dto build
			ComputerDTOImpl computerdto = ComputerDTOImpl.Builder()
					.id(request.getParameter("id"))
					.build();
			// Validation
			ComputerValidatorImpl computerV = new ComputerValidatorImpl();
			List<ErrorCodes> errors = computerV.validate(computerdto);
			Boolean wrongId = false;
			for (ErrorCodes error : errors) {
				if (error.getCode() == 1) {
					// Error handling
					logger.warn("Error happened on delete");
					ValidationErrorPage validationErrorPage = ValidationErrorPage.builder()
							.computerdto(computerdto)
							.modalShow(null)
							.errors(errors)
							.hideErrors("none")
							.build();
					request.getSession().setAttribute("ValidationErrorPage",
							validationErrorPage);
					action = "Dashboard";
					wrongId = true;
				}

			}
			if (!wrongId) {
				// Mapping
				ComputerMapperImpl computerMapper = new ComputerMapperImpl();
				ComputerServiceImpl.getInstance().delete(
						computerMapper.map(computerdto));
			}

		}

		response.sendRedirect(action);
	}
}
