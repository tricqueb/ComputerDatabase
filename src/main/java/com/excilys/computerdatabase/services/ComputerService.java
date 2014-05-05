package com.excilys.computerdatabase.services;

import java.util.List;

import com.excilys.computerdatabase.domain.Computer;

public interface ComputerService {

	// FIXME No check on company id !
	// Adding new computer to db
	public void create(Computer c);

	public void delete(Computer c);

	public Computer find(Long cId);

	public Integer count(String search);

	public List<Computer> find(
			String cName,
			Integer offset,
			Integer limit,
			Long orderBy,
			Boolean desc);

	public void update(Computer c);

	public List<Computer> find();

}