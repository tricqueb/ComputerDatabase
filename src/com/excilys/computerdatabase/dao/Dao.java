package com.excilys.computerdatabase.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Describe a classic DAO
 * 
 * @author excilys
 * 
 * @param <T>
 */
public interface Dao<T> {
	/**
	 * Add a new element to db
	 * 
	 * @param o
	 */
	public void create(T o) throws SQLException;

	/**
	 * Delete an existing element in db
	 * 
	 * @param o
	 */
	public void delete(T o) throws SQLException;

	/**
	 * Delete an existing element in db
	 * 
	 * @param id
	 *            of computer
	 */
	void delete(Long id) throws SQLException;

	/**
	 * (Find by name)
	 * 
	 * @param o
	 *            ob is the order column number applied
	 * 
	 * @return Object T found or an empty list
	 */
	public List<T> find(

	String o, Integer offset, Integer limit, Long ob, Boolean desc)
			throws SQLException;

	/**
	 * Find by id
	 * 
	 * @param o
	 * @return
	 */
	public T find(long o) throws SQLException;

	/**
	 * Replace an existing element by another
	 * 
	 * @param o
	 */
	public void update(T o) throws SQLException;

	List<T> find() throws SQLException;

}
