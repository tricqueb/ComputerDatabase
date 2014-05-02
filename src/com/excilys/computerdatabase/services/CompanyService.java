package com.excilys.computerdatabase.services;

import java.util.List;

import com.excilys.computerdatabase.domain.Company;

public interface CompanyService {

	public List<Company> find(String cName);

	public Company find(Long cId);

}
