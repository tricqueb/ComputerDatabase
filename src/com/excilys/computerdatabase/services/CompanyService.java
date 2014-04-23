package com.excilys.computerdatabase.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.models.Company;

public enum CompanyService {
	INSTANCE;
	private static final Logger logger = LoggerFactory
			.getLogger(CompanyService.class);

	private CompanyService() {
	}

	public static CompanyService getInstance() {
		return INSTANCE;
	}

	public List<Company> find(String cName) {
		return CompanyDao.getInstance().find(cName);

	}

	public Company find(Long cId) {
		return CompanyDao.getInstance().find(cId);
	}

}
