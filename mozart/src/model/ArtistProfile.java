package model;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
public class ArtistProfile {
	private String UserFName = "";
	private String UserLName = "";
	private String UserDOB = "";
	private String UserEMail = "";
	private String UserCity = "";
	private String LoginName = "";
	private String Password = "";
	private String marital = "";
	private String Phone = "";
	private String Address = "";
	private String Gender = "";
	private String InterestedIn = "";
	private String ReligiousViews = "";
	private String PoliticalViews = "";
	private String MotherTongue = "";
	private String University = "";
	private String School = "";
	private String Bio = "";
	String[] Music = null; 
	int ArtistId = -1;

	public ArtistProfile(String userfname, String userlname, String dob,
			String useremail, String usercity, String loginname, String password,String marital, String Phone, String Address,
			String Gender, String InterestedIn, String ReligiousViews, String PoliticalViews,String MotherTongue, String University, String School,String Bio, String[] music, int artistID) {
		UserFName = userfname;
		UserLName = userlname;
		UserEMail = useremail;
		UserCity = usercity;
		LoginName = loginname;
		Password = password;
		UserDOB = dob;
		this.marital=marital;
		this.Phone=Phone;
		this.Address=Address;
		this.Gender=Gender;
		this.InterestedIn=InterestedIn;
		this.ReligiousViews=ReligiousViews;
		this.PoliticalViews=PoliticalViews;
		this.MotherTongue=MotherTongue;
		this.University=University;
		this.School=School;
		this.Bio=Bio;
		Music = music;
		ArtistId = artistID;
	}

	public int DoLogin() {
		
			connect C = new connect();
			CallableStatement callableStatement = null;

			String insertStoreProc = "{call ArtistEditProfile(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

			try {
				// dbConnection = getDBConnection();
				callableStatement = C.DatabaseConnection
						.prepareCall(insertStoreProc);

				callableStatement.setString(1, UserFName);
				callableStatement.setString(2, UserLName);
				callableStatement.setDate(3, java.sql.Date.valueOf(UserDOB.toString()));
				callableStatement.setString(4, UserEMail);
				callableStatement.setString(5, UserCity);
				callableStatement.setString(6, marital);
				callableStatement.setInt(7, Integer.parseInt(Phone));
				callableStatement.setString(8, Address);
				callableStatement.setString(9, Gender);				
				callableStatement.setString(10, InterestedIn);
				callableStatement.setString(11, ReligiousViews);
				callableStatement.setString(12, PoliticalViews);
				callableStatement.setString(13, MotherTongue);
				callableStatement.setString(14, University);
				callableStatement.setString(15, School);
				callableStatement.setString(16, Bio);

				// execute insertDBUSER store procedure
				callableStatement.executeUpdate();
				Statement S = C.getQuery();
				if(Music != null && Music.length > 0){
					S.executeUpdate("Delete GenreUserBandMap where FollowerType = 'artist' AND FollowerID = " + ArtistId);
					for (String string : Music) {
						S.executeUpdate("INSERT INTO GenreUserBandMap values ( " + ArtistId + " , " + string + " ,'artist')");	
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
