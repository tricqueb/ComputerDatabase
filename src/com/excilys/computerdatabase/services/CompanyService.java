package com.excilys.computerdatabase.services;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.ConnectionManager;
import com.excilys.computerdatabase.models.Company;

public enum CompanyService {
	INSTANCE;
	private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

	private CompanyService() {
	}

	public static CompanyService getInstance() {
		return INSTANCE;
	}

	public List<Company> find(String cName) {
		List<Company> cList = null;
		logger.debug("Getting connection");
		ConnectionBox cnb = ConnectionBox.Builder()
				.connection(ConnectionManager.getInstance().getConnection())
				.build();
		try {
			cnb.getConnection().setAutoCommit(false);
			logger.warn("Read for {}", cName);
			cList = CompanyDao.getInstance().find(cnb, cName);
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
		logger.debug("{} elements found ", cList);
		return cList;
	}

	public Company find(Long cId) {
		logger.debug("Getting connection");
		Company company = null;
		ConnectionBox cnb = ConnectionBox.Builder()
				.connection(ConnectionManager.getInstance().getConnection())
				.build();
		try {
			cnb.getConnection().setAutoCommit(false);
			logger.warn("Read for {}", cId);
			company = CompanyDao.getInstance().find(cnb, cId);
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
		return company;
	}

}
