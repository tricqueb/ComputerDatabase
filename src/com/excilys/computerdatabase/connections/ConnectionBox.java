package com.excilys.computerdatabase.connections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ConnectionBox {

	public Connection getConnection();

	public void setConnection(Connection connection);

	public ResultSet getResultSet();

	public void setResultSet(ResultSet resultSet);

	public PreparedStatement getStatement();

	/**
	 * 
	 * @param statement
	 */
	public void setStatement(PreparedStatement statement);

	/**
	 * 
	 * @param query
	 * @throws SQLException
	 */
	public void setStatement(String query) throws SQLException;

	public boolean isInTransactionState();

	public void setTransactionState(Boolean b);
}