package com.excilys.computerdatabase.connections;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.excilys.computerdatabase.servlets.ComputerCrud;

public class ConnectionManager {

	private final Logger logger = LoggerFactory.getLogger(ComputerCrud.class);

	@Autowired
	@Qualifier("DataSource")
	private DataSource connectionPool;

	private ThreadLocal<ConnectionBoxImpl> threadLocal;

	private ConnectionManager() {

		threadLocal = new ThreadLocal<ConnectionBoxImpl>();

	}

	public Connection getConnection() {
		try {
			return connectionPool.getConnection();
		} catch (SQLException e) {
			logger.error("Error while trying to connect: {}", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public void initTransaction() {

		logger.debug("Getting connection...");
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
			ConnectionBoxImpl cnb = ConnectionBoxImpl.Builder()
					.connection(connection)
					.build();
			cnb.getConnection().setAutoCommit(false);
			cnb.setTransactionState(true);
			threadLocal.set(cnb);
		} catch (SQLException e) {
			logger.error("Error while trying to connect: {}", e.getMessage());
			e.printStackTrace();
		}
	}

	public void closeTransaction() {
		ConnectionBox cnb = threadLocal.get();
		try {
			cnb.getConnection().commit();
		} catch (SQLException e1) {
			logger.error("Commit error: " + e1.getMessage());
			e1.printStackTrace();
			try {
				cnb.getConnection().rollback();
			} catch (SQLException e) {
				logger.error("Rollback error: " + e.getMessage());
				e.printStackTrace();
			}
		}
		cnb.setTransactionState(false);
	}

	public ConnectionBox getConnectionBox() {
		return threadLocal.get(); // fetch a connection
	}

	public void disconnect() {

		ConnectionBox cnb = threadLocal.get();
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
				} finally {
					threadLocal.remove();
				}
			}
		}
	}

	public ThreadLocal<ConnectionBoxImpl> getThreadLocal() {
		return threadLocal;
	}

	public void setThreadLocal(ThreadLocal<ConnectionBoxImpl> threadLocal) {
		this.threadLocal = threadLocal;
	}
}
