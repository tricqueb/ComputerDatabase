package com.excilys.computerdatabase.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.services.ConnectionBox;

public enum CompanyDao implements ICompanyDao<Company> {
	INSTANCE;
	private static final Logger logger = LoggerFactory.getLogger(CompanyDao.class);

	private CompanyDao() {
	}

	public static CompanyDao getInstance() {
		return INSTANCE;
	}

	@Override
	public List<Company> find(ConnectionBox cnb, String companyName)
			throws SQLException {

		// Query
		cnb.setStatement("Select cy.id,cy.name from company as cy where cy.name LIKE ?;");

		// Query parameters
		cnb.getStatement().setString(1, "%" + companyName + "%");

		return doFind(cnb);
	}

	@Override
	public Company find(ConnectionBox cnb, long CompanyId) throws SQLException {

		// Query
		cnb.setStatement("Select cy.id,cy.name from company as cy where cy.id = ?;");

		// Query parameters
		cnb.getStatement().setLong(1, CompanyId);

		return doFind(cnb).get(0);
	}

	public List<Company> doFind(ConnectionBox cnb) throws SQLException {
		List<Company> cList = new ArrayList<Company>();

		// Execute
		cnb.setResultSet(cnb.getStatement().executeQuery());

		while (cnb.getResultSet().next()) {
			Company c = new Company();
			c.setId(cnb.getResultSet().getLong(1));
			c.setName(cnb.getResultSet().getString(2));
			cList.add(c);
		}
		return cList;
	}

}
