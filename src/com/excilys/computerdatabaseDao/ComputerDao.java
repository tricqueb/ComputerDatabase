package com.excilys.computerdatabaseDao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.models.Company;
import com.excilys.computerdatabase.models.Computer;

/**
 * 
 * @author excilys
 * 
 */
public enum ComputerDao implements IDao<Computer> {
	INSTANCE;
	private ComputerDao() {
	}

	public static ComputerDao getInstance() {
		return INSTANCE;
	}

	Connection cn;
	ConnectionManager cnManager;
	Statement stmt = null;
	ResultSet rs = null;

	// FIXME Validation (null...)
	public void create(Computer computer) {
		try {
			cn = cnManager.getConnection();
			PreparedStatement stmt = cn
					.prepareStatement("INSERT into computer(id,name,introduced,discontinued,company_id) VALUES(?,?,?,?,?);");
			stmt.setLong(1, computer.getId());
			stmt.setString(2, computer.getName());
			stmt.setDate(3, new Date(computer.getIntroduced().getTime()));
			stmt.setDate(4, new Date(computer.getDiscontinued().getTime()));
			stmt.setLong(5, computer.getCompany().getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out
					.println("ComputerDao - Creation Error " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println("ComputerDao - Closing Error : "
						+ e.getMessage());
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.out.println("ComputerDao - Closing Error : "
						+ e.getMessage());
			}
			try {
				if (cn != null)
					cn.close();
			} catch (SQLException e) {
				System.out.println("ComputerDao - Closing Error : "
						+ e.getMessage());
			}

		}

	}

	@Override
	public void delete(Computer computer) {
		try {
			cn = cnManager.getConnection();
			PreparedStatement stmt = cn
					.prepareStatement("DELETE c from computer where c.id=?;");
			stmt.setString(1, Long.toString(computer.getId()));
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("ComputerDao - Deletion Error: "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (stmt != null)
					stmt.close();

				if (cn != null)
					cn.close();
			} catch (SQLException e) {
				System.out.println("ComputerDao - Closing Error : " + e);
			}
		}

	}

	@Override
	public List<Computer> find(String computerName) {

		cn = cnManager.getConnection();
		PreparedStatement stmt = null;
		try {

			stmt = cn
					.prepareStatement("Select cr.id,cr.name,cr.introduced,cr.discontinued,cy.id,cy.name from computer as cr left outer join company as cy ON cy.id=cr.company_id where cr.name LIKE ?;");
			stmt.setString(1, "%" + computerName + "%");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doFind(stmt);
	}

	@Override
	public Computer find(long computerId) {
		cn = cnManager.getConnection();
		PreparedStatement stmt = null;
		try {

			stmt = cn
					.prepareStatement("Select cr.id,cr.name,cr.introduced,cr.discontinued,cy.id,cy.name from computer as cr left outer join company as cy ON cy.id=cr.company_id where cr.id = ?;");
			stmt.setLong(1, computerId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doFind(stmt).get(0);
	}

	public List<Computer> doFind(PreparedStatement stmt) {
		List<Computer> cList = new ArrayList<Computer>();
		try {

			rs = stmt.executeQuery();
			while (rs.next()) {
				Computer c = new Computer();
				Company cy = new Company();
				c.setId(rs.getLong(1));
				c.setName(rs.getString(2));
				c.setIntroduced((rs.getDate(3)));
				c.setDiscontinued(rs.getDate(4));
				cy.setId(rs.getLong(5));
				cy.setName(rs.getString(6));
				c.setCompany(cy);
				cList.add(c);
			}

		} catch (SQLException e) {
			System.out.println("ComputerDao - find Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (stmt != null)
					stmt.close();

				if (cn != null)
					cn.close();
			} catch (SQLException e) {
				System.out.println("ComputerDao - Closing Error : " + e);
			}
		}
		return cList;
	}

	@Override
	public void update(Computer computer) {
		// TODO Auto-generated method stub

	}

	/**
	 * Get related companies of cList
	 * 
	 * @param cList
	 * @return a map with Computer's id as key and corresponding company TODO
	 *         Company as key ?
	 */
	// public Map<Long, Company> getCompanies(List<Computer> cList) {
	//
	// Map<Long, Company> cMap = new HashMap<Long, Company>();
	// try {
	// cn = cnManager.getConnection();
	// PreparedStatement stmt = cn
	// .prepareStatement("Select cy.id,cy.name,cr.id, cr.name,cr.introduced,cr.discontinued from company as cy left outer join computer as cr ON cy.id=cr.id IN (?);");
	// StringBuilder stateString = new StringBuilder(Long.toString(cList
	// .get(0).getId()));
	//
	// int i = 1;
	// while (i < cList.size()) {
	// stateString.append(",");
	// stateString.append(Long.toString(cList.get(i).getId()));
	// i++;
	// }
	//
	// stmt.setString(1, stateString.toString());
	// rs = stmt.executeQuery();
	// while (rs.next()) {
	// Company c = new Company();
	// cy.setId(rs.getLong(1));
	// cy.setName(rs.getString(2));
	// cr.setId(rs.getLong(3));
	//
	// cMap.put(cr, cy);
	// }
	// } catch (SQLException e) {
	// System.out.println("ComputerDao - getCompanies Error: "
	// + e.getMessage());
	// e.printStackTrace();
	// } finally {
	// try {
	// if (rs != null)
	// rs.close();
	//
	// if (stmt != null)
	// stmt.close();
	//
	// if (cn != null)
	// cn.close();
	// } catch (SQLException e) {
	// System.out.println("ComputerDao - Closing Error : " + e);
	// }
	// }
	// return null;
	// }

	//
	// @Override
	// public void update(Computer computer) {
	// cn = cnManager.getConnection();
	// List<Computer> cList = new ArrayList<Computer>();
	// try {
	//
	// PreparedStatement stmt = cn
	// .prepareStatement("Select id,name,introduce,discontinued from computer where c.id=?;");
	// stmt.setLong(1, computer.getId());
	// rs = stmt.executeQuery();
	// while (rs.next()) {
	// Computer c = new Computer();
	// c.setId(new Long(rs.getLong(1)));
	// c.setName(rs.getString(2));
	// c.setIntroduce(Date.valueOf(rs.getString(3)));
	// c.setDiscontinued(Date.valueOf(rs.getString(4)));
	//
	// cList.add(c);
	// }
	//
	//
	//
	// } catch (SQLException e) {
	// System.out.println("ComputerDao - find Error: " + e.getMessage());
	// e.printStackTrace();
	// } finally {
	// try {
	// if (rs != null)
	// rs.close();
	//
	// if (stmt != null)
	// stmt.close();
	//
	// if (cn != null)
	// cn.close();
	// } catch (SQLException e) {
	// System.out.println("ComputerDao - Closing Error : " + e);
	// }
	// }
	// return cList;
	//
	// }

}
