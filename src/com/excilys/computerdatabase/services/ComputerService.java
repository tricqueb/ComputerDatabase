package com.excilys.computerdatabase.services;

import java.util.List;
import java.util.Map;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;
import com.excilys.computerdatabaseDao.ComputerDao;

public class ComputerService {

	/** Constructeur privé */
	private ComputerService() {
	}

	/** Instance unique pré-initialisée */
	private static ComputerService INSTANCE = new ComputerService();

	/** Point d'accès pour l'instance unique du singleton */
	public static ComputerService getInstance() {
		return INSTANCE;
	}

	public void create(Computer c) {
		ComputerDao.getInstance().create(c);

	}

	public void delete(Computer c) {
		ComputerDao.getInstance().delete(c);

	}

	public Computer find(Long cId) {
		return ComputerDao.getInstance().find(cId);

	}

	public List<Computer> find(String cName) {
		return ComputerDao.getInstance().find(cName);

	}

	/**
	 * 
	 * @param c
	 * @return Map<ComputerId,Company>
	 * @Warning DO NOT USE
	 */
	public Map<Long, Company> find(List<Computer> c) {
		// return ComputerDao.getInstance().getCompanies(c);
		return null;

	}

	public void update(Computer c) {
		ComputerDao.getInstance().update(c);

	}

}
