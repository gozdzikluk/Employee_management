package mvc.employee.model.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import mvc.employee.model.Department;
import mvc.employee.model.Employee;
import mvc.employee.model.Job;


public class EmployeesDAL {
	//private OraConn oraConn;
	private static SQLException ex;
	private ObservableList<Department> depList;
	private ObservableList<Job> jobList;
	private ObservableList<Employee> empList;
	private ObservableList<String> managersList = FXCollections.observableArrayList();
	private HashMap<Integer, String> managersNames = new HashMap<Integer, String>();
	private HashMap<String, String> jobsNames = new HashMap<String, String>();
	
	
	public SQLException getSQLException() {
		return ex;
	}

	public EmployeesDAL() { 
		depList = new DepartmentsDAL().getDepartments();
		jobList = new JobsDAL().getJobs();
		empList = getEmployees();
		}
	
	public ObservableList<Employee> getEmployees() {
		ObservableList<Employee> employees = FXCollections.observableArrayList();
		try (Statement statement = OraConn.getConnection().createStatement();) {
		    
			String query = "SELECT * FROM EMPLOYEES";
	        ResultSet resultSet = statement.executeQuery(query);
	       
	        while (resultSet.next()) {
	        	employees.add(rs2Employee(resultSet));
	        }
		}
		catch (SQLException ex ) {
			System.out.println(ex);
		} 
		return employees;
	}
	
	public ObservableList<Employee>  getEmployeesByEmployeeId(int EmployeeId) {
		
		ObservableList<Employee> employees = FXCollections.observableArrayList();
		try (Statement statement = OraConn.getConnection().createStatement();) {
		    
			String query = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID =" + EmployeeId;
	        ResultSet resultSet = statement.executeQuery(query);
	        
	        while (resultSet.next()) {	       
	        		employees.add(rs2Employee(resultSet));
	        }
		}
		catch (SQLException ex ) {
			System.out.println(ex);
		} 
		return employees;
	}
	
	public static int deleteByEmployeeId(int EmployeeId) {

		try (Statement statement = OraConn.getConnection().createStatement();) {
		    
			String query = "DELETE FROM EMPLOYEES WHERE EMPLOYEE_ID =" + EmployeeId;
			int affectedRows = statement.executeUpdate(query);
	        return affectedRows;
		}
		catch (SQLException e ) {
			ex = e;
			return 0;
		} 
	}

	public static int updateEmployee(Employee emp) {
		try (Statement statement = OraConn.getConnection().createStatement();) {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
			String hireDate = dtf.format(emp.getHireDate());
			
			String query = "UPDATE EMPLOYEES SET "
					+ "LAST_NAME     = '"  + emp.getLastName() 	   + "', "
					+ "FIRST_NAME    = '"  + emp.getFirstName()    + "', "
					+ "EMAIL         = '"  + emp.getEmail() 	   + "', "
					+ "JOB_ID        = '"  + emp.getJobId() 	   + "', "
					+ "PHONE_NUMBER  = '"  + emp.getPhoneName() 	   + "', "
					+ "HIRE_DATE     =  to_date('"  + hireDate + "', 'yyyyMMdd') , "	
					+ "DEPARTMENT_ID =  "  + emp.getDepartmentId() + " , "
					+ "MANAGER_ID    =  "  + emp.getManagerId()    + " , "
					+ "SALARY        =  "  + emp.getSalary() 	   + "   "
					+ "WHERE " 
					+ "EMPLOYEE_ID   =  " + emp.getEmployeeId();
	        int affectedRows = statement.executeUpdate(query);
	        OraConn.getConnection().commit();
	        return affectedRows;
		}
		catch (SQLException e ) {
			ex = e;
			return 0;
		} 
	}
	
	public static int insertEmployee(Employee emp) {
		try (Statement statement = OraConn.getConnection().createStatement();) {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
			String hireDate = dtf.format(emp.getHireDate());
					
			String query = "INSERT INTO EMPLOYEES VALUES("
					+ "(SELECT MAX(EMPLOYEE_ID) + 1 FROM EMPLOYEES) , '" 
					+ emp.getFirstName()    + "','"
					+ emp.getLastName()     + "','"
					+ emp.getEmail() 	    + "','"
					+ emp.getPhoneName() 	    + "', "
					+ "to_date('" + hireDate + "','yy/MM/dd'), '"	
					+ emp.getJobId() 	    + "', "
					+ emp.getSalary() 	    + " , "
					+ "null"				+ " , "
					+ emp.getManagerId() 	+ " , "
					+ emp.getDepartmentId() + " ) " ;
			return statement.executeUpdate(query);
		}
		catch (SQLException e ) {
			ex = e;
			return 0;
		} 
	}
	
	private Employee rs2Employee(ResultSet resultSet) throws SQLException{
		Employee emp = null;
		
		try {
			int col = 1;
			emp = new Employee();
			emp.setEmployeeId(resultSet.getInt(col++));
	    	emp.setFirstName(resultSet.getNString(col++));
	    	emp.setLastName(resultSet.getNString(col++));
	    	emp.setEmail(resultSet.getNString(col++));
	    	emp.setPhoneName(resultSet.getNString(col++));
	    	emp.setHireDate(resultSet.getDate(col++).toLocalDate());
	    	emp.setJobId(resultSet.getNString(col++));
	    	emp.setSalary(resultSet.getInt(col++));
	    	col++;
	    	emp.setManagerId(resultSet.getInt(col++));
	    	emp.setDepartmentId(resultSet.getInt(col++));
	    	emp.setDepartmentName(getDepName(emp.getDepartmentId()));
	    	emp.setJobName(getJobName(emp.getJobId()));
	    	String s = Integer.toString(emp.getEmployeeId())+" "+emp.getFirstName()+ " "+emp.getLastName();
	    	managersList.add(s);
	    	jobsNames.put(emp.getJobName().get(), emp.getJobId());
	    	managersNames.put(emp.getEmployeeId(), emp.getFirstName()+" "+emp.getLastName());
	    	emp.setManagerName(managersNames.get(emp.getEmployeeId()));
		}
		catch (SQLException ex ) {
			this.ex = ex;
		}
		return emp;
	}
	
	private String getDepName(int depId) {
		String s = "null";
		Integer i = depId;
		for(Department d : depList) {
    		if(d.toString().equals(i.toString())) {
    			s = d.getDepartmentName();
    		}
		}
		return s;
	}
	
	
	
	private String getJobName(String jobId) {
		for(Job j : jobList) {
			if(j.getJobId().equals(jobId.toString())) {
    			return j.toString();
    		}
		}
		return "null";
	}
	
	public ObservableList<String> getManagersList(){
		getEmployees();
		return managersList;
	}
	
	public HashMap<Integer, String> getManagersNames() {
		return managersNames;
	}
	
	public HashMap<String, String> getJobNameList(){
		return jobsNames;
		
	}
}

