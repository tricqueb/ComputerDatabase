package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.services.ConnectionBox;
import com.excilys.computerdatabase.servlets.ComputerCrud;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public enum ConnectionManager {
	INSTANCE;

	private static final Logger logger = LoggerFactory.getLogger(ComputerCrud.class);
	private static final String DATABASE = "computer-database-db";
	private static final String url = "jdbc:mysql://localhost:3306/" + DATABASE
			+ "?zeroDateTimeBehavior=convertToNull";
	// Bloc executed after constructor
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			BoneCPConfig config = new BoneCPConfig(); // create a new
														// configuration object
			config.setJdbcUrl(url); // set the JDBC url
			config.setUsername("root"); // set the username
			config.setPassword("root"); // set the passwords
			connectionPool = new BoneCP(config); // setup the connection pool

		} catch (ClassNotFoundException | SQLException e) {
			logger.error("Error on driver instanciation {}", e.getMessage());
			e.printStackTrace();
		}
	}

	private static BoneCP connectionPool;

	private ConnectionManager() {
	}

	public static ConnectionManager getInstance() {
		return INSTANCE;
	}

	public Connection getConnection() {
		logger.debug("Getting connection...");
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();

		} catch (SQLException e) {
			logger.error("Error while trying to connect: {}", e.getMessage());
			e.printStackTrace();
		}

		return connection; // fetch a connection
	}

	public void disconnect(ConnectionBox cnb) {
		try {
			if (cnb.getStatement() != null)
				cnb.getStatement().close();
		} catch (SQLException e) {
			logger.error("Closing Error : " + e.getMessage());
		} finally {
			try {
				if (cnb.getStatement() != null)
					cnb.getStatement().close();
			} catch (SQLException e) {
				logger.error("Closing Error : " + e.getMessage());
			} finally {
				try {
					if (cnb.getConnection() != null)
						cnb.getConnection().close();
				} catch (SQLException e) {
					logger.error("ComputerDao - Closing Error : "
							+ e.getMessage());
				}

			}
		}
	}
}
