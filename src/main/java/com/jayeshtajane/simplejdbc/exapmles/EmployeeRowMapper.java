package com.jayeshtajane.simplejdbc.exapmles;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jayeshtajane.simplejdbc.mapper.RowMapper;

/**
 * This is a example of row mapper.
 * @author jayes
 *
 */
public class EmployeeRowMapper implements RowMapper <Employee> {

	@Override
	public Employee mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		Employee e = new Employee();
		e.setEmpId(resultSet.getInt("eid")); 
		e.setEmpName(resultSet.getString("ename"));
		e.setEmpSal(resultSet.getDouble("esal"));
		return e;
	}

}
