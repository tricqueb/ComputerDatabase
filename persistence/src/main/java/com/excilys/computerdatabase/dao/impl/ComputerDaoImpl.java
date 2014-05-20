package com.excilys.computerdatabase.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;

/**
 * 
 * @author excilys
 * 
 */
@Repository
public class ComputerDaoImpl implements ComputerDao {

	private static final Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert updateComputer;

	@Autowired
	@Qualifier("DataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.updateComputer = new SimpleJdbcInsert(dataSource).withTableName("computer");
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	private ComputerDaoImpl() {
	}

	// TODO Test if null values are correctly added
	// TODO Test if conversion is needed
	public void create(Computer computer) {

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("name", computer.getName());

		if (computer.getIntroduced() != null)
			parameters.put("introduced", new Date(computer.getIntroduced()
					.toDate()
					.getTime()));
		else
			parameters.put("introduced", null);

		if (computer.getDiscontinued() != null)
			parameters.put("discontinued", new Date(computer.getDiscontinued()
					.toDate()
					.getTime()));
		else
			parameters.put("discontinued", null);

		if (computer.getCompany() != null)
			parameters.put("company_id", computer.getCompany().getId());
		else
			parameters.put("company_id", null);

		updateComputer.execute(parameters);

	}

	public void delete(Computer computer) {
		this.jdbcTemplate.update("delete from computer where id = ?",
				computer.getId());

	}

	public void delete(Long id) {
		this.jdbcTemplate.update("delete from computer where id = ?", id);
	}

	public List<Computer> find() {

		return this.jdbcTemplate.query(
				"Select cr.id,cr.name,cr.introduced,cr.discontinued,cy.id,cy.name from computer as cr left outer join company as cy ON cy.id=cr.company_id;",
				new ComputerMapper());
	}

	private static final class ComputerMapper implements RowMapper<Computer> {

		public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Computer computer = new Computer();
			Company company = new Company();
			computer = Computer.Builder()
					.id(rs.getLong(1))
					.name(rs.getString(2))
					.introduced(rs.getDate(3))
					.discontinued(rs.getDate(4))
					.build();
			if (rs.getLong(5) != 0) {
				company = Company.Builder()
						.id(rs.getLong(5))
						.name(rs.getString(6))
						.build();

			}
			computer.setCompany(company);
			return computer;
		}
	}

	// TODO Add a filter function to separate "LIKE" part.
	// (WARNING : Care about data type)
	@Override
	public List<Computer> find(

			String computerName,
			Integer offset,
			Integer limit,
			Long orderBy,
			Boolean desc) {

		StringBuilder query;
		query = new StringBuilder(
				"Select cr.id,cr.name,cr.introduced,cr.discontinued,cy.id,cy.name from computer as cr left outer join company as cy ON cy.id=cr.company_id where cr.name LIKE :search OR cy.name LIKE :search ORDER BY :orderBy ");

		if (orderBy == null)
			orderBy = 1l;

		if (desc) {
			query.append("DESC ");
		}

		query.append("LIMIT :limit OFFSET :offset");

		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(
				"search", "%" + computerName + "%");
		sqlParameterSource.addValue("orderBy", orderBy);

		if (limit == null)
			sqlParameterSource.addValue("limit", "ALL");
		else
			sqlParameterSource.addValue("limit", limit);
		if (offset == null)
			sqlParameterSource.addValue("offset", 0);
		else
			sqlParameterSource.addValue("offset", offset);

		return this.namedParameterJdbcTemplate.query(query.toString(),
				sqlParameterSource, new ComputerMapper());
	}

	public int count(String search) {
		logger.debug("Counting element with {} as search parameter: " + search);

		// Connection
		return this.jdbcTemplate.queryForObject(
				"select count(*) from computer as cr left outer join company as cy ON cy.id=cr.company_id where cr.name LIKE ? OR cy.name LIKE ?;",
				Integer.class, "%" + search + "%", "%" + search + "%");

	}

	@Override
	public Computer find(long computerId) {

		return this.jdbcTemplate.queryForObject(
				"Select cr.id,cr.name,cr.introduced,cr.discontinued,cy.id,cy.name from computer as cr left outer join company as cy ON cy.id=cr.company_id where cr.id = ?;",
				new ComputerMapper(), computerId);
	}

	@Override
	public void update(Computer computer) {
		String query;
		query = "update computer set name=:name, introduced = :introduced, discontinued = :discontinued,company_id=:company_id where id=:id ";

		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(
				"name", computer.getName());

		sqlParameterSource.addValue("id", computer.getId());

		if (computer.getIntroduced() != null)
			sqlParameterSource.addValue("introduced", new Date(
					computer.getIntroduced().toDate().getTime()));
		else
			sqlParameterSource.addValue("introduced", null);

		if (computer.getDiscontinued() != null)
			sqlParameterSource.addValue("discontinued", new Date(
					computer.getDiscontinued().toDate().getTime()));
		else
			sqlParameterSource.addValue("discontinued", null);
		if (computer.getCompany() != null)
			sqlParameterSource.addValue("company_id", computer.getCompany()
					.getId());
		else
			sqlParameterSource.addValue("company_id", null);

		this.namedParameterJdbcTemplate.update(query, sqlParameterSource);

	}
}
