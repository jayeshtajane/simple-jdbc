package com.jayeshtajane.simplejdbc;

import java.sql.Connection;

import com.jayeshtajane.simplejdbc.template.query.Query;

/**
 * <p>The JdbcTemplate is able to create <code>Connection</code> object and <code>Query</code> object.
 * This class stores the meta-data for connection. It will provide current connection object by calling
 *  <code>getConnection()</code>.</p>
 * 
 * @author Jayesh Tajane
 * @version 1.0
 * @see Query
 */ 
public interface JdbcTemplate {
	
	/**
	 * <p>Making the connection with database using given jdbc properties.</p>
	 * 
	 * @return Connection object
	 */
	public Connection getConnection();
	
	/**
	 * <p>Creating Query object and setting the query to <code>Query</code> object which is provided by programmer,
	 * and also setting connection details to <code>Query</code> object.</p>
	 * 
	 * @param sqlQuery - SQL query
	 * @return newly create <code>Query</code> object.
	 */
	public Query createQuery(String sqlQuery);
	
	/**
	 * <p>Creating Query object and setting the query to <code>Query</code> object which is provided by programmer, 
	 * and also setting query parameters, connection details to the <code>Query</code> object.</p>
	 * 
	 * @param sqlQuery - SQL query.
	 * @param params - Values that you replace with question mark. Order must follow.
	 * @return newly create <code>Query</code> object.
	 */
	public Query createQuery(String sqlQuery, Object... params);
}
