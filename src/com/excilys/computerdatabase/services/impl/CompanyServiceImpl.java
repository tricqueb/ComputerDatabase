package com.excilys.computerdatabase.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.connections.ConnectionManager;
import com.excilys.computerdatabase.dao.impl.CompanyDaoImpl;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.services.CompanyService;

public enum CompanyServiceImpl implements CompanyService {
	INSTANCE;
	private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

	private CompanyServiceImpl() {
	}

	public static CompanyServiceImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public List<Company> find(String cName) {
		List<Company> cList = null;
		logger.debug("Getting connection");
		ConnectionManager.getInstance().initTransaction();
		try {
			logger.debug("Read for {}", cName);
			cList = CompanyDaoImpl.getInstance().find(cName);
			ConnectionManager.getInstance().closeTransaction();

		} catch (SQLException e) {
			logger.error("find error : " + e.getMessage());

		} finally {
			ConnectionManager.getInstance().disconnect();
		}
		logger.debug("{} elements found ", cList);
		return cList;
	}

	@Override
	public Company find(Long cId) {
		logger.debug("Getting connection");
		Company company = null;
		ConnectionManager.getInstance().initTransaction();
		try {

			logger.debug("Read for {}", cId);
			company = CompanyDaoImpl.getInstance().find(cId);
			ConnectionManager.getInstance().closeTransaction();

		} catch (SQLException e) {
			logger.error("find error : " + e.getMessage());

		} finally {
			ConnectionManager.getInstance().disconnect();
		}
		logger.debug("company {} found ", company);
		return company;
	}
}
