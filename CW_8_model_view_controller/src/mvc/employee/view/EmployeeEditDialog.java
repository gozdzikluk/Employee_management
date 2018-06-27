package mvc.employee.view;



import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import mvc.employee.model.Employee;
import mvc.employee.model.dal.DepartmentsDAL;
import mvc.employee.model.dal.EmployeesDAL;
import mvc.employee.model.dal.JobsDAL;

public class EmployeeEditDialog {
	
	private DepartmentsDAL departments = new DepartmentsDAL();
	private JobsDAL jobs = new JobsDAL();
	private EmployeesDAL employees = new EmployeesDAL();
	private Stage dialogStage;
	private Employee employee;
	private boolean okClicked = false;
	private final String pattern = "yyyy-MM-dd";
	private LocalDate localDate;

	
	@FXML private TextField temployeeId;
	@FXML private TextField tfirstName;
	@FXML private TextField tlastName;
	@FXML private TextField temail;
	@FXML private TextField tphone;
	@FXML private TextField tsalary;
	@FXML private ComboBox comboDepartment;
	@FXML private ComboBox comboJobs;
	@FXML private ComboBox comboManagerId;
	@FXML private DatePicker hireDatePicker;
	
	
	@FXML
	private void initialize() {
		comboDepartment.setItems(new DepartmentsDAL().getDepNames());
		comboJobs.setItems(jobs.getJobs());
		comboManagerId.setItems(employees.getManagersList());
		
		hireDatePicker.setPromptText(pattern.toLowerCase());

		hireDatePicker.setConverter(new StringConverter<LocalDate>() {
		     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 });
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@SuppressWarnings("unchecked")
	public void setEmployee(Employee employee) {
		this.employee = employee;
		
		temployeeId.setText(Integer.toString(employee.getEmployeeId()));
		tfirstName.setText(employee.getFirstName());
		tlastName.setText(employee.getLastName());
		temail.setText(employee.getEmail());
		tphone.setText(employee.getPhoneName());
		tsalary.setText(Integer.toString(employee.getSalary()));
		comboDepartment.setValue(employee.getDepartmentName());
		comboJobs.setValue(employee.getJobName().get());
		hireDatePicker.setValue(employee.getHireDate());
		comboManagerId.setValue(employee.getManagerId()+" "+employee.getManagerName().get());
	}
	
	public void setEmployee2(Employee employee) {
		this.employee = employee;
	}
	
	public boolean isClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleOK() {
		if(isInputValid()) {
			employee.setEmployeeId(Integer.parseInt(temployeeId.getText()));
			employee.setFirstName(tfirstName.getText());
			employee.setLastName(tlastName.getText());
			employee.setEmail(temail.getText());
			employee.setPhoneName(tphone.getText());
			employee.setSalary(Integer.parseInt(tsalary.getText()));
			employee.setJobName(comboJobs.getValue().toString());	
			employee.setDepartmentName(comboDepartment.getValue().toString());
			employee.setJobId(employees.getJobNameList().get(comboJobs.getValue().toString()));
			employee.setDepartmentId(departments.getDepNamesList().get(employee.getDepartmentName()));
			
			try {
				int i = ((Number)NumberFormat.getInstance().parse(comboManagerId.getValue().toString())).intValue();
						employee.setManagerId(i);
				employee.setManagerName(employees.getManagersNames().get(i));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			employee.setHireDate(hireDatePicker.getValue());
			
			okClicked = true;
			dialogStage.close();
		}
	}
	
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	
	private boolean isInputValid() {
		String errorMessage = "";

	        if (tfirstName.getText() == null || tfirstName.getText().length() == 0) {
	            errorMessage += "Brak wprowadzonego imienia!!\n"; 
	        }
	        if (tlastName.getText() == null || tlastName.getText().length() == 0) {
	            errorMessage += "Brak wprowadzonego nazwiska!\n"; 
	        }
	        if (temail.getText() == null || temail.getText().length() == 0) {
	            errorMessage += "Brak wprowadzonego adresu email!\n"; 
	        }

	        if (tphone.getText() == null || tphone.getText().length() == 0) {
	            errorMessage += "Brak wprowadzonego numeru telefonu!\n"; 
	        } 
  
	        if (temployeeId.getText() == null || temployeeId.getText().length() == 0) {
	            errorMessage += "Brak wprowadzonego identyfikatora pracownika!\n";
	        }
	        if (tsalary.getText() == null || tsalary.getText().length() == 0) {
	            errorMessage += "Brak wprowadzonego wynagrodzenia!\n";
	        }
	        if (comboDepartment.getValue() == null) {
	        	errorMessage += "Brak wprowadzonego dzia³u!\n";
	        }
	        if (hireDatePicker.getValue() == null) {
	        	errorMessage += "Brak wprowadzonej daty zatrudnienia!\n";
	        }
	        if (comboManagerId.getValue() == null) {
	        	errorMessage += "Brak wprowadzonego managera!\n";
	        }
	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(dialogStage);
	            alert.setTitle("Niew³aœciwie wype³nione pola");
	            alert.setHeaderText("Proszê poprawic wprowadzone dane");
	            alert.setContentText(errorMessage);

	            alert.showAndWait();

	            return false;
	        }
	    }
	
	
	
	
}
