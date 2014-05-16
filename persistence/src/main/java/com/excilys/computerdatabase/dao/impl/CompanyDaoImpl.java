package com.excilys.computerdatabase.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.connection.ConnectionBox;
import com.excilys.computerdatabase.connection.ConnectionBoxImpl;
import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.domain.Company;

@Repository
public class CompanyDaoImpl implements CompanyDao {

	private static final Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);
	@Autowired
	@Qualifier("DataSource")
	private DataSource datasource;

	private CompanyDaoImpl() {
	}

	public List<Company> find(String companyName) {
		logger.info("looking for {}", companyName);
		// Connection
		ConnectionBox cnb = ConnectionBoxImpl.Builder()
				.connection(DataSourceUtils.getConnection(datasource))
				.build();

		try {
			// Query
			cnb.setStatement("Select cy.id,cy.name from company as cy where cy.name LIKE ?;");

			// Query parameters
			cnb.getStatement().setString(1, "%" + companyName + "%");
		} catch (SQLException e) {
			logger.error("Error on find of {}" + companyName);
			e.printStackTrace();
			throw new DataRetrievalFailureException(
					"Error on find of companyName");
		}

		return doFind(cnb);
	}

	public Company find(long companyId) {
		logger.info("looking for {}", companyId);
		// Connection
		ConnectionBox cnb = ConnectionBoxImpl.Builder()
				.connection(DataSourceUtils.getConnection(datasource))
				.build();

		try {
			// Query
			cnb.setStatement("Select cy.id,cy.name from company as cy where cy.id = ?;");
			// Query parameters
			cnb.getStatement().setLong(1, companyId);
		} catch (SQLException e) {
			logger.error("Error on find of {}" + companyId);
			e.printStackTrace();
			throw new DataRetrievalFailureException(
					"Error on find of companyId");
		}

		return doFind(cnb).get(0);
	}

	public List<Company> doFind(ConnectionBox cnb) {

		// Connection
		List<Company> cList = new ArrayList<Company>();

		// Execute
		try {
			cnb.setResultSet(cnb.getStatement().executeQuery());

			while (cnb.getResultSet().next()) {
				Company c = new Company();
				c.setId(cnb.getResultSet().getLong(1));
				c.setName(cnb.getResultSet().getString(2));
				cList.add(c);
			}
		} catch (SQLException e) {
			logger.error("SQL Error");
			e.printStackTrace();
			throw new DataRetrievalFailureException(
					"Error on find of companyId");
		}
		cnb.close();
		return cList;
	}

}
