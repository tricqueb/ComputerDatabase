package com.excilys.computerdatabase.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.services.ConnectionBox;

/**
 * Describe a classic DAO
 * 
 * @author excilys
 * 
 * @param <T>
 */
public interface IDao<T> {
	/**
	 * Add a new element to db
	 * 
	 * @param o
	 */
	public void create(ConnectionBox cnb, T o) throws SQLException;

	/**
	 * Delete an existing element in db
	 * 
	 * @param o
	 */
	public void delete(ConnectionBox cnb, T o) throws SQLException;

	/**
	 * Delete an existing element in db
	 * 
	 * @param id
	 *            of computer
	 */
	void delete(ConnectionBox cnb, Long id) throws SQLException;

	/**
	 * (Find by name)
	 * 
	 * @param o
	 *            ob is the order column number applied
	 * 
	 * @return Object T found or an empty list
	 */
	public List<T> find(
			ConnectionBox cnb,
			String o,
			Integer offset,
			Integer limit,
			Long ob,
			Boolean desc) throws SQLException;

	/**
	 * Find by id
	 * 
	 * @param o
	 * @return
	 */
	public T find(ConnectionBox cnb, long o) throws SQLException;

	/**
	 * Replace an existing element by another
	 * 
	 * @param o
	 */
	public void update(ConnectionBox cnb, T o) throws SQLException;

	List<T> find(ConnectionBox cnb) throws SQLException;

}
