package com.excilys.computerdatabase.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionBox {

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public PreparedStatement getStatement() {
		return statement;
	}

	/**
	 * 
	 * @param statement
	 */
	public void setStatement(PreparedStatement statement) {
		this.statement = statement;
	}

	/**
	 * 
	 * @param query
	 * @throws SQLException
	 */
	public void setStatement(String query) throws SQLException {
		this.statement = this.connection.prepareStatement(query);
	}

	private Connection connection;
	private ResultSet resultSet;
	private PreparedStatement statement = null;

	public static class Builder {
		ConnectionBox connectionBox;

		private Builder() {
			connectionBox = new ConnectionBox();
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

		public ConnectionBox build() {
			return connectionBox;
		}
	}

	public static Builder Builder() {
		return new Builder();
	}

}
