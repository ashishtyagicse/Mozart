package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BandHome {
	public boolean FlagNavigation = false;
	public int NavigationID = -1;
	public String NavigationType = "";
	public boolean NavigatorFollow = false;
	public int Followers = 0;	
	public int BandID = -1;
	public String BandName = "";
	public String BandBiodata = "";
	public String BandCreationDate = "";
	public String BandWebsite = "";
	public String Address = "";
	public String Phone = "";
	public String Email = "";	
	public ArrayList<String> Music = new ArrayList<String>();
	public ArrayList<String> Artist = new ArrayList<String>();
	public ArrayList<Integer> ArtistID = new ArrayList<Integer>();
	
	public class Concerts {
		public String ConcertName = "";
		public int ConcertID = -1;
		public int VenueID = -1;
		public int OverAllRating = 0;
		public String ConcertStartDate = "";
		public String ConcertEndDate = "";
		public String VenureName = "";
		public Concerts() {}
	}
	public ArrayList<Concerts> UpcomingConcerts = new ArrayList<BandHome.Concerts>();
	public ArrayList<Concerts> PastConcerts = new ArrayList<BandHome.Concerts>();
	
	public class Followers {
		public String UserName = "";
		public int UserID = -1;
		public Followers(){}
	}
	public ArrayList<Followers> UsersFollowing = new ArrayList<BandHome.Followers>();
	public ArrayList<Followers> ArtistFollowing = new ArrayList<BandHome.Followers>();
	
	
	
	public BandHome(int bandId, int NavigatorID ,String type, boolean Flag) {
		this.BandID = bandId;
		this.NavigationID = NavigatorID;
		this.NavigationType = type;
		this.FlagNavigation = Flag;
	}

	public int GetData() {
		if (this.BandID != -1) {
			connect C = new connect();
			Statement S = C.getQuery();
			Statement CRS = C.getQuery();			
			try {
				ResultSet Result = null;
				Result = S.executeQuery("SELECT BandName, BandBioData, "
						+ "BandCreateDate, BandWebSite, Phone, "
						+ "Address, Email FROM Band WHERE BandID = "
						+ this.BandID);
				if (Result.next()) {					
					this.BandName = Result.getString("BandName");
					this.BandBiodata = Result.getString("BandBioData");
					this.BandCreationDate = Result.getString("BandCreateDate");
					this.BandWebsite = Result.getString("BandWebSite");
					this.Phone = Result.getString("Phone");
					this.Address = Result.getString("Address");
					this.Email = Result.getString("Email");
				}
				Result = null;
				Result = S.executeQuery("Select GenreName "
						+ "from Genre where GenreID in ("
						+ "Select Distinct GenreID from GenreUserBandMap "
						+ "Where FollowerType = 'band' AND FollowerID = "
						+ this.BandID + ")");
				while (Result.next()) {
					this.Music.add(Result.getString("GenreName"));
				}
				Result = null;
				Result = S.executeQuery("SELECT ArtistID, ArtistFName, "
						+ "ArtistLName FROM Artist WHERE BandID ="
						+ this.BandID);
				while (Result.next()) {
					this.Artist.add(Result.getString("ArtistFName") + " " + Result.getString("ArtistLName"));
					this.ArtistID.add(Result.getInt("ArtistID"));
				}				
				Result = null;
				Result = S.executeQuery("SELECT C.ConcertName, C.ConcertID, "
						+ "C.ConcertStartDT, C.ConcertEndDT, V.VenueName, C.VenueID "
						+ "FROM Venue As V INNER JOIN Concert As C "
						+ "ON V.VenueID = C.VenueID "
						+ "WHERE C.ConcertStartDT > GETDATE() "
						+ "AND C.BandID = "	+ this.BandID);
				while (Result.next()) {
					Concerts upcomingconcerts = new Concerts();
					int UCID = Result.getInt("ConcertID");
					upcomingconcerts.ConcertName = Result.getString("ConcertName");
					upcomingconcerts.ConcertStartDate = Result.getDate("ConcertStartDT").toString().substring(0,10);
					upcomingconcerts.ConcertEndDate = Result.getDate("ConcertEndDT").toString().substring(0,10);
					upcomingconcerts.ConcertID = UCID;
					upcomingconcerts.VenueID = Result.getInt("VenueID");
					upcomingconcerts.VenureName = Result.getString("VenueName");
					this.UpcomingConcerts.add(upcomingconcerts);
				}
				Result = null;
				Result = S.executeQuery("SELECT C.ConcertName, C.ConcertID, "
						+ "C.ConcertStartDT, C.ConcertEndDT, V.VenueName, C.VenueID "
						+ "FROM Venue As V INNER JOIN Concert As C "
						+ "ON V.VenueID = C.VenueID "
						+ "WHERE C.ConcertStartDT <= GETDATE() "
						+ "AND C.BandID = "	+ this.BandID );
				while (Result.next()) {
					Concerts pastconcers = new Concerts();
					int UCID = Result.getInt("ConcertID");
					pastconcers.ConcertName = Result.getString("ConcertName");
					pastconcers.ConcertStartDate = Result.getDate("ConcertStartDT").toString().substring(0,10);
					pastconcers.ConcertEndDate = Result.getDate("ConcertEndDT").toString().substring(0,10);
					pastconcers.ConcertID = UCID;
					pastconcers.VenueID = Result.getInt("VenueID");
					pastconcers.VenureName = Result.getString("VenueName");					
					ResultSet RatingResult = null;
					RatingResult = CRS.executeQuery("SELECT ROUND(AVG(Rating),0) As Rating FROM Attended WHERE ConcertID =" + UCID);
					while (RatingResult.next()) {
						pastconcers.OverAllRating = RatingResult.getInt("Rating");
					}
					this.PastConcerts.add(pastconcers);
				}
				Result = null;
				Result = S.executeQuery("SELECT F.FollowerID, U.UserFName, U.UserLName "
						+ "FROM Follow As F INNER JOIN Users As U "
						+ "ON F.FollowerID = U.UserID "
						+ "WHERE FollowerType = 'user' "
						+ "AND FollowingType = 'band' "
						+ "AND FollowingID = " + this.BandID);
				while (Result.next()) {
					Followers U = new Followers();
					U.UserName = Result.getString("UserFName") + " " + Result.getString("UserLName");
					U.UserID = Result.getInt("FollowerID");
					this.UsersFollowing.add(U);
				}
				if(this.UsersFollowing != null){
					this.Followers += this.UsersFollowing.size(); 
				}
				Result = null;
				Result = S.executeQuery("SELECT F.FollowerID, U.ArtistFName, U.ArtistLName "
						+ "FROM Follow As F INNER JOIN Artist As U "
						+ "ON F.FollowerID = U.ArtistID "
						+ "WHERE FollowerType = 'artist' "
						+ "AND FollowingType = 'band' "
						+ "AND FollowingID = " + this.BandID);
				while (Result.next()) {
					Followers U = new Followers();
					U.UserName = Result.getString("ArtistFName") + " " + Result.getString("ArtistLName");
					U.UserID = Result.getInt("FollowerID");
					this.ArtistFollowing.add(U);
				}
				if(this.ArtistFollowing != null){
					this.Followers += this.ArtistFollowing.size(); 
				}
				if (FlagNavigation) {
					Result = null;
					Result = S.executeQuery("IF EXISTS "
							+ "(SELECT * FROM Follow WHERE "
							+ "FollowerType = '" + this.NavigationType + "' "
							+ "AND FollowingType = 'band' "
							+ "AND FollowerID = " + this.NavigationID + " "
							+ "AND FollowingID = " + this.BandID + ") "
							+ "SELECT 1 AS R "
							+ "ELSE SELECT 0 AS R ");
					while (Result.next()) {
						if(Result.getInt("R") == 1){
							this.NavigatorFollow = true;
						} else {
							this.NavigatorFollow = false;
						}
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
	public void doFollow(){
		if (this.BandID != -1) {
			connect C = new connect();
			Statement S = C.getQuery();
			try {
				S.executeUpdate("INSERT INTO Follow (FollowerType, "
						+ "FollowingType, FollowerID, FollowingID, FollowStartDate) "
						+ "VALUES (" + " '" + this.NavigationType 
						+ "', 'band' , " + this.NavigationID + " , " + this.BandID +" , GETDATE())");
			} catch (SQLException e) {
				C.closeConnection();				
			}
			C.closeConnection();
		}
	}
}
