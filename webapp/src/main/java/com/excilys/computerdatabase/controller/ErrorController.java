package com.excilys.computerdatabase.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	@RequestMapping("/error")
	public ModelAndView handleGeneral(HttpServletRequest request) {
		ModelAndView modelView = new ModelAndView("error");

		modelView.addObject("errorCode",
				request.getAttribute("javax.servlet.error.status_code"));

		modelView.addObject("message",
				"An equip of highly trained monkey is going to resolve this problem !");
		return modelView;
	}

	@RequestMapping("/error/400")
	public ModelAndView handle400() {
		ModelAndView modelView = new ModelAndView("error");

		modelView.addObject("errorCode", 400);
		modelView.addObject("message",
				"You have made a bad request... check your request parameters !");

		return modelView;
	}

	@RequestMapping("/error/401")
	public ModelAndView handle401() {
		ModelAndView modelView = new ModelAndView("error");

		modelView.addObject("errorCode", 401);
		modelView.addObject("message", "You have no right to do this ! ");

		return modelView;
	}

	@RequestMapping("/error/403")
	public ModelAndView handle403() {
		ModelAndView modelView = new ModelAndView("error");

		modelView.addObject("errorCode", 403);
		modelView.addObject("message", "No way... ");

		return modelView;
	}

	@RequestMapping("/error/404")
	public ModelAndView handle404() {
		ModelAndView modelView = new ModelAndView("error");

		modelView.addObject("errorCode", 404);
		modelView.addObject("message",
				"The adress you indicated might contains error");

		return modelView;
	}

	@RequestMapping("/error/500")
	public ModelAndView handle500() {
		ModelAndView modelView = new ModelAndView("error");

		modelView.addObject("errorCode", 500);
		modelView.addObject("message", "Something baaad happens...");

		return modelView;
	}
}
