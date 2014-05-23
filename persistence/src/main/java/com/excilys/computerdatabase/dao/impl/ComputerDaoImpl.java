package com.excilys.computerdatabase.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.QCompany;
import com.excilys.computerdatabase.domain.QComputer;
import com.mysema.query.jpa.hibernate.HibernateQuery;

/**
 * 
 * @author excilys
 * 
 */
@Repository
public class ComputerDaoImpl implements ComputerDao {

	private static final Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private ComputerDaoImpl() {
	}

	// TODO Test if null values are correctly added
	// TODO Test if conversion is needed
	public void create(Computer computer) {
		// where session is a Hibernate session
		this.sessionFactory.getCurrentSession().save(computer);
	}

	public void delete(Computer computer) {
		this.sessionFactory.getCurrentSession().delete(computer);
	}

	public List<Computer> find() {

		HibernateQuery query = new HibernateQuery(
				this.sessionFactory.getCurrentSession());
		QComputer computer = QComputer.computer;

		return query.from(computer).list(computer);
	}

	// TODO Add a filter function to separate "LIKE" part.
	// (WARNING : Care about data type)
	@Override
	public List<Computer> find(
			String search,
			Integer offset,
			Integer limit,
			String orderBy,
			Boolean desc) {

		HibernateQuery query = new HibernateQuery(
				this.sessionFactory.getCurrentSession());
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;

		// fully generic access

		query.from(computer)
				.leftJoin(computer.company, company)
				.where(computer.name.like("%" + search + "%").or(
						company.name.like("%" + search + "%")));

		switch (orderBy) {
		case "name": {
			if (desc)
				query.orderBy(computer.name.desc());
			else
				query.orderBy(computer.name.asc());
		}
		case "introduced": {
			if (desc)
				query.orderBy(computer.introduced.desc());
			else
				query.orderBy(computer.introduced.asc());
		}
		case "discontinued": {
			if (desc)
				query.orderBy(computer.discontinued.desc());
			else
				query.orderBy(computer.discontinued.asc());
		}
		case "company": {
			if (desc)
				query.orderBy(computer.company.name.desc());
			else
				query.orderBy(computer.company.name.asc());
		}
		default:
			query.orderBy(computer.id.asc());
		}

		if (limit != null)
			query.limit(limit);
		if (offset != null)
			query.offset(offset);

		return query.list(computer);
	}

	public Long count(String search) {
		logger.debug("Counting element with {} as search parameter: " + search);

		HibernateQuery query = new HibernateQuery(
				this.sessionFactory.getCurrentSession());
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;

		return query.from(computer)
				.leftJoin(computer.company, company)
				.where(computer.name.like("%" + search + "%").or(
						company.name.like("%" + search + "%")))
				.count();
	}

	@Override
	public Computer find(long computerId) {

		HibernateQuery query = new HibernateQuery(
				this.sessionFactory.getCurrentSession());
		QComputer computer = QComputer.computer;

		return query.from(computer)
				.where(computer.id.eq(computerId))
				.uniqueResult(computer);
	}

	@Override
	public void update(Computer computer) {
		this.sessionFactory.getCurrentSession().merge(computer);
	}

}
