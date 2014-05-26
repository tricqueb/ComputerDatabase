package com.excilys.computerdatabase.service;

import java.util.List;

import com.excilys.computerdatabase.domain.Company;

public interface CompanyService {

	public List<Company> findAll();

	public Company find(Long cId);

}
