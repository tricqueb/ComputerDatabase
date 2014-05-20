package com.excilys.computerdatabase.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.domain.Company;

@Repository
public class CompanyDaoImpl implements CompanyDao {

	private static final Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("DataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private CompanyDaoImpl() {
	}

	public List<Company> find(String companyName) {
		logger.info("looking for {}", companyName);

		return this.jdbcTemplate.query(
				"Select cy.id,cy.name from company as cy where cy.name LIKE ?;",
				new CompanyMapper(), "%" + companyName + "%");
	}

	public Company find(long companyId) {
		logger.info("looking for {}", companyId);

		// Query
		return this.jdbcTemplate.queryForObject(
				"Select cy.id,cy.name from company as cy where cy.id = ?;",
				new CompanyMapper(), companyId);

	}

	private static final class CompanyMapper implements RowMapper<Company> {

		public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
			Company company = new Company();
			company = Company.Builder()
					.id(rs.getLong(1))
					.name(rs.getString(2))
					.build();
			return company;
		}
	}

}
