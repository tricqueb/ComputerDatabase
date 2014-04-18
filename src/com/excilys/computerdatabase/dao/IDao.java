package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.models.Computer;

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
	public void create(T o);

	/**
	 * Delete an existing element to db
	 * 
	 * @param o
	 */
	public void delete(T o);

	/**
	 * (Find by name)
	 * 
	 * @param o
	 * 
	 * @return Object T found or an empty list
	 */
	public List<T> find(String o);

	/**
	 * Find by id
	 * 
	 * @param o
	 * @return
	 */
	public T find(long o);

	/**
	 * Replace an existing element by another
	 * 
	 * @param o
	 */
	public void update(T o);

	List<Computer> find();
}
