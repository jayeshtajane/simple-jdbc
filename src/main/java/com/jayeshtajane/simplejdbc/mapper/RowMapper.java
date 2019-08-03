package com.jayeshtajane.simplejdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper <T> {
	T mapRow(ResultSet resultSet, int rowNumber) throws SQLException; 
}
