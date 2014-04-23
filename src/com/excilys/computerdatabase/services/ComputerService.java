package com.excilys.computerdatabase.services;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

public enum ComputerService {
	INSTANCE;
	private static final Logger logger = LoggerFactory
			.getLogger(ComputerService.class);

	private ComputerService() {
	}

	public static ComputerService getInstance() {
		return INSTANCE;
	}

	public boolean create(String name, String introducedDateS,
			String discontinuedDateS, String companyIdS) {

		logger.debug("Getting parameters {} {} {} {}", name, introducedDateS,
				discontinuedDateS, companyIdS);

		Date introducedDate = null;
		Date discontinuedDate = null;
		Long companyId = null;
		Company cy = null;
		Computer c = null;

		try {
			if (!introducedDateS.isEmpty())
				introducedDate = Date.valueOf(introducedDateS);
			if (!discontinuedDateS.isEmpty())
				discontinuedDate = Date.valueOf(discontinuedDateS);
		} catch (IllegalArgumentException e) {
			logger.error("Error on date format {}" + e.getMessage());
			return false;
		}

		if (!"null".contentEquals(companyIdS)) {
			companyId = Long.parseLong(companyIdS);
			logger.debug("Getting company !");
			// Getting corresponding company
			cy = CompanyService.getInstance().find(companyId);
			logger.debug("The company : {}", cy);
		}
		c = Computer.Builder().company(cy).name(name)
				.introduced(introducedDate).discontinued(discontinuedDate)
				.build();

		logger.debug("Computer parameters : {} {} {} {}", name, introducedDate,
				discontinuedDate, companyId);

		// Adding new computer to db
		logger.debug("Ajout de {} de la companie {}", name, c);

		if (!c.getName().isEmpty())
			if (c.getId() == null) {
				ComputerDao.getInstance().create(c);
				return true;
			} else
				logger.error("Id is not null");
		else
			logger.error("Name is not set");
		return false;
	}

	public void delete(Computer c) {
		ComputerDao.getInstance().delete(c);
	}

	public boolean delete(String idS) {
		if (idS != null) {
			Long computerId = Long.parseLong(idS);
			ComputerDao.getInstance().delete(computerId);
			logger.debug("Element {} has been deleted", computerId);
			return true;
		} else
			logger.error("Id computer is null");
		return false;
	}

	public Computer find(Long cId) {
		return ComputerDao.getInstance().find(cId);

	}

	public List<Computer> find(String cName) {
		return ComputerDao.getInstance().find(cName);

	}

	/**
	 * 
	 * @param c
	 * @return Map<ComputerId,Company>
	 * @Warning DO NOT USE
	 */
	public Map<Long, Company> find(List<Computer> c) {
		// return ComputerDao.getInstance().getCompanies(c);
		return null;

	}

	public void update(Computer c) {
		ComputerDao.getInstance().update(c);
	}

	public boolean update(String idS, String name, String introducedDateS,
			String discontinuedDateS, String companyIdS) {

		Long id = Long.parseLong(idS);
		Computer c = ComputerService.getInstance().find(id);

		logger.debug("Getting parameters {} {} {} {}", name, introducedDateS,
				discontinuedDateS, companyIdS);

		Date introducedDate = null;
		Date discontinuedDate = null;
		Long companyId = null;
		Company cy = null;

		try {
			if (!introducedDateS.isEmpty())
				introducedDate = Date.valueOf(introducedDateS);
			if (!discontinuedDateS.isEmpty())
				discontinuedDate = Date.valueOf(discontinuedDateS);
		} catch (IllegalArgumentException e) {
			logger.error("Error on date format {}" + e.getMessage());
			return false;
		}

		if (!"null".contentEquals(companyIdS)) {
			companyId = Long.parseLong(companyIdS);
			logger.debug("Getting company !");
			// Getting corresponding company
			cy = CompanyService.getInstance().find(companyId);
			logger.debug("The company : {}", cy);
		}

		c.update(name, introducedDate, discontinuedDate, cy);
		logger.debug("Computer parameters : {} {} {} {}", c.getName(),
				c.getIntroduced(), c.getDiscontinued(), c.getCompany());

		if (!c.getName().isEmpty()) {
			ComputerDao.getInstance().update(c);
			return true;
		} else
			logger.error("Name is not set");
		return false;
	}

	public List<Computer> find() {
		return ComputerDao.getInstance().find();

	}
}
