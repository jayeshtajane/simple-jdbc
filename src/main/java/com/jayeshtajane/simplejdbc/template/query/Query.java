package com.jayeshtajane.simplejdbc.template.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jayeshtajane.simplejdbc.mapper.RowMapper;

public class Query {

	/* Storing the connection object */
	private Connection connection;
	/* Stores the PreparedStatement for current query */
	private PreparedStatement statement;
	
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

	public Query(String userQuery, Connection connection, Object... queryParam) {
		super();
		/* Setting connection */
		this.connection = connection;
		
		/* Creating a Query q=jt.createQuery("select * from emptab where eid=10"); */
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
	 * Setting Setting parameters to Query object. This function internally set the provided data to the PreparedStatement object.
	 * @param index - indicates the parameter index.
	 * @param data - indicates the data of index value.
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
	 * Setting Setting parameters to Query object. 
	 * This function internally set the provided data to the PreparedStatement object using setQueryParam(int index, Object data).
	 * Position of data is matters. 
	 * @param data
	 */
	public void setQueryParam(Object... data) {
		for(int i=0;i<data.length;i++) {
			setQueryParam(i+1,data[i]);
		}
	}
	
	/**
	 * When you want to execute the non-select query means CREATE, UPADATE, DELETE etc.
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
	 * When your operation is SELECT and you know the query returns only one or zero row then use this function.  
	 * @param rowMapper - implementation class of RowMapper
	 * @return - List<T>
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
	 * When your operation is SELECT and you know the query returns one more rows then use this function.  
	 * @param rowMapper - implementation class of RowMapper
	 * @return - List<T>
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
	
	public <T> T[] loadAll(Class<T> userClass) {
		return null;
	}
}
