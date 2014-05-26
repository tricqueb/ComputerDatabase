package com.excilys.computerdatabase.dao.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.excilys.computerdatabase.domain.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {

	List<Company> findAll(Sort sort);

}
