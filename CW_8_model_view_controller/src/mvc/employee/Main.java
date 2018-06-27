package mvc.employee;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import mvc.employee.model.Employee;
import mvc.employee.model.dal.DepartmentsDAL;
import mvc.employee.model.dal.EmployeesDAL;
import mvc.employee.model.dal.OraConn;
import mvc.employee.view.EmployeeController;
import mvc.employee.view.EmployeeEditDialog;
import mvc.employee.view.MainController;
import mvc.employee.view.ViewLoader;

public class Main extends Application {
	OraConn oraConn = null;
	private static Stage primaryStage;
	private ObservableList<Employee> employeesDataList;

	@Override
	public void start(Stage primaryStage) {
		oraConn = new OraConn();
		this.primaryStage = primaryStage;
		
		if (oraConn.open("jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf", "xtemp11", "xtemp11") > 0) {
			return;
		}

		ViewLoader<BorderPane, Object> viewLoader = new ViewLoader<BorderPane, Object>("view/Main.fxml");
		BorderPane borderPane = viewLoader.getLayout();

		Scene scene = new Scene(borderPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Pracownicy");
		primaryStage.setOnCloseRequest(e -> oraConn.close());
		primaryStage.show();
		
		
		ViewLoader<AnchorPane, EmployeeController> viewLoaderEmp = new ViewLoader<AnchorPane, EmployeeController>(
				"view/EmployeeData.fxml");
		AnchorPane anchorPaneEmp = viewLoaderEmp.getLayout();
		borderPane.setCenter(anchorPaneEmp);
		
		((MainController) viewLoader.getController()).setStage(primaryStage);
		
		employeesDataList = new EmployeesDAL().getEmployees();
		EmployeeController empControler = viewLoaderEmp.getController();
		empControler.setEmployees(employeesDataList);
		
		
		((MainController) viewLoader.getController()).setStage(primaryStage);
		((MainController) viewLoader.getController()).setEmployeeFXML(viewLoaderEmp);

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void primaryStage_Hiding(WindowEvent e) {
		oraConn.close();
	}
	
	public static boolean showEmployeeEditDialog(Employee employee) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("view/EmployeeEditDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Edycja pracownika");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        EmployeeEditDialog controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setEmployee(employee);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public static boolean showEmployeeAddDialog(Employee employee) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("view/EmployeeEditDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Dodawanie pracownika");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        EmployeeEditDialog controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setEmployee2(employee);

	        // Show the dialog and wait until the user clsoses it
	        dialogStage.showAndWait();

	        return controller.isClicked();
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public static Window getPrimaryStage() {
		return primaryStage;
	}
	
}
