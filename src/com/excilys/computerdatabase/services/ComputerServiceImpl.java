package com.excilys.computerdatabase.services;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.connections.ConnectionManager;
import com.excilys.computerdatabase.dao.impl.ComputerDaoImpl;
import com.excilys.computerdatabase.domain.Computer;

public enum ComputerServiceImpl {
	INSTANCE;
	private static final Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);

	private ComputerServiceImpl() {
	}

	public static ComputerServiceImpl getInstance() {
		return INSTANCE;
	}

	// FIXME No check on company id !
	// Adding new computer to db
	public void create(Computer c) {

		logger.debug("Getting connection");
		ConnectionManager.getInstance().initTransaction();
		try {

			logger.warn("Ajout de {}", c.getName());
			ComputerDaoImpl.getInstance().create(c);
			ConnectionManager.getInstance().closeTransaction();

		} catch (SQLException e) {
			logger.error("Create error : " + e.getMessage());

		} finally {
			ConnectionManager.getInstance().disconnect();
		}

		logger.debug("Create ");

	}

	public void delete(Computer c) {
		logger.debug("Getting connection");
		ConnectionManager.getInstance().initTransaction();
		try {

			logger.warn("Deleting {} ", c.getId());
			ComputerDaoImpl.getInstance().delete(c.getId());
			ConnectionManager.getInstance().closeTransaction();

		} catch (SQLException e) {
			logger.error("Delete error : " + e.getMessage());

		} finally {
			ConnectionManager.getInstance().disconnect();
		}

		logger.debug("Element {} has been deleted", c.getName());
	}

	public Computer find(Long cId) {
		Computer computer = null;
		logger.debug("Getting connection");
		ConnectionManager.getInstance().initTransaction();
		try {

			logger.warn("Reading for {} ", cId);
			computer = ComputerDaoImpl.getInstance().find(cId);
		} catch (SQLException e) {
			logger.error("Find error : " + e.getMessage());

		} finally {
			ConnectionManager.getInstance().disconnect();
		}
		logger.debug("Element {} have been found", computer);
		return computer;
	}

	public Integer count(String search) {
		Integer count = null;
		logger.debug("Getting connection");
		ConnectionManager.getInstance().initTransaction();
		try {

			logger.warn("Counting {} elements", search);
			count = ComputerDaoImpl.getInstance().count(search);
		} catch (SQLException e) {
			logger.error("Count error : " + e.getMessage());

		} finally {
			ConnectionManager.getInstance().disconnect();
		}
		logger.debug("{} Element have been found", count);
		return count;
	}

	public List<Computer> find(
			String cName,
			Integer offset,
			Integer limit,
			Long orderBy,
			Boolean desc) {
		List<Computer> cList = null;
		logger.debug("Getting connection");
		ConnectionManager.getInstance().initTransaction();
		try {

			logger.warn("Reading {} elements", cName);
			cList = ComputerDaoImpl.getInstance().find(cName, offset, limit,
					orderBy, desc);
			ConnectionManager.getInstance().closeTransaction();
		} catch (SQLException e) {
			logger.error("find error : " + e.getMessage());

		} finally {
			ConnectionManager.getInstance().disconnect();
		}
		logger.debug("{} Element have been found", cList.size());
		return cList;
	}

	public void update(Computer c) {

		logger.debug("Getting connection");
		ConnectionManager.getInstance().initTransaction();
		try {

			logger.warn("Updating computer {} ({}) with new values:  {} {} {}",
					c.getName(), c.getId(), c.getIntroduced(),
					c.getDiscontinued(), c.getCompany());
			ComputerDaoImpl.getInstance().update(c);
			ConnectionManager.getInstance().closeTransaction();
			logger.debug("Find ");

		} catch (SQLException e) {
			logger.error("find error : " + e.getMessage());

		} finally {
			ConnectionManager.getInstance().disconnect();
		}
		logger.debug("Update done");
	}

	public List<Computer> find() {
		List<Computer> cList = null;
		logger.debug("Getting connection");
		ConnectionManager.getInstance().initTransaction();
		try {

			logger.warn("Read all");
			cList = ComputerDaoImpl.getInstance().find();
			ConnectionManager.getInstance().closeTransaction();

		} catch (SQLException e) {
			logger.error("find error : " + e.getMessage());

		} finally {
			ConnectionManager.getInstance().disconnect();
		}
		logger.debug("{} Element have been found", cList.size());
		return cList;

	}
}
