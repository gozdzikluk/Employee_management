package mvc.employee.view;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mvc.employee.Main;
import mvc.employee.model.Department;
import mvc.employee.model.Employee;
import mvc.employee.model.dal.EmployeesDAL;

public class EmployeeController {
	private ObservableList<Department> dep = null;
	private ObservableList<Employee> empList = null;
	
	@FXML private TableView<Employee> employeeTable;
	
	@FXML private TableColumn<Employee, Integer> employeeIdColumn;
	@FXML private TableColumn<Employee, String> firstNameColumn;
	@FXML private TableColumn<Employee, String> lastNameColumn;
	@FXML private TableColumn<Employee, String> phoneNumberColumn;
	@FXML private TableColumn<Employee, String> emailColumn;
	@FXML private TableColumn<Employee, LocalDate> hireDateColumn;
	@FXML private TableColumn<Employee, String> jobIdColumn;
	@FXML private TableColumn<Employee, Integer> salaryColumn;
	@FXML private TableColumn<Employee, String> managerIdColumn;
	@FXML private TableColumn<Employee, String> departmentIdColumn;
	
	@FXML private Label employeeIdLabel;
	@FXML private Label firstNameLabel;
	@FXML private Label lastNameLabel;
	@FXML private Label emailLabel;
	@FXML private Label phoneNumberLabel;
	@FXML private Label jobIdLabel;
	@FXML private Label managerIdLabel;
	@FXML private Label departmentIdLabel;
	@FXML private Label salaryLabel;
	@FXML private Label hireDateLabel;
	
	
	
	@FXML
	private void initialize(){
		employeeTable.setTableMenuButtonVisible(true);
		
		employeeIdColumn.setCellValueFactory(
				cellData -> cellData.getValue().employeeIdProperty().asObject());
		firstNameColumn.setCellValueFactory(
				cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(
				cellData -> cellData.getValue().lastNameProperty());
		emailColumn.setCellValueFactory(
				cellData -> cellData.getValue().emailProperty());
		phoneNumberColumn.setCellValueFactory(
				cellData -> cellData.getValue().phoneNameProperty());
		hireDateColumn.setCellValueFactory(
				cellData -> cellData.getValue().hireDateProperty());
		jobIdColumn.setCellValueFactory(
				cellData -> cellData.getValue().getJobName());
		salaryColumn.setCellValueFactory(
				cellData -> cellData.getValue().salaryProperty().asObject());
//		managerIdColumn.setCellValueFactory(
//				cellData -> cellData.getValue().getManagerName());
		departmentIdColumn.setCellValueFactory(
				cellData -> cellData.getValue().departmentName());
		managerIdColumn.setCellValueFactory(
				cellData -> cellData.getValue().getManagerName());
//		departmentIdColumn.setCellValueFactory(
//				cellData -> cellData.getValue().departmentIdProperty().asObject());

		
		refreshEmployee(null); 
		
		employeeTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> refreshEmployee(newValue));
		
	}
	
	public void setEmployees(ObservableList<Employee> olEmployees){
		employeeTable.getItems().clear();
		employeeTable.setItems(olEmployees);	
		empList = olEmployees;
	}
	
	public void setDepartments(ObservableList<Department> olDepartments) {
		this.dep = olDepartments;
	}
	

	
	private void refreshEmployee(Employee emp) {
		if (emp != null){
			employeeIdLabel.setText(Integer.toString(emp.getEmployeeId()));
			firstNameLabel.setText(emp.getFirstName());
			lastNameLabel.setText(emp.getLastName());
			emailLabel.setText(emp.getEmail());
			phoneNumberLabel.setText(emp.getPhoneName());
			jobIdLabel.setText(emp.getJobName().get());
			managerIdLabel.setText(emp.getManagerName().get());
			departmentIdLabel.setText(emp.getDepartmentName());
			salaryLabel.setText(Double.toString(emp.getSalary()));
			hireDateLabel.setText(emp.getHireDate().toString());
		}
	}
	
	@FXML
	public void deleteEmployee(){
		int selIdx =  employeeTable.getSelectionModel().getSelectedIndex();      
		if (selIdx >= 0) {
			employeeTable.getItems().remove(selIdx); 
			EmployeesDAL.deleteByEmployeeId(employeeTable.getSelectionModel().getSelectedItem().getEmployeeId());
			}
	}
	
	@FXML
	private void handleEditPerson() {
	    Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
	    if (selectedEmployee != null) {
	        boolean okClicked = Main.showEmployeeEditDialog(selectedEmployee);
	        if (okClicked) {
	            refreshEmployee(selectedEmployee);
	            System.out.println(EmployeesDAL.updateEmployee(selectedEmployee));
	        }

	    } else {
	        // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(Main.getPrimaryStage());
	        alert.setTitle("Brak zaznaczonego pracownika!");
	        alert.setHeaderText("B³¹d wyboru pracownika!");
	        alert.setContentText("Najpierw nale¿y wybraæ pracownika z listy.");
	        alert.showAndWait();
	    }
	}
	
	@FXML
	private void handleNewEmployee() {
	    Employee tempEmployee = new Employee();
	    boolean okClicked = Main.showEmployeeAddDialog(tempEmployee);
	    if (okClicked) {
	        EmployeesDAL.insertEmployee(tempEmployee);
	        employeeTable.getItems().add(tempEmployee);
	    }
	}
	
	
}
