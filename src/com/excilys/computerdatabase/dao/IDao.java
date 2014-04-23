package com.excilys.computerdatabase.dao;

import java.util.List;

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
	 * Delete an existing element in db
	 * 
	 * @param o
	 */
	public void delete(T o);

	/**
	 * Delete an existing element in db
	 * 
	 * @param id
	 *            of computer
	 */
	void delete(Long id);

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

	List<T> find();

}
