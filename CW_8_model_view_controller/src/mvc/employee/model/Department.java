package mvc.employee.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Department {
	private IntegerProperty departmentIdp;
	private int departmentId, managerId, locationId;
	private String departmentName;
	
	
	public Department(){}
	public Department(int departmentId) {
		this.departmentId = departmentId;
	}
	
	@Override
	public String toString() {
		return String.valueOf(departmentId);
	}
	public void setDepartmentId(int depId) {
		this.departmentId = depId;
	}
	public void setManagerId(int int1) {
		this.managerId = int1;
	}
	public void setLocationId(int int1) {
		this.locationId = int1;
	}
	
	public int getDepartmentId(){
		return departmentId;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	
	public IntegerProperty departmentIdProperty(){
		return departmentIdp;
	}
	public void setDepartmentName(String nString) {
		this.departmentName = nString;
		
	}
	
	
	
	
	
}
