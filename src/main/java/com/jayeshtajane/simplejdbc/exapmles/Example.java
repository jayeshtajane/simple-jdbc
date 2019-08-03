package com.jayeshtajane.simplejdbc.exapmles;

import java.util.List;

import com.jayeshtajane.simplejdbc.template.SimpleJdbcTemplate;
import com.jayeshtajane.simplejdbc.template.query.Query;

/**
 * Example code
 *
 */
public class Example 
{
    public static void main( String[] args )
    {
    	// To test create object and call the functions.
    	
    	//Example e = new Example();
    	
    	//e.insertQueryExample();
    	//e.updateQueryExample();
    	//e.deleteQueryExample();
    	//e.singleSelectQueryExample();
    	//e.multipleSelectQueryExample();
    	    	
    }
    
    public void insertQueryExample() {
    	SimpleJdbcTemplate jt=SimpleJdbcTemplate.createTemplateUsingPropertiesFile("app.properties");
        Query query=jt.createQuery("insert into emptab values(?,?,?)",105,"JAYDADA",55.5);
        System.out.println("Rows affected : " + query.update());
    }
    
    public void updateQueryExample() {
    	SimpleJdbcTemplate jt=SimpleJdbcTemplate.createTemplateUsingPropertiesFile("app.properties");
    	Query query=jt.createQuery("update emptab set ename=?,esal=? where eid=?","JAYT",59.5,105);
        System.out.println("Rows affected : " + query.update());
    }
    
    public void deleteQueryExample() {
    	SimpleJdbcTemplate jt=SimpleJdbcTemplate.createTemplateUsingPropertiesFile("app.properties");
    	Query query=jt.createQuery("delete from emptab where eid=?",105);
        System.out.println("Rows affected : " + query.update());
    }
    
    public void singleSelectQueryExample() {
    	SimpleJdbcTemplate jt=SimpleJdbcTemplate.createTemplateUsingPropertiesFile("app.properties");
    	Query query1=jt.createQuery("select * from emptab where eid=10");
    	Employee e = query1.get(new EmployeeRowMapper());
    	System.out.println(e);
    }
    
    public void multipleSelectQueryExample() {
    	SimpleJdbcTemplate jt=SimpleJdbcTemplate.createTemplateUsingPropertiesFile("app.properties");
    	Query query=jt.createQuery("select * from emptab");
    	List<Employee> el = query.load(new EmployeeRowMapper());
    	System.out.println(el);
    }
}
