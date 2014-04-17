package com.excilys.computerdatabaseDao;

import java.util.List;

public interface IDao<T> {
	/**
	 * 
	 * @param o
	 */
	public void create(T o);

	/**
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
	 * 
	 * @param o
	 */
	public void update(T o);

}
