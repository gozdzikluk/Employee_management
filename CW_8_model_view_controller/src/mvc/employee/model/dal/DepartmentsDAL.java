package mvc.employee.model.dal;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mvc.employee.model.Department;

public class DepartmentsDAL {
	private ObservableList<String> depNames = FXCollections.observableArrayList();
	private HashMap<String, Integer> depNamesList = new HashMap<String, Integer>();
	
	SQLException ex;
	public SQLException getSQLException() {
		return ex;
	}

	public DepartmentsDAL() { }
	
	public ObservableList<Department> getDepartments() {
		
		ObservableList<Department> jobs = FXCollections.observableArrayList();
		try (Statement statement = OraConn.getConnection().createStatement();) {
		    
			String query = "SELECT * FROM DEPARTMENTS";
	        ResultSet resultSet = statement.executeQuery(query);
	        
	        
	        while (resultSet.next()) {
	        	jobs.add(rs2Department(resultSet));
	        }
		}
		catch (SQLException ex ) {
			System.out.println(ex);
		} 
		return jobs;
	}
	
	public ObservableList<Department>  getDepartmentsByDepartmentId(int DepartmentId) {
		
		ObservableList<Department> jobs = FXCollections.observableArrayList();
		try (Statement statement = OraConn.getConnection().createStatement();) {
		    
			String query = "SELECT * FROM DEPARTMENTS WHERE DEPARTMENT_ID = " + DepartmentId ;
	        ResultSet resultSet = statement.executeQuery(query);
	        
	        while (resultSet.next()) {
	        	jobs.add(rs2Department(resultSet));
	        }
		}
		catch (SQLException ex ) {
			System.out.println(ex);
		} 
		return jobs;
	}
	
	private Department rs2Department(ResultSet resultSet){
		Department dep = new Department();
		try {
			dep.setDepartmentId(resultSet.getInt(1));
			dep.setDepartmentName(resultSet.getNString(2));
			dep.setManagerId(resultSet.getInt(3));
			dep.setLocationId(resultSet.getInt(4));
			depNames.add(dep.getDepartmentName());
			depNamesList.put(dep.getDepartmentName().toString(), dep.getDepartmentId());
		}
		catch (SQLException ex ) {
			this.ex = ex;
		}
		return dep;
	}
	
	public ObservableList<String> getDepNames(){
		getDepartments();
		return depNames;
	}
	
	public HashMap<String, Integer> getDepNamesList(){
		getDepartments();
		return depNamesList;
	}
	

}
	
	
	
	
	

