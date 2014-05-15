package com.excilys.computerdatabase.service.impl;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.service.CompanyService;

@Transactional(readOnly = true, rollbackFor = DataRetrievalFailureException.class)
@Service
public class CompanyServiceImpl implements CompanyService {

	private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
	@Autowired
	@Qualifier("DataSource")
	private DataSource datasource;
	@Autowired
	private CompanyDao companyDao;

	private CompanyServiceImpl() {
	}

	@Transactional
	@Override
	public List<Company> find(String cName) {
		List<Company> cList = null;
		logger.debug("Read for {}", cName);
		cList = companyDao.find(cName);

		logger.debug("{} elements found ", cList);
		return cList;
	}

	@Transactional
	@Override
	public Company find(Long cId) {
		Company company = null;
		logger.debug("Read for {}", cId);
		company = companyDao.find(cId);
		logger.debug("company {} found ", company);
		return company;
	}
}
