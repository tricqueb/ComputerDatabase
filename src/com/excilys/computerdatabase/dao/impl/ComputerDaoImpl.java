package com.excilys.computerdatabase.dao.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.connections.ConnectionBox;
import com.excilys.computerdatabase.connections.ConnectionBoxImpl;
import com.excilys.computerdatabase.connections.ConnectionManager;
import com.excilys.computerdatabase.dao.Dao;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;

/**
 * 
 * @author excilys
 * 
 */
public enum ComputerDaoImpl implements Dao<Computer> {
	INSTANCE;
	private static final Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);
	private ThreadLocal<ConnectionBoxImpl> threadLocal;

	private ComputerDaoImpl() {
		threadLocal = new ThreadLocal<ConnectionBoxImpl>();
	}

	public static ComputerDaoImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public void create(Computer computer) throws SQLException {

		ConnectionBox cnb = ConnectionManager.INSTANCE.getConnectionBox();
		cnb.setStatement("INSERT into computer(name,introduced,discontinued,company_id) VALUES(?,?,?,?);");
		cnb.getStatement().setString(1, computer.getName());

		if (computer.getIntroduced() != null)
			cnb.getStatement().setDate(2,
					new Date(computer.getIntroduced().getTime()));
		else
			cnb.getStatement().setNull(2, Types.NULL);

		if (computer.getDiscontinued() != null)
			cnb.getStatement().setDate(3,
					new Date(computer.getDiscontinued().getTime()));
		else
			cnb.getStatement().setNull(3, Types.NULL);

		if (computer.getCompany() != null)
			cnb.getStatement().setLong(4, computer.getCompany().getId());
		else
			cnb.getStatement().setNull(4, Types.NULL);

		cnb.getStatement().executeUpdate();
	}

	@Override
	public void delete(Computer computer) throws SQLException {
		ConnectionBox cnb = ConnectionManager.INSTANCE.getConnectionBox();
		cnb.setStatement("DELETE from computer where id=?;");
		cnb.getStatement().setString(1, Long.toString(computer.getId()));
		cnb.getStatement().executeUpdate();
	}

	@Override
	public void delete(Long id) throws SQLException {
		ConnectionBox cnb = ConnectionManager.INSTANCE.getConnectionBox();
		cnb.setStatement("DELETE from computer where id=?;");
		cnb.getStatement().setString(1, Long.toString(id));
		cnb.getStatement().executeUpdate();

	}

	@Override
	public List<Computer> find() throws SQLException {
		ConnectionBox cnb = ConnectionManager.INSTANCE.getConnectionBox();

		logger.debug("Find all");
		cnb.setStatement("Select cr.id,cr.name,cr.introduced,cr.discontinued,cy.id,cy.name from computer as cr left outer join company as cy ON cy.id=cr.company_id;");

		return doFind(cnb);
	}

	// TODO add search criteria parameter to do more than looking for name
	// TODO Add a filter function to separate "LIKE" part.
	// (WARNING : Care about data type)
	@Override
	public List<Computer> find(

			String computerName,
			Integer offset,
			Integer limit,
			Long orderBy,
			Boolean desc) throws SQLException {

		StringBuilder query;
		query = new StringBuilder(
				"Select cr.id,cr.name,cr.introduced,cr.discontinued,cy.id,cy.name from computer as cr left outer join company as cy ON cy.id=cr.company_id where cr.name LIKE ? OR cy.name LIKE ? ORDER BY ? ");

		if (orderBy == null)
			orderBy = 1l;

		if (desc)
			query.append("DESC ");

		query.append("LIMIT ? OFFSET ?");

		ConnectionBox cnb = ConnectionManager.INSTANCE.getConnectionBox();
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

		return doFind(cnb);
	}

	public int count(String search) throws SQLException {
		// Connection
		ConnectionBox cnb = ConnectionManager.INSTANCE.getConnectionBox();

		Integer result = 0;
		logger.debug("Counting element with {} as search parameter: " + search);
		cnb.setStatement("select count(*) from computer as cr left outer join company as cy ON cy.id=cr.company_id where cr.name LIKE ? OR cy.name LIKE ?;");
		cnb.getStatement().setString(1, "%" + search + "%");
		cnb.getStatement().setString(2, "%" + search + "%");
		cnb.setResultSet(cnb.getStatement().executeQuery());
		while (cnb.getResultSet().next())
			result = cnb.getResultSet().getInt(1);

		return result;
	}

	@Override
	public Computer find(long computerId) throws SQLException {
		ConnectionBox cnb = ConnectionManager.INSTANCE.getConnectionBox();
		cnb.setStatement("Select cr.id,cr.name,cr.introduced,cr.discontinued,cy.id,cy.name from computer as cr left outer join company as cy ON cy.id=cr.company_id where cr.id = ?;");
		cnb.getStatement().setLong(1, computerId);

		return doFind(cnb).get(0);
	}

	private List<Computer> doFind(ConnectionBox cnb) throws SQLException {
		logger.debug("do Find");
		List<Computer> cList = new ArrayList<Computer>();
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

		return cList;
	}

	@Override
	public void update(Computer computer) throws SQLException {
		ConnectionBox cnb = ConnectionManager.INSTANCE.getConnectionBox();
		cnb.setStatement("UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? where id=?;");
		cnb.getStatement().setString(1, computer.getName());

		if (computer.getIntroduced() != null)
			cnb.getStatement().setDate(2,
					new Date(computer.getIntroduced().getTime()));
		else {
			logger.debug("Setting null value");
			cnb.getStatement().setNull(2, Types.NULL);
		}

		if (computer.getDiscontinued() != null)
			cnb.getStatement().setDate(3,
					new Date(computer.getDiscontinued().getTime()));
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

	}
}
