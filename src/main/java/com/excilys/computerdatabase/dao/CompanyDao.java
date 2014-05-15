package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.domain.Company;

public interface CompanyDao {

	/**
	 * (Find by name)
	 * 
	 * @param o
	 * 
	 * @return Object T found or an empty list
	 */
	public List<Company> find(String o);

	/**
	 * Find by id
	 * 
	 * @param o
	 * @return
	 */
	public Company find(long o);
}
