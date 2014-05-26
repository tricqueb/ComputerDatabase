package com.excilys.computerdatabase.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.dao.repository.CompanyRepository;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.service.CompanyService;

@Transactional(readOnly = true, rollbackFor = DataRetrievalFailureException.class)
@Service
public class CompanyServiceImpl implements CompanyService {

	private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

	@Autowired
	private CompanyRepository companyRepository;

	private CompanyServiceImpl() {
	}

	@Transactional
	@Override
	public List<Company> findAll() {
		logger.debug("Read for all companies");
		return companyRepository.findAll(new Sort(Direction.ASC, "name"));

	}

	@Transactional
	@Override
	public Company find(Long id) {
		Company company = null;
		logger.debug("Read for {}", id);
		company = companyRepository.findOne(id);
		logger.debug("company {} found ", company);
		return company;
	}
}
