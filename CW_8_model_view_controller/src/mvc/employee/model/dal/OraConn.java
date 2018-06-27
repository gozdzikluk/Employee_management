package mvc.employee.model.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OraConn {

	
	private String userName;
	private String userPassword;
	private String url;
	
	private static Connection connection = null;

	public OraConn() {
	}

	public static Connection getConnection() {
		return connection;
	}

	private int err = 0;

	public int getErr() {
		return err;
	}

	private String errMsg = "";

	public String getErrMsg() {
		return errMsg;
	}


	public int open(String url, String usr, String pass) {

		this.url = url;
		this.userName = usr;
		this.userPassword = pass;
		// utworzenie polaczenia
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("poprawnie zaladowany");
		} catch (ClassNotFoundException e) {
			System.out.println("błąd ładowania sterownika");
		}
		
		try {
			connection = DriverManager.getConnection(
					this.url, this.userName, this.userPassword);
			errMsg = "";
			System.out.println(connection);
			return err = 0;
		} catch (SQLException exception) {
			connection = null;
			errMsg = exception.getMessage();
			return err = 2;
		}
	}

	public int close() {
		// sprawdzenie poprawnosci polaczenia, zamkniecie polaczenia
		try {
			if (connection != null) {
				System.out.println("wykonano poprawne polaczenie: " + connection);
				connection.close();
			}
		} catch (SQLException ex) {
			connection = null;
			errMsg = ex.getMessage();
			return err = 1;
		}
		return err;
	}

//	public static boolean setAutoCommit(boolean isAutoCommit) {
//		try {
//			connection.setAutoCommit(isAutoCommit);
//
//		} catch (SQLException e) {
//			return false;
//		}
//		return true;
//	}

}
