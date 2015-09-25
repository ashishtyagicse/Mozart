package model;

import java.sql.CallableStatement;

import java.sql.SQLException;

import java.util.Date;

public class Signup {

	private String UserFName = "";
	private String UserLName = "";
	private String UserDOB = "";
	private String UserEMail = "";
	private String UserCity = "";
	private String LoginName = "";
	private String Password = "";

	public Signup(String userfname, String userlname, String dob,
			String useremail, String usercity, String loginname, String password) {
		UserFName = userfname;
		UserLName = userlname;
		UserEMail = useremail;
		UserCity = usercity;
		LoginName = loginname;
		Password = password;
		UserDOB = dob;
	}

	public int CreateUser() {
		if (this.UserFName != "" && this.UserLName != ""
				&& this.UserEMail != "" && this.UserCity != ""
				&& this.LoginName != "" && this.Password != ""
				&& this.UserDOB != null) {
			connect C = new connect();
			CallableStatement callableStatement = null;
			String insertStoreProc = "{call UsersSignUp(?,?,?,?,?,?,?,?)}";
			try {				
				callableStatement = C.DatabaseConnection
						.prepareCall(insertStoreProc);
				callableStatement.setString(1, UserFName);
				callableStatement.setString(2, UserLName);
				callableStatement.setDate(3, java.sql.Date.valueOf(UserDOB.toString()));
				callableStatement.setString(4, UserEMail);
				callableStatement.setString(5, UserCity);
				callableStatement.setString(6, null);
				callableStatement.setString(7, LoginName);
				callableStatement.setString(8, Password);
				callableStatement.executeUpdate();
			} catch (SQLException e) {
				C.closeConnection();
			} finally {
				if (callableStatement != null) {
					try {
						callableStatement.close();
					} catch (SQLException e) {
						C.closeConnection();
					}
				}
				if (C.DatabaseConnection != null) {
					try {
						C.DatabaseConnection.close();
					} catch (SQLException e) {
						C.closeConnection();
					}
				}
			}
			C.closeConnection();
			return 1;
		} else {
			return 0;
		}
	}
}
