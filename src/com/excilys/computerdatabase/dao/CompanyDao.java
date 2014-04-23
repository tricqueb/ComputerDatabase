package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.models.Company;

public enum CompanyDao implements ICompanyDao<Company> {
	INSTANCE;
	private static final Logger logger = LoggerFactory
			.getLogger(CompanyDao.class);

	private Connection cn;
	private ResultSet rs;
	private Statement stmt = null;

	private CompanyDao() {

	}

	public static CompanyDao getInstance() {
		return INSTANCE;
	}

	@Override
	public List<Company> find(String companyName) {

		cn = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = cn
					.prepareStatement("Select cy.id,cy.name from company as cy where cy.name LIKE ?;");
			stmt.setString(1, "%" + companyName + "%");
		} catch (SQLException e) {
			logger.error("Find Statement Error : " + e.getMessage());
			e.printStackTrace();
		}
		return doFind(stmt);
	}

	@Override
	public Company find(long CompanyId) {
		cn = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {

			stmt = cn
					.prepareStatement("Select cy.id,cy.name from company as cy where cy.id = ?;");
			stmt.setLong(1, CompanyId);
		} catch (SQLException e) {
			logger.error("Find Statement Error : " + e.getMessage());
			e.printStackTrace();
		}
		return doFind(stmt).get(0);
	}

	public List<Company> doFind(PreparedStatement stmt) {
		List<Company> cList = new ArrayList<Company>();
		try {
			rs = stmt.executeQuery();
			while (rs.next()) {
				Company c = new Company();
				c.setId(rs.getLong(1));
				c.setName(rs.getString(2));
				cList.add(c);
			}

		} catch (SQLException e) {
			logger.error("doFind Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return cList;
	}

	private void disconnect() {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			logger.error("Closing Error : " + e.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				logger.error("Closing Error : " + e.getMessage());
			} finally {
				try {
					if (cn != null)
						cn.close();
				} catch (SQLException e) {
					logger.error("ComputerDao - Closing Error : "
							+ e.getMessage());
				}

			}
		}
	}

}
