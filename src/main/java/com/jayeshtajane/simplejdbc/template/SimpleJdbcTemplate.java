package com.jayeshtajane.simplejdbc.template;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.jayeshtajane.simplejdbc.template.query.Query;

/**
 * This class is doing connection related task and configuring the Query object. 
 * @author jayes
 *
 */
public class SimpleJdbcTemplate {
	private Properties jdbcProperties;
	private static Connection connection = null;
	
	static {
		
	}
	
	//public static final String CLASSPATH = "src/main/resources/";
	
	private SimpleJdbcTemplate(String driverName, String url, String username, String password) {
		super();
		this.jdbcProperties.put("jdbc.driverName", driverName);
		this.jdbcProperties.put("jdbc.url", url);
		this.jdbcProperties.put("jdbc.username", username);
		this.jdbcProperties.put("jdbc.password", password);
	}
	
	private SimpleJdbcTemplate(Properties jdbcProperties) {
		super();
		this.jdbcProperties = jdbcProperties;
	}

	/**
	 * Creating the SimpleJdbcTemplate object using properties file.
	 * If you providing properties file then make sure in that properties file only jdbc related keys
	 * you put. Those key are - jdbc.driver, jdbc.url, jdbc.username, jdbc.password
	 * If you are putting extra key in properties file then no problem its work fine but abou key must be present.
	 * 
	 * @param fileUrl
	 * @return SimpleJdbcTemplate
	 */
	public static SimpleJdbcTemplate createTemplateUsingPropertiesFile(String fileUrl) {
		System.err.println("INFO: SimpleJdbcTemplate creation process starts");
		Properties p = new Properties();
		try {
			InputStream stream = SimpleJdbcTemplate.class.getResourceAsStream("/" + fileUrl);
	        p.load(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println("INFO: SimpleJdbcTemplate creation process ends");
		return new SimpleJdbcTemplate(p);
	}
	
	/**
	 * Making the connection with database using given jdbc properties
	 * @return
	 */
	private Connection createConnection() {
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
	 * Creating Query object and setting programmer user query, connection details to the object
	 * @param sqlQuery
	 * @return
	 */
	public Query createQuery(String sqlQuery) {
		System.err.println("INFO: Query creation process starts");
		Query query = new Query(sqlQuery, createConnection());
		System.err.println("INFO: Query creation process ends");
		return query;
	}
	
	/**
	 * Creating Query object and setting programmer user query, setting query parameter, connection details to the object
	 * @param sqlQuery
	 * @param params
	 * @return
	 */
	public Query createQuery(String sqlQuery, Object... params) {
		System.err.println("INFO: Query creation process starts");
		Query query = new Query(sqlQuery, createConnection(), params);
		System.err.println("INFO: Query creation process ends");
		return query;
	}
}
