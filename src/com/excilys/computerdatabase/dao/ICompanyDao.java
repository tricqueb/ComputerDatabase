package com.excilys.computerdatabase.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.services.ConnectionBox;

public interface ICompanyDao<T> {

	/**
	 * (Find by name)
	 * 
	 * @param o
	 * 
	 * @return Object T found or an empty list
	 */
	public List<T> find(ConnectionBox cnb, String o) throws SQLException;

	/**
	 * Find by id
	 * 
	 * @param o
	 * @return
	 */
	public T find(ConnectionBox cnb, long o) throws SQLException;
}
