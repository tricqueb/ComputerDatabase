package com.excilys.computerdatabase.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.connections.ConnectionManager;
import com.excilys.computerdatabase.dao.impl.ComputerDaoImpl;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.services.ComputerService;

public enum ComputerServiceImpl implements ComputerService {
	INSTANCE;
	private static final Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);

	private ComputerServiceImpl() {
	}

	public static ComputerService getInstance() {
		return INSTANCE;
	}

	// FIXME No check on company id !
	// Adding new computer to db
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdatabase.services.impl.ComputerService#create(com
	 * .excilys.computerdatabase.domain.Computer)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdatabase.services.impl.ComputerService#delete(com
	 * .excilys.computerdatabase.domain.Computer)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdatabase.services.impl.ComputerService#find(java.
	 * lang.Long)
	 */
	@Override
	public Computer find(Long cId) {
		Computer computer = null;
		logger.debug("Getting connection");
		ConnectionManager.getInstance().initTransaction();
		try {

			logger.debug("Reading for {} ", cId);
			computer = ComputerDaoImpl.getInstance().find(cId);
		} catch (SQLException e) {
			logger.error("Find error : " + e.getMessage());

		} finally {
			ConnectionManager.getInstance().disconnect();
		}
		logger.debug("Element {} have been found", computer);
		return computer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdatabase.services.impl.ComputerService#count(java
	 * .lang.String)
	 */
	@Override
	public Integer count(String search) {
		Integer count = null;
		logger.debug("Getting connection");
		ConnectionManager.getInstance().initTransaction();
		try {

			logger.debug("Counting {} elements", search);
			count = ComputerDaoImpl.getInstance().count(search);
		} catch (SQLException e) {
			logger.error("Count error : " + e.getMessage());

		} finally {
			ConnectionManager.getInstance().disconnect();
		}
		logger.debug("{} Element have been found", count);
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdatabase.services.impl.ComputerService#find(java.
	 * lang.String, java.lang.Integer, java.lang.Integer, java.lang.Long,
	 * java.lang.Boolean)
	 */
	@Override
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

			logger.debug("Reading {} elements", cName);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdatabase.services.impl.ComputerService#update(com
	 * .excilys.computerdatabase.domain.Computer)
	 */
	@Override
	public void update(Computer c) {

		logger.debug("Getting connection");
		ConnectionManager.getInstance().initTransaction();
		try {

			ComputerDaoImpl.getInstance().update(c);
			logger.warn("Updating computer {} ({}) with new values:  {} {} {}",
					c.getName(), c.getId(), c.getIntroduced(),
					c.getDiscontinued(), c.getCompany());
			ConnectionManager.getInstance().closeTransaction();
			logger.debug("Find ");

		} catch (SQLException e) {
			logger.error("find error : " + e.getMessage());

		} finally {
			ConnectionManager.getInstance().disconnect();
		}
		logger.debug("Update done");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.services.impl.ComputerService#find()
	 */
	@Override
	public List<Computer> find() {
		List<Computer> cList = null;
		logger.debug("Getting connection");
		ConnectionManager.getInstance().initTransaction();
		try {

			logger.debug("Read all");
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
