package com.excilys.computerdatabase.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.QCompany;
import com.mysema.query.jpa.hibernate.HibernateQuery;

@Repository
public class CompanyDaoImpl implements CompanyDao {

	private static final Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private CompanyDaoImpl() {
	}

	public List<Company> find(String companyName) {
		logger.info("looking for {}", companyName);

		HibernateQuery query = new HibernateQuery(
				this.sessionFactory.getCurrentSession());
		QCompany company = QCompany.company;

		return query.from(company)
				.where(company.name.like(companyName))
				.list(company);
	}

	public Company find(long companyId) {
		logger.info("looking for {}", companyId);

		HibernateQuery query = new HibernateQuery(
				this.sessionFactory.getCurrentSession());
		QCompany company = QCompany.company;

		return query.from(company)
				.where(company.id.eq(companyId))
				.uniqueResult(company);
	}
}
