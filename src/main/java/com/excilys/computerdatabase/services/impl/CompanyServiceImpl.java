package com.excilys.computerdatabase.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdatabase.connections.ConnectionManager;
import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.services.CompanyService;

public class CompanyServiceImpl implements CompanyService {

	private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
	@Autowired
	private ConnectionManager cm;
	@Autowired
	private CompanyDao companyDao;

	private CompanyServiceImpl() {
	}

	@Override
	public List<Company> find(String cName) {
		List<Company> cList = null;
		logger.debug("Getting connection");
		cm.initTransaction();
		try {
			logger.debug("Read for {}", cName);
			cList = companyDao.find(cName);
			cm.closeTransaction();

		} catch (SQLException e) {
			logger.error("find error : " + e.getMessage());

		} finally {
			cm.disconnect();
		}
		logger.debug("{} elements found ", cList);
		return cList;
	}

	@Override
	public Company find(Long cId) {
		logger.debug("Getting connection");
		Company company = null;
		cm.initTransaction();
		try {

			logger.debug("Read for {}", cId);
			company = companyDao.find(cId);
			cm.closeTransaction();

		} catch (SQLException e) {
			logger.error("find error : " + e.getMessage());

		} finally {
			cm.disconnect();
		}
		logger.debug("company {} found ", company);
		return company;
	}
}
