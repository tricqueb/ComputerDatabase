package com.excilys.computerdatabase.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionBoxImpl implements ConnectionBox {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdatabase.service.impl.ConnectionBox#getConnection()
	 */
	public Connection getConnection() {
		return connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdatabase.service.impl.ConnectionBox#setConnection
	 * (java.sql.Connection)
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdatabase.service.impl.ConnectionBox#getResultSet()
	 */
	public ResultSet getResultSet() {
		return resultSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ConnectionBox#setResultSet
	 * (java.sql.ResultSet)
	 */
	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.excilys.computerdatabase.service.impl.ConnectionBox#getStatement()
	 */
	public PreparedStatement getStatement() {
		return statement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ConnectionBox#setStatement
	 * (java.sql.PreparedStatement)
	 */
	public void setStatement(PreparedStatement statement) {
		this.statement = statement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.computerdatabase.service.impl.ConnectionBox#setStatement
	 * (java.lang.String)
	 */
	public void setStatement(String query) throws SQLException {
		this.statement = this.connection.prepareStatement(query);
	}

	public boolean isInTransactionState() {
		return transactionState;
	}

	public void setTransactionState(Boolean transactionState) {
		this.transactionState = transactionState;
	}

	private Connection connection;
	private ResultSet resultSet;
	private PreparedStatement statement = null;
	private boolean transactionState = false;

	public static class Builder {
		ConnectionBoxImpl connectionBox;

		private Builder() {
			connectionBox = new ConnectionBoxImpl();
		}

		public Builder connection(Connection c) {
			connectionBox.connection = c;
			return this;
		}

		public Builder resultSet(ResultSet rs) {
			connectionBox.resultSet = rs;
			return this;
		}

		public Builder statement(PreparedStatement stmt) {
			connectionBox.statement = stmt;
			return this;
		}

		public ConnectionBoxImpl build() {
			return connectionBox;
		}
	}

	public static Builder Builder() {
		return new Builder();
	}

	public void close() {
		log.debug("Closing connections");
		try {
			if (statement != null)
				statement.close();
		} catch (SQLException e) {
			log.error("Closing Error : " + e.getMessage());
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
			} catch (SQLException e) {
				log.error("Closing Error : " + e.getMessage());
			}
		}

	}
}
