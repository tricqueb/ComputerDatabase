package com.excilys.computerdatabase.dao;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDao<T> {

	/**
	 * (Find by name)
	 * 
	 * @param o
	 * 
	 * @return Object T found or an empty list
	 */
	public List<T> find(String o) throws SQLException;

	/**
	 * Find by id
	 * 
	 * @param o
	 * @return
	 */
	public T find(long o) throws SQLException;
}
