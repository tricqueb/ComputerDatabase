package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	static final String DATABASE = "computer-database-db";
	Connection cn = null;

	public ConnectionManager() {

	}

	public Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/" + DATABASE
				+ "?zeroDateTimeBehavior=convertToNull";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			try {

				cn = DriverManager.getConnection(url, "root", "root");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;

		return cn;
	}

}
