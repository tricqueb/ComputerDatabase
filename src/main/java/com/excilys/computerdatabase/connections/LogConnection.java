package com.excilys.computerdatabase.connections;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.db.ConnectionSource;
import ch.qos.logback.core.db.DBHelper;
import ch.qos.logback.core.db.dialect.DBUtil;
import ch.qos.logback.core.db.dialect.SQLDialectCode;
import ch.qos.logback.core.spi.ContextAwareBase;

@Component
public class LogConnection extends ContextAwareBase implements
		ConnectionSource, ApplicationContextAware {

	private String driverClass = null;
	private String url = null;

	private boolean started;

	private String user = null;
	private String password = null;

	// initially we have an unknown dialect
	private SQLDialectCode dialectCode = SQLDialectCode.UNKNOWN_DIALECT;
	private boolean supportsGetGeneratedKeys = false;
	private boolean supportsBatchUpdates = false;

	private ApplicationContext applicationContext;
	@Autowired
	private ConnectionManager cm;

	public void start() {
		try {
			if (driverClass != null) {
				cm = (ConnectionManager) applicationContext.getBean("connectionManager");
				Class.forName(driverClass);
				discoverConnectionProperties();
				// SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
				// cm = (ConnectionManager)
				// applicationContext.getBean("applicationContext");
			} else {
				addError("WARNING: No JDBC driver specified for logback DriverManagerConnectionSource.");
			}
		} catch (final ClassNotFoundException cnfe) {
			addError("Could not load JDBC driver class: " + driverClass, cnfe);
		}
	}

	/**
	 * Learn relevant information about this connection source.
	 * 
	 */
	public void discoverConnectionProperties() {
		// super.discoverConnectionProperties();
		Connection connection = null;
		try {
			connection = getConnection();
			if (connection == null) {
				addWarn("Could not get a connection");
				return;
			}
			DatabaseMetaData meta = connection.getMetaData();

			DBUtil util = new DBUtil();
			util.setContext(getContext());

			supportsGetGeneratedKeys = util.supportsGetGeneratedKeys(meta);
			supportsBatchUpdates = util.supportsBatchUpdates(meta);
			dialectCode = DBUtil.discoverSQLDialect(meta);
			addInfo("Driver name=" + meta.getDriverName());
			addInfo("Driver version=" + meta.getDriverVersion());
			addInfo("supportsGetGeneratedKeys=" + supportsGetGeneratedKeys);
			addInfo("dialectCode=" + dialectCode);

		} catch (SQLException se) {
			addWarn("Could not discover the dialect to use.", se);
		} finally {
			// if (cm.getConnection() == null) {
			DBHelper.closeConnection(connection);
			// }
		}
	}

	/**
	 * @see ch.qos.logback.core.db.ConnectionSource#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		return cm.getConnection();
	}

	/**
	 * Returns the url.
	 * 
	 * @return String
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 * 
	 * @param url
	 *            The url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Returns the name of the driver class.
	 * 
	 * @return String
	 */
	public String getDriverClass() {
		return driverClass;
	}

	/**
	 * Sets the driver class.
	 * 
	 * @param driverClass
	 *            The driver class to set
	 */
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	/**
	 * Does this connection support the JDBC Connection.getGeneratedKeys method?
	 */
	public final boolean supportsGetGeneratedKeys() {
		return supportsGetGeneratedKeys;
	}

	public final SQLDialectCode getSQLDialectCode() {
		return dialectCode;
	}

	/**
	 * Get the password for this connection source.
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            The password to set
	 */
	public final void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Get the user for this connection source.
	 */
	public final String getUser() {
		return user;
	}

	/**
	 * Sets the username.
	 * 
	 * @param username
	 *            The username to set
	 */
	public final void setUser(final String username) {
		this.user = username;
	}

	/**
	 * Does this connection support batch updates?
	 */
	public final boolean supportsBatchUpdates() {
		return supportsBatchUpdates;
	}

	public boolean isStarted() {
		return started;
	}

	public void stop() {
		started = false;
	}

	/**
	 * Get Spring application context FIXME: Aha Spring is initializing his
	 * container after appender initialization...
	 */
	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext) throws BeansException {
		// System.out.println("setting context");
		this.applicationContext = applicationContext;
	}
}
