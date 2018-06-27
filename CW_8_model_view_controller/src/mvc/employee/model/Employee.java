package mvc.employee.model;


import java.time.LocalDate;
import javafx.beans.property.*;


public class Employee {

	private IntegerProperty employeeId, managerId, departmentId, salary;

	private StringProperty firstName, lastName, email, phoneName, jobId, departmentName, jobName, managerName;
	private ObjectProperty<LocalDate> hireDate;

	
	
	
	public Employee() {
		//this();
		employeeId = new SimpleIntegerProperty();
		managerId = new SimpleIntegerProperty();
		departmentId = new SimpleIntegerProperty();
		salary = new SimpleIntegerProperty();
		firstName = new SimpleStringProperty();
		lastName = new SimpleStringProperty();
		email = new SimpleStringProperty();
		phoneName = new SimpleStringProperty();
		jobId = new SimpleStringProperty();
		hireDate = new SimpleObjectProperty();
		departmentName = new SimpleStringProperty();
		jobName = new SimpleStringProperty();
		managerName = new SimpleStringProperty();
	}

	public StringProperty getManagerName() {
		return managerName;
	}
	public void setManagerName(String s) {
		managerName.set(s);
	}
	
	public void setEmployeeId(int id) {
		employeeId.set(id);
	}
	
	
	public StringProperty getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName.set(jobName);
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
	public void setEmail(String email) {
		this.email.set(email);
	}
	public void setPhoneName(String phoneName) {
		this.phoneName.set(phoneName);
	}
	public void setHireDate(LocalDate hireDate) {
		this.hireDate.set(hireDate);
	}
	public void setJobId(String jobId) {
		this.jobId.set(jobId);
	}
	public void setSalary(int salary) {
		this.salary.set(salary);
	}
	public void setManagerId(int managerId) {
		this.managerId.set(managerId);
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId.set(departmentId);
	}
	
	public void setDepartmentName(String s) {
		this.departmentName.set(s);
	}
	///////////////////////////////////////////////////////////////////
	public int getEmployeeId() {
		return this.employeeId.get();
	}
	

	
	public String getFirstName() {
		return this.firstName.get();
	}
	
	public String getLastName() {
		return this.lastName.get();
	}
	

	public String getEmail() {
		return this.email.get();
	}
	public String getPhoneName() {
		return this.phoneName.get();
	}
	public LocalDate getHireDate() {
		return this.hireDate.get();
	}
	public String getJobId() {
		return this.jobId.get();
	}
	public int getSalary() {
		return this.salary.get();
	}	
	public int getManagerId() {
		return this.managerId.get();
	}
	public int getDepartmentId() {
		return this.departmentId.get();
	}
	
	public String getDepartmentName() {
		return this.departmentName.get();
	}
//	@Override
//	public String toString() {
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		return 
//			(Config.isEmployeeId? String.valueOf(getEmployeeId()) + ", " : "") +
//			(Config.isFirstName? getFirstName() + ", " : "") + 
//			(Config.isLastName? getLastName() + ", " : "") + 
//			(Config.isEmail? getEmail() + ", " : "") + 
//			(Config.isPhoneName? getPhoneName() + ", " : "") + 
//			(Config.isHireDate? dtf.format(getHireDate()) + ", " : "") + 
//			(Config.isJobId? getJobId() + ", " : "") + 
//			(Config.isSalary? String.valueOf(getPhoneName()) + ", " : "") + 
//			(Config.isManagerId? String.valueOf(getManagerId()) + ", " : "") + 
//			(Config.isDepartmentId? String.valueOf(getDepartmentId()) + ", " : "");
//	}
	
	public IntegerProperty employeeIdProperty(){
		return employeeId;
	}
	
	public StringProperty firstNameProperty() {
		return firstName;
	}
	
	public StringProperty lastNameProperty(){
		return lastName;
	}
	
	public StringProperty emailProperty(){
		return email;
	}
	
	public StringProperty phoneNameProperty(){
		return phoneName;
	}
	
	public ObjectProperty hireDateProperty(){
		return hireDate;
	}
	
	public IntegerProperty managerIdProperty(){
		return managerId;
	}
	
	public StringProperty jobIdProperty(){
		return jobId;
	}
	
	public IntegerProperty salaryProperty(){
		return salary;
	}
	
	public IntegerProperty departmentIdProperty(){
		return departmentId;
	}
	
	public StringProperty departmentName() {
		return departmentName;
	}

}
