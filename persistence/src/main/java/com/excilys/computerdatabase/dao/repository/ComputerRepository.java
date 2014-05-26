package com.excilys.computerdatabase.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.excilys.computerdatabase.domain.Computer;

public interface ComputerRepository extends CrudRepository<Computer, Long> {

	Page<Computer> findAll(Pageable pageable);

	Page<Computer> findByNameContainingOrCompany_NameContaining(
			String name,
			String companyName,
			Pageable pageable);

	Long countByNameContainingOrCompany_NameContaining(
			String computerName,
			String companyName);

}
