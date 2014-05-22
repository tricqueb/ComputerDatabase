package com.excilys.computerdatabase.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.domain.Company;

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

		return this.sessionFactory.getCurrentSession()
				.createQuery("from Company where name LIKE :name")
				.setString("name", "%" + companyName + "%")
				.list();
	}

	public Company find(long companyId) {
		logger.info("looking for {}", companyId);

		// Query
		return (Company) this.sessionFactory.getCurrentSession()
				.createQuery(
						"Select cy.id,cy.name from Company as cy where cy.id = :id")
				.setLong("id", companyId)
				.uniqueResult();
	}

}
