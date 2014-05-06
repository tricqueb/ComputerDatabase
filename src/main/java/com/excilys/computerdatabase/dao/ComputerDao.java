package com.excilys.computerdatabase.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Computer;

/**
 * Describe a classic DAO
 * 
 * @author excilys
 * 
 * @param <ComputerDao>
 */
public interface ComputerDao {
	/**
	 * Add a new element to db
	 * 
	 * @param o
	 */
	public void create(Computer o) throws SQLException;

	/**
	 * Delete an existing element in db
	 * 
	 * @param o
	 */
	public void delete(Computer o) throws SQLException;

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
	 * @return Object ComputerDao found or an empty list
	 */
	public List<Computer> find(

	String o, Integer offset, Integer limit, Long ob, Boolean desc)
			throws SQLException;

	/**
	 * Find by id
	 * 
	 * @param o
	 * @return
	 */
	public Computer find(long o) throws SQLException;

	/**
	 * Replace an existing element by another
	 * 
	 * @param o
	 */
	public void update(Computer o) throws SQLException;

	List<Computer> find() throws SQLException;

	public int count(String search) throws SQLException;

}
