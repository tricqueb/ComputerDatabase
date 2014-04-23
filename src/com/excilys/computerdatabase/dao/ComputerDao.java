package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

/**
 * 
 * @author excilys
 * 
 */
public enum ComputerDao implements IDao<Computer> {
	INSTANCE;
	private static final Logger logger = LoggerFactory
			.getLogger(ComputerDao.class);

	private Connection cn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private ComputerDao() {
	}

	public static ComputerDao getInstance() {
		return INSTANCE;
	}

	public void create(Computer computer) {
		try {
			cn = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = cn
					.prepareStatement("INSERT into computer(name,introduced,discontinued,company_id) VALUES(?,?,?,?);");
			stmt.setString(1, computer.getName());

			if (computer.getIntroduced() != null)
				stmt.setDate(2, new Date(computer.getIntroduced().getTime()));
			else
				stmt.setNull(2, Types.NULL);

			if (computer.getDiscontinued() != null)
				stmt.setDate(3, new Date(computer.getDiscontinued().getTime()));
			else
				stmt.setNull(3, Types.NULL);

			if (computer.getCompany() != null)
				stmt.setLong(4, computer.getCompany().getId());
			else
				stmt.setNull(4, Types.NULL);

			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(" Creation Error " + e.getMessage());
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	@Override
	public void delete(Computer computer) {
		try {
			cn = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = cn
					.prepareStatement("DELETE from computer where id=?;");
			stmt.setString(1, Long.toString(computer.getId()));
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Deletion statement Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	@Override
	public void delete(Long id) {
		try {
			cn = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = cn
					.prepareStatement("DELETE from computer where id=?;");
			stmt.setString(1, Long.toString(id));
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Deletion statement Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	@Override
	public List<Computer> find() {
		logger.debug("Find all");
		cn = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = cn
					.prepareStatement("Select cr.id,cr.name,cr.introduced,cr.discontinued,cy.id,cy.name from computer as cr left outer join company as cy ON cy.id=cr.company_id;");
		} catch (SQLException e) {
			logger.error("Statement Error : " + e.getMessage());
			e.printStackTrace();
		}
		return doFind(stmt);
	}

	// TODO add search criteria parameter to do more than looking for name
	// (WARNING : Care about data type)
	@Override
	public List<Computer> find(String computerName) {

		cn = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = cn
					.prepareStatement("Select cr.id,cr.name,cr.introduced,cr.discontinued,cy.id,cy.name from computer as cr left outer join company as cy ON cy.id=cr.company_id where cr.name LIKE ?;");
			stmt.setString(1, "%" + computerName + "%");
		} catch (SQLException e) {
			logger.error("Statement Error : " + e.getMessage());
			e.printStackTrace();
		}
		return doFind(stmt);
	}

	@Override
	public Computer find(long computerId) {
		cn = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = cn
					.prepareStatement("Select cr.id,cr.name,cr.introduced,cr.discontinued,cy.id,cy.name from computer as cr left outer join company as cy ON cy.id=cr.company_id where cr.id = ?;");
			stmt.setLong(1, computerId);
		} catch (SQLException e) {
			logger.error("Statement Error : " + e.getMessage());
			e.printStackTrace();
		}
		return doFind(stmt).get(0);
	}

	private List<Computer> doFind(PreparedStatement stmt) {
		logger.debug("do Find");
		List<Computer> cList = new ArrayList<Computer>();
		try {
			rs = stmt.executeQuery();
			logger.debug("Filling Computer list");
			while (rs.next()) {
				Computer c;
				Company cy = null;
				c = Computer.Builder().id(rs.getLong(1)).name(rs.getString(2))
						.introduced(rs.getDate(3)).discontinued(rs.getDate(4))
						.build();
				if (rs.getLong(5) != 0) {
					cy = Company.Builder().id(rs.getLong(5))
							.name(rs.getString(6)).build();
				}
				c.setCompany(cy);
				cList.add(c);
			}
			logger.debug("Done: {}", cList.size());

		} catch (SQLException e) {
			logger.error("find query Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return cList;
	}

	@Override
	public void update(Computer computer) {
		try {
			cn = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt = cn
					.prepareStatement("UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? where id=?;");
			stmt.setString(1, computer.getName());

			if (computer.getIntroduced() != null)
				stmt.setDate(2, new Date(computer.getIntroduced().getTime()));
			else
				stmt.setNull(2, Types.NULL);

			if (computer.getDiscontinued() != null)
				stmt.setDate(3, new Date(computer.getDiscontinued().getTime()));
			else {
				logger.debug("Setting null value");
				stmt.setNull(3, Types.NULL);
			}

			if (computer.getCompany() != null)
				stmt.setLong(4, computer.getCompany().getId());
			else
				stmt.setNull(4, Types.NULL);

			stmt.setLong(5, computer.getId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(" Update Error " + e.getMessage());
			e.printStackTrace();
		} finally {
			disconnect();
		}
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
