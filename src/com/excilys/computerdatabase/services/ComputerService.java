package com.excilys.computerdatabase.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.models.Computer;

public enum ComputerService {
	INSTANCE;
	private static final Logger logger = LoggerFactory.getLogger(ComputerService.class);

	private ComputerService() {
	}

	public static ComputerService getInstance() {
		return INSTANCE;
	}

	// FIXME No check on company id !
	public void create(Computer c) {
		// Adding new computer to db
		logger.debug("Ajout de {}", c.getName());
		ComputerDao.getInstance().create(c);
	}

	public void delete(Computer c) {
		ComputerDao.getInstance().delete(c.getId());
		logger.debug("Element {} has been deleted", c.getName());
	}

	public Computer find(Long cId) {
		return ComputerDao.getInstance().find(cId);

	}

	public Integer count(String search) {
		return ComputerDao.getInstance().count(search);
	}

	public List<Computer> find(
			String cName,
			Integer offset,
			Integer limit,
			Long orderBy,
			Boolean desc) {
		return ComputerDao.getInstance().find(cName, offset, limit, orderBy,
				desc);

	}

	public void update(Computer c) {
		logger.debug("Updating computer {} ({}) with new values:  {} {} {}",
				c.getName(), c.getId(), c.getIntroduced(), c.getDiscontinued(),
				c.getCompany());

		ComputerDao.getInstance().update(c);
	}

	public List<Computer> find() {
		return ComputerDao.getInstance().find();

	}
}
