package com.jayeshtajane.simplejdbc.template;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.jayeshtajane.simplejdbc.JdbcTemplate;
import com.jayeshtajane.simplejdbc.template.query.Query;

/**
 * <p>This class stores the meta-data for connection.
 * This class is not thread-safe. It will take care of all the connection related task.
 * If you want to provide a database related operation then must need <code>Query</code> object. 
 * And this object is provide by the <code>createQuery()</code> method of this class.</p>
 * 
 * @author Jayesh Tajane
 * 
 * @version 1.0
 * 
 * @see Query
 */
public class SimpleJdbcTemplate implements JdbcTemplate {
	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = -2121839645248751969L;
	/**
	 * <p>store the database properties.</p>
	 */
	private Properties jdbcProperties;
	/**
	 * <p>store the current database connection.</p>
	 */
	private Connection connection = null;
	
	/**
	 * <p>Create object using driverName, url, username, password variable.</p>
	 * 
	 * @param driverName - Fully qualified Jdbc driver class name
	 * @param url - Database url
	 * @param username - database username
	 * @param password - database password
	 */
	public SimpleJdbcTemplate(String driverName, String url, String username, String password) {
		super();
		System.err.println("INFO: SimpleJdbcTemplate creation process starts");
		
		this.jdbcProperties = new Properties();
		
		if(driverName != null)
			this.jdbcProperties.put("jdbc.driver", driverName);
		if(url != null)
			this.jdbcProperties.put("jdbc.url", url);
		if(username != null)
			this.jdbcProperties.put("jdbc.username", username);
		if(password != null)
			this.jdbcProperties.put("jdbc.password", password);
		
		System.err.println("INFO: SimpleJdbcTemplate creation process ends");
	}
	
	/**
	 * <p>Create object using java.util.Properties class object.
	 * That object must contains connection related keys like jdbc.driver, 
	 * jdbc.url, jdbc.username, jdbc.password (optional).</p>
	 * 
	 * @param jdbcProperties - object of java.util.Properties having keys jdbc.driver, 
	 * jdbc.url, jdbc.username, jdbc.password (optional) keys.
	 */
	public SimpleJdbcTemplate(Properties jdbcProperties) {
		super();
		this.jdbcProperties = jdbcProperties;
	}
	
	/**
	 * <p>Creating the SimpleJdbcTemplate object using properties file.
	 * If you providing properties file then make sure in that properties file jdbc related keys
	 * you put. Those key are - jdbc.driver, jdbc.url, jdbc.username, jdbc.password
	 * If you are putting extra key in properties file then no problem its work fine but 
	 * above key must be present.</p>
	 * 
	 * @param fileUrl - name or path of you properties file while have the following keys jdbc.driver, 
	 * jdbc.url, jdbc.username, jdbc.password (optional).
	 */
	public SimpleJdbcTemplate(String fileUrl) {
		System.err.println("INFO: SimpleJdbcTemplate creation process starts");
		this.jdbcProperties = new Properties();
		try {
			InputStream stream = SimpleJdbcTemplate.class.getResourceAsStream("/" + fileUrl);
	        this.jdbcProperties.load(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println("INFO: SimpleJdbcTemplate creation process ends");
	}
	
	/**
	 * <p>Making the connection with database using given jdbc properties.</p>
	 * @return current Connection
	 */
	public Connection getConnection() {
		System.err.println("INFO: Connection creation process starts");
		try {
			if(connection == null || connection.isClosed()) {
				Class.forName(jdbcProperties.getProperty("jdbc.driver"));
				connection=DriverManager.getConnection(jdbcProperties.getProperty("jdbc.url"), 
						jdbcProperties.getProperty("jdbc.username"), 
						jdbcProperties.getProperty("jdbc.password"));
				System.err.println("INFO: Connection Successful");
			}
			System.err.println("INFO: Previous Connection return");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.err.println("INFO: Connection creation process ends");
		return connection;
	}
	
	/**
	 * <p>Creating Query object and setting programmer user query, connection details
	 *  to the object.</p>
	 * @param sqlQuery - sql query which you want to execute
	 * @return configured Query
	 */
	public Query createQuery(String sqlQuery) {
		System.err.println("INFO: Query creation (single arg) process starts");
		Query query = new Query(sqlQuery, getConnection());
		System.err.println("INFO: Query creation (single arg) process ends");
		return query;
	}
	
	/**
	 * <p>Creating Query object and setting programmer user query, setting query parameter, 
	 * connection details to the object.</p>
	 * @param sqlQuery - sql query which you want to execute
	 * @param params - indicates the list of values that you want to replace with 
	 * question marks of query. Order must follow.
	 * @return configured Query
	 */
	public Query createQuery(String sqlQuery, Object... params) {
		System.err.println("INFO: Query creation (two args) process starts");
		Query query = new Query(sqlQuery, getConnection(), params);
		System.err.println("INFO: Query creation process ends");
		return query;
	}
	
	/**
	 * Use for closing the connection of database.
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if(connection != null) {
			if(!connection.isClosed()) 
				connection.close();
		}
	}
}
