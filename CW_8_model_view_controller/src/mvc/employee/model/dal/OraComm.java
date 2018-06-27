package mvc.employee.model.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OraComm {
	
	private Connection connection = null;
	
	public OraComm(){
		testConnection();
	}
	
	public void testConnection(){
		registerDriver();
		open();
	}
	
	public void registerDriver(){
		//rejestracja sterownika
		try{
			Class.forName("ojdbc6");
		}catch(ClassNotFoundException e){	}
		
		System.out.println("Sterownik poprawnie zarejestrowany");
		//MessageBox.showAndWait(AlertType.INFORMATION, "Poprawnie zarejestrowano sterownik JDBC");
	}
	
	public void open(){
		//utworzenie polaczenia
		try{
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf",
					"xtemp11", "xtemp11");
		}
		catch(SQLException exception){
			System.out.println("Blad polaczenia testowego");
			//MessageBox.showAndWait(AlertType.ERROR, "Błąd połączenia!\n"+
			//						exception.getMessage());
			return;
		}
	}
	
	public void close(){
		//sprawdzenie poprawnosci polaczenia, zamkniecie polaczenia 
		try{
			if(connection != null){
				System.out.println("wykonano poprawne polaczenie: "+ connection);
				//MessageBox.showAndWait(AlertType.INFORMATION, "Wykonano poprawne połączenie:\n"
				//												+connection);
				connection.close();
			}else{
				System.out.println("Brak polaczenia");
				//MessageBox.showAndWait(AlertType.ERROR, "Błąd połączenia");
			}
		}catch(SQLException ex){
			System.out.println("Nie udalo sie zakonczyc polaczenia");
			//MessageBox.showAndWait(AlertType.ERROR, "Błąd zamkniecia połączenia! \n"+ex.getMessage());
		}
	}
	
	
	
	public Connection getConnection(){
		return connection;
	}
	
}
