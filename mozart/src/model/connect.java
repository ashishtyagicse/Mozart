package model;

import java.sql.*;

public class connect {
	public Connection DatabaseConnection = null;
	public Statement Query = null;
	public int ErrorFlag = 0;
	private static final String JDBCConnection = "jdbc:sqlserver://localhost:1433;databaseName=Mozart";
	private static final String DatabaseLoginUser = "Krishna";
	private static final String DatabaseLoginPassword = "qwerty";

	public connect() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			DatabaseConnection = DriverManager.getConnection(JDBCConnection,
					DatabaseLoginUser, DatabaseLoginPassword);			
			Query = DatabaseConnection.createStatement();
		} catch (Exception e) {
			ErrorFlag = 1;			
		}
	}
	
	public Statement getQuery(){
		try {
			return DatabaseConnection.createStatement();
		} catch (SQLException e) {
			return null;
		}
	}

	public void closeConnection() {
		try {
			Query.close();
			DatabaseConnection.close();
		} catch (SQLException e) {
			ErrorFlag = 1;
		}

	}
}
