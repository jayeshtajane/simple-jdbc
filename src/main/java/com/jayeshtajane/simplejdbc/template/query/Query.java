package com.jayeshtajane.simplejdbc.template.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jayeshtajane.simplejdbc.JdbcTemplate;
import com.jayeshtajane.simplejdbc.mapper.RowMapper;

/**
 * <p>This class is use to perform a database operations.
 * You don't need to create this object. You have just call the <code>createQuery()</code>
 * method which is present in <code>JdbcTemplate</code>.</p>
 * 
 * @author Jayesh Tajane
 *
 * @version 1.0
 * @see JdbcTemplate#createQuery(String)
 * @see JdbcTemplate#createQuery(String, Object...)
 */
public class Query {

	/**
	 *  <p>Storing the connection object</p> 
	 */
	private Connection connection;
	
	/**
	 *  <p>Stores the PreparedStatement for current query</p> 
	 */
	private PreparedStatement statement;
	
	/**
	 * <p>Creating <code>Query</code> object by using <code>userQuery</code> and <code>connection</code> .
	 * Setting <code>connection</code> and <code>statement</code>.</p>
	 * 
	 * @param userQuery - SQL query provided by programmer.
	 * @param connection - <code>Connection</code> object
	 */
	public Query(String userQuery, Connection connection) {
		super();
		/* Setting connection */
		this.connection = connection;
		/* Creating a PreparedStatement */
		try {
			this.statement = this.connection.prepareStatement(userQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>Creating <code>Query</code> object by using <code>userQuery</code>, 
	 * <code>connection</code> and <code>queryParam</code></p>
	 * 
	 * @param userQuery - SQL query provided by programmer.
	 * @param connection - <code>Connection</code> object
	 * @param queryParam - Values that you want to replace with question marks
	 *  that are present in query. Order must follow.
	 */
	public Query(String userQuery, Connection connection, Object... queryParam) {
		super();
		/* Setting connection */
		this.connection = connection;
		
		try {
			this.statement = this.connection.prepareStatement(userQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/* Setting data to PreparedStatement */
		for(int i=0;i<queryParam.length;i++) {
			setQueryParam(i+1,queryParam[i]);
		}
	}

	/**
	 * <p>Setting parameters to Query object. This function internally 
	 * set the provided data to the PreparedStatement object.</p>
	 * 
	 * @param index - indicates the index of question mark in query. Not exactly index see the example.
	 * @param data - indicates the value that you want to replace with question marks given index in query.
	 * 
	 */
	public void setQueryParam(int index, Object data) {
		System.err.println("INFO: Parameter setting starts index is " + index);
		try {
			/* Setting data to PreparedStatement Object */
			statement.setObject(index, data);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		System.err.println("INFO: Parameter setting ends index is " + index);
	}
	
	/**
	 * <p>Setting Setting parameters to Query object. 
	 * This function internally set the provided data to the PreparedStatement 
	 * object using setQueryParam(int index, Object data).
	 * Position of data is matters. </p>
	 * 
	 * @param data - indicates the list of values that you want to replace with 
	 * question marks of query. Order must follow.
	 */
	public void setQueryParam(Object... data) {
		for(int i=0;i<data.length;i++) {
			setQueryParam(i+1,data[i]);
		}
	}
	
	/**
	 * <p>When you want to execute the non-select query means CREATE, UPADATE, 
	 * DELETE etc. then use this function</p>
	 * @return - number of rows affected.
	 */
	public int update() {
		System.err.println("INFO: Query execution starts update()");
		int num = 0;
		try {
			/* Executing the query */
			num = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.err.println("INFO: Query execution ends update()");
		return num;
	}
	
	/**
	 * <p>When your operation is SELECT and you know the query returns only 
	 * one or zero row then use this function.
	 * If your query returns the more than two rows then the first row of 
	 * that result will return.</p>
	 * 
	 * @param rowMapper - implementation class of RowMapper
	 * @return - object of <code>T</code>
	 */
	public <T> T get(RowMapper<T> rowMapper) {
		System.err.println("INFO: Query execution starts get(...)");
		ResultSet rs=null;
		T entity = null;
		try {
			rs = statement.executeQuery();
			if(rs.next())
				entity = rowMapper.mapRow(rs, 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.err.println("INFO: Query execution ends get(...)");
		return entity;
	}
	
	/**
	 * <p>When your operation is SELECT and you know the query returns 
	 * zero more rows then use this function.</p>
	 *   
	 * @param rowMapper - implementation class of RowMapper
	 * @return - List
	 */
	public <T> List<T> load(RowMapper<T> rowMapper) {
		System.err.println("INFO: Query execution starts loadAll(...)");
		ResultSet rs=null;
		List<T> dataList = new ArrayList<T>();
		try {
			rs = statement.executeQuery();
			while(rs.next()) {
				dataList.add(rowMapper.mapRow(rs, rs.getRow())); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.err.println("INFO: Query execution ends loadAll(...)");
		return dataList;
	}
}
