package com.excilys.computerdatabase.services;

import java.util.List;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabaseDao.CompanyDao;

public class CompanyService {
	/** Constructeur privé */
	private CompanyService() {
	}

	/** Instance unique pré-initialisée */
	private static CompanyService INSTANCE = new CompanyService();

	/** Point d'accès pour l'instance unique du singleton */
	public static CompanyService getInstance() {
		return INSTANCE;
	}

	public void create(Company c) {
		CompanyDao.getInstance().create(c);

	}

	public void delete(Company c) {
		CompanyDao.getInstance().delete(c);

	}

	public List<Company> find(String cName) {
		return CompanyDao.getInstance().find(cName);

	}

	public Company find(Long cId) {
		return CompanyDao.getInstance().find(cId);
	}

	public void update(Company c) {
		CompanyDao.getInstance().update(c);

	}
}
