package mvc.employee.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mvc.employee.Main;

public class MainController {
	private Stage primaryStage;
	private Main mainApp;

	
	ViewLoader<AnchorPane, EmployeeController> viewLoaderEmp;
	
	public void setStage(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	
	public void setEmployeeFXML(ViewLoader<AnchorPane, EmployeeController> viewLoaderEmp){
		this.viewLoaderEmp = viewLoaderEmp;
	}
	
	@FXML 
	private void menuItem_About(){
		 Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Aplikacja do zarz¹dzania pracownikami");
	        alert.setHeaderText("O programie");
	        alert.setContentText("Autor: £ukasz Goziewski");
	        alert.showAndWait();
	}
	
	@FXML
	private void menuItem_Exit(){
		primaryStage.fireEvent(new WindowEvent(primaryStage,WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void deleteEmployee() {
		
	}
}
