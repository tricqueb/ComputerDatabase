package com.excilys.computerdatabase.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
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

			String computerName,
			Integer offset,
			Integer limit,
			String orderBy,
			Boolean desc) {

		String queryString = "from Computer as cr left outer join fetch cr.company as cy where cr.name LIKE :search ORDER BY ";

		if (orderBy == null)
			orderBy = "cr.id";

		else if (orderBy.contentEquals("company"))
			orderBy = "cy.name";
		else
			orderBy = "cr." + orderBy;

		queryString += orderBy;

		if (desc) {
			queryString += " DESC";
		} else
			queryString += " ASC";

		Query query = this.sessionFactory.getCurrentSession().createQuery(
				queryString);

		query.setString("search", "%" + computerName + "%");

		if (limit != null)
			query.setMaxResults(limit);
		if (offset != null)
			query.setFirstResult(offset);

		return query.list();
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
				.createQuery(
						"from Computer as cr left outer join cr.ompany as cy where id=:id")
				.setLong(0, computerId)
				.uniqueResult();
	}

	@Override
	public void update(Computer computer) {
		this.sessionFactory.getCurrentSession().merge(computer);
	}

}
