package com.excilys.computerdatabase.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.domain.Computer;

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
		this.sessionFactory.getCurrentSession().save(computer);
	}

	public void delete(Computer computer) {
		this.sessionFactory.getCurrentSession().delete(computer);
	}

	public List<Computer> find() {
		return this.sessionFactory.getCurrentSession()
				.createQuery(
						"from Computer as cr left outer join fetch cr.company ")
				.list();
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

		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Computer.class)
				.createAlias("company", "company", JoinType.LEFT_OUTER_JOIN)
				.add(Restrictions.or(
						Restrictions.like("name", "%" + search + "%"),
						Restrictions.like("company.name", "%" + search + "%")));

		if (orderBy == null)
			orderBy = "id";

		else if (orderBy.contentEquals("company")) {
			orderBy = "company.name";
		}

		if (desc)
			criteria.addOrder(Order.desc(orderBy));
		else
			criteria.addOrder(Order.asc(orderBy));

		if (limit != null)
			criteria.setMaxResults(limit);
		if (offset != null)
			criteria.setFirstResult(offset);

		return criteria.list();
	}

	public Long count(String search) {
		logger.debug("Counting element with {} as search parameter: " + search);
		return (Long) this.sessionFactory.getCurrentSession()
				.createQuery(
						"select count(*) from Computer as cr left outer join cr.company as cy where cr.name LIKE :search OR cy.name LIKE :search")
				.setString("search", "%" + search + "%")
				.uniqueResult();
	}

	@Override
	public Computer find(long computerId) {

		return (Computer) this.sessionFactory.getCurrentSession()
				.createCriteria(Computer.class)
				.add(Restrictions.idEq(computerId))
				.uniqueResult();
	}

	@Override
	public void update(Computer computer) {
		this.sessionFactory.getCurrentSession().merge(computer);
	}

}
