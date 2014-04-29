package com.excilys.computerdatabase.services;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.ConnectionManager;
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
	// Adding new computer to db
	public void create(Computer c) {

		logger.debug("Getting connection");
		ConnectionBox cnb = ConnectionBox.Builder()
				.connection(ConnectionManager.getInstance().getConnection())
				.build();
		try {
			cnb.getConnection().setAutoCommit(false);
			logger.warn("Ajout de {}", c.getName());
			ComputerDao.getInstance().create(cnb, c);
			cnb.getConnection().commit();
			cnb.getConnection().close();

		} catch (SQLException e) {
			logger.error("Create error : " + e.getMessage());
			try {
				cnb.getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Rollback error: " + e1.getMessage());
				e1.printStackTrace();
			}
		} finally {
			ConnectionManager.getInstance().disconnect(cnb);
		}

		logger.debug("Create ");

	}

	public void delete(Computer c) {
		logger.debug("Getting connection");
		ConnectionBox cnb = ConnectionBox.Builder()
				.connection(ConnectionManager.getInstance().getConnection())
				.build();
		try {
			cnb.getConnection().setAutoCommit(false);
			logger.warn("Deleting {} ", c.getId());
			ComputerDao.getInstance().delete(cnb, c.getId());
			cnb.getConnection().commit();
			cnb.getConnection().close();

		} catch (SQLException e) {
			logger.error("Delete error : " + e.getMessage());
			try {
				cnb.getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Rollback error: " + e1.getMessage());
				e1.printStackTrace();
			}
		} finally {
			ConnectionManager.getInstance().disconnect(cnb);
		}

		logger.debug("Element {} has been deleted", c.getName());
	}

	public Computer find(Long cId) {
		Computer computer = null;
		logger.debug("Getting connection");
		ConnectionBox cnb = ConnectionBox.Builder()
				.connection(ConnectionManager.getInstance().getConnection())
				.build();
		try {
			cnb.getConnection().setAutoCommit(false);
			logger.warn("Reading for {} ", cId);
			computer = ComputerDao.getInstance().find(cnb, cId);
		} catch (SQLException e) {
			logger.error("Find error : " + e.getMessage());
			try {
				cnb.getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Rollback error: " + e1.getMessage());
				e1.printStackTrace();
			}
		} finally {
			ConnectionManager.getInstance().disconnect(cnb);
		}
		logger.debug("Element {} have been found", computer);
		return computer;
	}

	public Integer count(String search) {
		Integer count = null;
		logger.debug("Getting connection");
		ConnectionBox cnb = ConnectionBox.Builder()
				.connection(ConnectionManager.getInstance().getConnection())
				.build();
		try {
			cnb.getConnection().setAutoCommit(false);
			logger.warn("Counting {} elements", search);
			count = ComputerDao.getInstance().count(cnb, search);
		} catch (SQLException e) {
			logger.error("Count error : " + e.getMessage());
			try {
				cnb.getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Rollback error: " + e1.getMessage());
				e1.printStackTrace();
			}
		} finally {
			ConnectionManager.getInstance().disconnect(cnb);
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
		ConnectionBox cnb = ConnectionBox.Builder()
				.connection(ConnectionManager.getInstance().getConnection())
				.build();
		try {
			cnb.getConnection().setAutoCommit(false);
			logger.warn("Reading {} elements", cName);
			cList = ComputerDao.getInstance().find(cnb, cName, offset, limit,
					orderBy, desc);
			cnb.getConnection().commit();
		} catch (SQLException e) {
			logger.error("find error : " + e.getMessage());
			try {
				cnb.getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Rollback error: " + e1.getMessage());
				e1.printStackTrace();
			}
		} finally {
			ConnectionManager.getInstance().disconnect(cnb);
		}
		logger.debug("{} Element have been found", cList.size());
		return cList;
	}

	public void update(Computer c) {

		logger.debug("Getting connection");
		ConnectionBox cnb = ConnectionBox.Builder()
				.connection(ConnectionManager.getInstance().getConnection())
				.build();
		try {
			cnb.getConnection().setAutoCommit(false);
			logger.warn("Updating computer {} ({}) with new values:  {} {} {}",
					c.getName(), c.getId(), c.getIntroduced(),
					c.getDiscontinued(), c.getCompany());
			ComputerDao.getInstance().update(cnb, c);
			cnb.getConnection().commit();
			logger.debug("Find ");

		} catch (SQLException e) {
			logger.error("find error : " + e.getMessage());
			try {
				cnb.getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Rollback error: " + e1.getMessage());
				e1.printStackTrace();
			}
		} finally {
			ConnectionManager.getInstance().disconnect(cnb);
		}
		logger.debug("Update done");
	}

	public List<Computer> find() {
		List<Computer> cList = null;
		logger.debug("Getting connection");
		ConnectionBox cnb = ConnectionBox.Builder()
				.connection(ConnectionManager.getInstance().getConnection())
				.build();
		try {
			cnb.getConnection().setAutoCommit(false);
			logger.warn("Read all");
			cList = ComputerDao.getInstance().find(cnb);
			cnb.getConnection().commit();

		} catch (SQLException e) {
			logger.error("find error : " + e.getMessage());
			try {
				cnb.getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Rollback error: " + e1.getMessage());
				e1.printStackTrace();
			}
		} finally {
			ConnectionManager.getInstance().disconnect(cnb);
		}
		logger.debug("{} Element have been found", cList.size());
		return cList;

	}
}
