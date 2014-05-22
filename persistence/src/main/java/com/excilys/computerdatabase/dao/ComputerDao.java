package com.excilys.computerdatabase.dao;

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
	public void create(Computer o);

	/**
	 * Delete an existing element in db
	 * 
	 * @param o
	 */
	public void delete(Computer o);

	/**
	 * (Find by name)
	 * 
	 * @param o
	 *            ob is the order column number applied
	 * 
	 * @return Object ComputerDao found or an empty list
	 */
	public List<Computer> find(

	String o, Integer offset, Integer limit, String orderBy, Boolean desc);

	/**
	 * Find by id
	 * 
	 * @param o
	 * @return
	 */
	public Computer find(long o);

	/**
	 * Replace an existing element by another
	 * 
	 * @param o
	 */
	public void update(Computer o);

	List<Computer> find();

	public Long count(String search);

}
