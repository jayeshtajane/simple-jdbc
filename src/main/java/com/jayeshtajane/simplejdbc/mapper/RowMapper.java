package com.jayeshtajane.simplejdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jayeshtajane.simplejdbc.JdbcTemplate;
import com.jayeshtajane.simplejdbc.template.query.Query;

/**
 * <p>An interface used by JdbcTemplate for mapping rows of a ResultSet on a per-row basis. 
 * Implementations of this interface perform the actual work of mapping each row to a result
 * object, but don't need to worry about exception handling. SQLExceptions will be caught and
 * handled by the calling JdbcTemplate.</p>
 * 
 * @author Jayesh Tajane
 *
 * @param <T> the result type
 * 
 * @version 1.0
 * 
 * @see JdbcTemplate
 * @see Query
 */
@FunctionalInterface
public interface RowMapper <T> {
	/**
	 * <p>Implementations must implement this method to map each row of data in the ResultSet.</p>
	 * 
	 * @param resultSet - the ResultSet to map (pre-initialized for the current row)
	 * @param rowNumber - the number of the current row
	 * @return the result object for the current row (may be null)
	 * @throws SQLException - if a SQLException is encountered getting column values (that is, 
	 * there's no need to catch SQLException)
	 */
	T mapRow(ResultSet resultSet, int rowNumber) throws SQLException; 
}
