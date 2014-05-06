package com.excilys.computerdatabase.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdatabase.connections.ConnectionBox;
import com.excilys.computerdatabase.connections.ConnectionManager;
import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.domain.Company;

public class CompanyDaoImpl implements CompanyDao {

	private static final Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);
	@Autowired
	private ConnectionManager cm;

	private CompanyDaoImpl() {
	}

	public List<Company> find(String companyName) throws SQLException {

		// Connection
		ConnectionBox cnb = cm.getConnectionBox();

		// Query
		cnb.setStatement("Select cy.id,cy.name from company as cy where cy.name LIKE ?;");

		// Query parameters
		cnb.getStatement().setString(1, "%" + companyName + "%");

		return doFind(cnb);
	}

	public Company find(long CompanyId) throws SQLException {

		// Connection
		ConnectionBox cnb = cm.getConnectionBox();

		// Query
		cnb.setStatement("Select cy.id,cy.name from company as cy where cy.id = ?;");

		// Query parameters
		cnb.getStatement().setLong(1, CompanyId);

		return doFind(cnb).get(0);
	}

	public List<Company> doFind(ConnectionBox cnb) throws SQLException {
		// Connection

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
