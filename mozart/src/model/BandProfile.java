package model;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import model.search.Band;
public class BandProfile {
	private String BandName = "";
	private String BandBiodata = "";
	private String BandCreateDate = "";
	private String EMail = "";
	private String Phone= "";
	private String Address = "";
	private String LoginName = "";
	private String Password = "";
	String[] Music = null; 
	int BandId = -1;

	public BandProfile(String userfname, String userlname, String dob,
			String useremail, String usercity, String loginname, String password,String Phone, String[] music, int bandID) {
		BandName = userfname;
		BandBiodata = userlname;
		EMail = useremail;
		Address = usercity;
		LoginName = loginname;
		Password = password;
		BandCreateDate = dob;
		Phone=Phone;
		Music = music;
		BandId = bandID;
	}

	public int DoLogin() {
		
			connect C = new connect();
			CallableStatement callableStatement = null;

			String insertStoreProc = "{call BandEditProfile(?,?,?,?,?,?,?)}";

			try {
				// dbConnection = getDBConnection();
				callableStatement = C.DatabaseConnection.prepareCall(insertStoreProc);

				callableStatement.setString(1, BandName);
				callableStatement.setString(2, BandBiodata);
				callableStatement.setDate(3, java.sql.Date.valueOf(BandCreateDate.toString()));
				callableStatement.setString(4, null);
				callableStatement.setString(5, this.Phone);
				callableStatement.setString(6, Address);
				callableStatement.setString(7, EMail);								
				// execute insertDBUSER store procedure
				callableStatement.executeUpdate();
				
				Statement S = C.getQuery();
				if(Music != null && Music.length > 0){
				S.executeUpdate("Delete GenreUserBandMap where FollowerType = 'band' AND FollowerID = " + BandId);
				for (String string : Music) {
					S.executeUpdate("INSERT INTO GenreUserBandMap values ( " + BandId + " , " + string + " ,'band')");	
				}
				}
				System.out.println("Record is inserted into DBUSER table!");

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
		
	}
}
