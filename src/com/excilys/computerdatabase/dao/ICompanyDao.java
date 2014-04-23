package com.excilys.computerdatabase.dao;

import java.util.List;

public interface ICompanyDao<T> {

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
}
