package com.excilys.computerdatabase.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Dashboard")
public class DashboardController {
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@RequestMapping("")
	public String dashboard(
	// @RequestParam(value = "search", required = false, defaultValue = "")
	// String search, Model model
	) {
		// model.addAttribute("search", search);
		logger.debug("WAZAAAAAAAAAAAAAAAA !");
		System.out.println("poil");
		return "dashboard";
	}
}
