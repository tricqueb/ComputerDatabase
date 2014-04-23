package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.servlets.ComputerCrud;

public class ConnectionManager {
	private static final Logger logger = LoggerFactory
			.getLogger(ComputerCrud.class);
	private static final String DATABASE = "computer-database-db";
	private static final String url = "jdbc:mysql://localhost:3306/" + DATABASE
			+ "?zeroDateTimeBehavior=convertToNull";

	private Connection cn = null;

	public ConnectionManager() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			logger.error("Error on driver instanciation {}", e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.error("Error on driver instanciation {}", e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			logger.error("Error on driver instanciation {}", e.getMessage());
			e.printStackTrace();
		}

	}

	public Connection getConnection() {
		logger.debug("Getting connection...");

		try {

			cn = DriverManager.getConnection(url, "root", "root");

		} catch (SQLException e) {
			logger.error("Error while trying to connect: {}", e.getMessage());
			e.printStackTrace();
		}

		return cn;
	}
}
