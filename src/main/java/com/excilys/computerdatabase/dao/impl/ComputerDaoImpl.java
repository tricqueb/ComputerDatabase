package com.excilys.computerdatabase.dao.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.connection.ConnectionBox;
import com.excilys.computerdatabase.connection.ConnectionBoxImpl;
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
	@Autowired
	@Qualifier("DataSource")
	private DataSource datasource;

	private ComputerDaoImpl() {
	}

	public void create(Computer computer) {

		ConnectionBox cnb = ConnectionBoxImpl.Builder()
				.connection(DataSourceUtils.getConnection(datasource))
				.build();

		try {
			cnb.setStatement("INSERT into computer(name,introduced,discontinued,company_id) VALUES(?,?,?,?);");

			cnb.getStatement().setString(1, computer.getName());

			if (computer.getIntroduced() != null)
				cnb.getStatement().setDate(2,
						new Date(computer.getIntroduced().toDate().getTime()));
			else
				cnb.getStatement().setNull(2, Types.NULL);

			if (computer.getDiscontinued() != null)
				cnb.getStatement()
						.setDate(
								3,
								new Date(computer.getDiscontinued()
										.toDate()
										.getTime()));
			else
				cnb.getStatement().setNull(3, Types.NULL);

			if (computer.getCompany() != null)
				cnb.getStatement().setLong(4, computer.getCompany().getId());
			else
				cnb.getStatement().setNull(4, Types.NULL);

			cnb.getStatement().executeUpdate();
			cnb.close();
		} catch (SQLException e) {
			logger.error("Error on create of {}" + computer);
			e.printStackTrace();
			throw new DataAccessResourceFailureException(
					"Error on create of computer");
		}
	}

	public void delete(Computer computer) {
		ConnectionBox cnb = ConnectionBoxImpl.Builder()
				.connection(DataSourceUtils.getConnection(datasource))
				.build();
		try {
			cnb.setStatement("DELETE from computer where id=?;");

			cnb.getStatement().setString(1, Long.toString(computer.getId()));
			cnb.getStatement().executeUpdate();
		} catch (SQLException e) {
			logger.error("Error on delete of {}" + computer);
			e.printStackTrace();
			throw new DataAccessResourceFailureException(
					"Error on delete of computer");
		}
	}

	public void delete(Long id) {
		ConnectionBox cnb = ConnectionBoxImpl.Builder()
				.connection(DataSourceUtils.getConnection(datasource))
				.build();

		try {
			cnb.setStatement("DELETE from computer where id=?;");

			cnb.getStatement().setString(1, Long.toString(id));
			cnb.getStatement().executeUpdate();
		} catch (SQLException e) {
			logger.error("Error on delete of {}" + id);
			e.printStackTrace();
			throw new DataAccessResourceFailureException(
					"Error on delete of id");
		}
	}

	public List<Computer> find() {
		ConnectionBox cnb = ConnectionBoxImpl.Builder()
				.connection(DataSourceUtils.getConnection(datasource))
				.build();

		logger.debug("Find all");
		try {
			cnb.setStatement("Select cr.id,cr.name,cr.introduced,cr.discontinued,cy.id,cy.name from computer as cr left outer join company as cy ON cy.id=cr.company_id;");
		} catch (SQLException e) {
			logger.error("Error on find all");
			e.printStackTrace();
			throw new DataAccessResourceFailureException("Error on find all");
		}

		return doFind(cnb);
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
				"Select cr.id,cr.name,cr.introduced,cr.discontinued,cy.id,cy.name from computer as cr left outer join company as cy ON cy.id=cr.company_id where cr.name LIKE ? OR cy.name LIKE ? ORDER BY ? ");

		if (orderBy == null)
			orderBy = 1l;

		if (desc)
			query.append("DESC ");

		query.append("LIMIT ? OFFSET ?");

		ConnectionBox cnb = ConnectionBoxImpl.Builder()
				.connection(DataSourceUtils.getConnection(datasource))
				.build();
		try {
			cnb.setStatement(query.toString());

			cnb.getStatement().setString(1, "%" + computerName + "%");
			cnb.getStatement().setString(2, "%" + computerName + "%");
			cnb.getStatement().setLong(3, orderBy);
			if (limit == null)
				cnb.getStatement().setString(4, "ALL");
			else
				cnb.getStatement().setLong(4, limit);
			if (offset == null)
				cnb.getStatement().setLong(5, 0);
			else
				cnb.getStatement().setLong(5, offset);
		} catch (SQLException e) {
			logger.error("Error on find of {}" + computerName);
			e.printStackTrace();
			throw new DataAccessResourceFailureException(
					"Error on find of computerName");
		}
		return doFind(cnb);
	}

	public int count(String search) {
		// Connection
		ConnectionBox cnb = ConnectionBoxImpl.Builder()
				.connection(DataSourceUtils.getConnection(datasource))
				.build();

		Integer result = 0;
		logger.debug("Counting element with {} as search parameter: " + search);
		try {
			cnb.setStatement("select count(*) from computer as cr left outer join company as cy ON cy.id=cr.company_id where cr.name LIKE ? OR cy.name LIKE ?;");

			cnb.getStatement().setString(1, "%" + search + "%");
			cnb.getStatement().setString(2, "%" + search + "%");
			cnb.setResultSet(cnb.getStatement().executeQuery());
			while (cnb.getResultSet().next())
				result = cnb.getResultSet().getInt(1);

			cnb.close();
		} catch (SQLException e) {
			logger.error("Error on count of {}" + search);
			e.printStackTrace();
			throw new DataAccessResourceFailureException(
					"Error on count of search");
		}
		return result;
	}

	@Override
	public Computer find(long computerId) {
		ConnectionBox cnb = ConnectionBoxImpl.Builder()
				.connection(DataSourceUtils.getConnection(datasource))
				.build();
		try {
			cnb.setStatement("Select cr.id,cr.name,cr.introduced,cr.discontinued,cy.id,cy.name from computer as cr left outer join company as cy ON cy.id=cr.company_id where cr.id = ?;");

			cnb.getStatement().setLong(1, computerId);
		} catch (SQLException e) {
			logger.error("Error on find of {}" + computerId);
			e.printStackTrace();
			throw new DataAccessResourceFailureException(
					"Error on find of computerId");
		}
		return doFind(cnb).get(0);
	}

	private List<Computer> doFind(ConnectionBox cnb) {
		logger.debug("do Find");
		List<Computer> cList = new ArrayList<Computer>();
		try {
			cnb.setResultSet(cnb.getStatement().executeQuery());
			logger.debug("Filling Computer list");
			while (cnb.getResultSet().next()) {
				Computer c;
				Company cy = null;
				c = Computer.Builder()
						.id(cnb.getResultSet().getLong(1))
						.name(cnb.getResultSet().getString(2))
						.introduced(cnb.getResultSet().getDate(3))
						.discontinued(cnb.getResultSet().getDate(4))
						.build();
				if (cnb.getResultSet().getLong(5) != 0) {
					cy = Company.Builder()
							.id(cnb.getResultSet().getLong(5))
							.name(cnb.getResultSet().getString(6))
							.build();
				}
				c.setCompany(cy);
				cList.add(c);
			}
			logger.debug("Done: {}", cList.size());

			cnb.close();
		} catch (SQLException e) {
			logger.error("Error on find query");
			e.printStackTrace();
			throw new DataAccessResourceFailureException("Error on find query");
		}
		return cList;
	}

	@Override
	public void update(Computer computer) {
		ConnectionBox cnb = ConnectionBoxImpl.Builder()
				.connection(DataSourceUtils.getConnection(datasource))
				.build();
		try {
			cnb.setStatement("UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? where id=?;");

			cnb.getStatement().setString(1, computer.getName());

			if (computer.getIntroduced() != null)
				cnb.getStatement().setDate(2,
						new Date(computer.getIntroduced().toDate().getTime()));
			else {
				logger.debug("Setting null value");
				cnb.getStatement().setNull(2, Types.NULL);
			}

			if (computer.getDiscontinued() != null)
				cnb.getStatement()
						.setDate(
								3,
								new Date(computer.getDiscontinued()
										.toDate()
										.getTime()));
			else {
				logger.debug("Setting null value");
				cnb.getStatement().setNull(3, Types.NULL);
			}

			if (computer.getCompany() != null)
				cnb.getStatement().setLong(4, computer.getCompany().getId());
			else
				cnb.getStatement().setNull(4, Types.NULL);

			cnb.getStatement().setLong(5, computer.getId());

			cnb.getStatement().executeUpdate();
		} catch (SQLException e) {
			logger.error("Error on update of {}" + computer);
			e.printStackTrace();
			throw new DataAccessResourceFailureException(
					"Error on update of computer");
		}
		cnb.close();
	}
}
