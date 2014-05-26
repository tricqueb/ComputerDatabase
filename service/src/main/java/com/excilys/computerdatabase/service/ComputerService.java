package com.excilys.computerdatabase.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.excilys.computerdatabase.domain.Computer;

public interface ComputerService {

	// FIXME No check on company id !
	// Adding new computer to db
	public void create(Computer c);

	public void delete(Computer c);

	public Computer find(Long cId);

	public Long count(String search);

	public Page<Computer> find(String computerName, Pageable page);

	public void update(Computer c);

}