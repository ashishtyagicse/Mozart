package model;

import java.sql.*;

public class Login {
	private String UserName = "";
	private String Password = "";
	public int UserId = -1;
	public String AccountType = "";
	
	public Login(String userName, String password) {		
		UserName = userName;
		Password = password;
	}
	
	public int DoLogin(boolean LoginFlag) {
		if (this.UserName != "" && this.Password != "") {
			connect C = new connect();
			Statement S = C.getQuery();
			try {
				ResultSet Result = null;
				if (LoginFlag) {
					Result = S.executeQuery("Select Top 1 LoginID, LogType from Logins Where LoginName = '" + UserName + "' And Passwords = '" + Password + "'");								
					if (Result.next()) {
						UserId = Result.getInt("LoginID");
						AccountType = Result.getString("LogType");
						S.executeUpdate("Update Logins Set LastAccess = GETDATE() Where LoginName = '" + UserName + "'");					
					} else {
						C.closeConnection();
						return 0;
					}	
				} else {
					Result = S.executeQuery("Select Top 1 LoginID from Logins Where LoginName = '" + UserName + "'");				
					if (Result.next()) {						
						C.closeConnection();
						return 0;
					}
				}				
			} catch (SQLException e) {
				C.closeConnection();
				return 0;
			}			
			C.closeConnection();
			return 1;
		} else {			
			return 0;
		}
	}	
}
