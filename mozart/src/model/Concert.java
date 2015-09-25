package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Concert {		
	public int ConcertID = -1;	
	public int NavigatorID = -1;
	public String  NavigatorType = "";
	public String ConcertName = "";
	public int VenueID = -1;
	public int OverAllRating = -1;
	public String ConcertStartDate = "";
	public String ConcertEndDate = "";
	public String VenureName = "";
	public int TicketAmount = 0;
	public int SoldTickets = 0;
	public String TicketSalesDate = "";
	public String TicketWebLink = "";
	public int VenueCapacity = 0;
	public String VenueAddress = "";
	public String VenueWebLink = "";	
	public int BandId = -1;
	public String BandName = "";
	public String BandWebsite = "";	
	public String NavComment = "";
	public int NavRating = -1;
	public String NavStatus = "None";
	public String NavName = "";
	public int FollowerCount = 0;
	public ArrayList<String> NavFollowerName = new ArrayList<String>();
	public ArrayList<Integer> NavFollowerID = new ArrayList<Integer>();
	
	public class Followers {
		public String UserName = "";
		public int UserID = -1;
		public String Comment = "";
		public int Rating = -1;
		public String Status = "None";
		public Followers(){}
	}
	public ArrayList<Followers> UsersFollowing = new ArrayList<Concert.Followers>();	
	
	public Concert(int concertId, int navigatorID, String navigatorType) {
		this.ConcertID = concertId;
		this.NavigatorID = navigatorID;
		this.NavigatorType = navigatorType;
	}

	public int GetData() {
		if (this.ConcertID != -1) {
			connect C = new connect();
			Statement S = C.getQuery();
			Statement CRS = C.getQuery();			
			try {
				ResultSet Result = null;
				Result = S.executeQuery("SELECT * FROM Concert As C "
						+ "INNER JOIN Venue As V "
						+ "On C.VenueID = V.VenueID "
						+ "WHERE ConcertID = "
						+ this.ConcertID);
				if (Result.next()) {						
					ConcertName = Result.getString("ConcertName");
					VenueID = Result.getInt("VenueID");					
					ConcertStartDate = Result.getDate("ConcertStartDT").toString().substring(0,10);
					ConcertEndDate = Result.getDate("ConcertEndDT").toString().substring(0,10);
					VenureName = Result.getString("VenueName");
					TicketAmount = Result.getInt("TicketAmount");
					SoldTickets = Result.getInt("SoldTickets");
					TicketSalesDate = Result.getDate("TicketSaleStartDT").toString().substring(0,10);
					TicketWebLink = Result.getString("TicketWebLink");
					VenueCapacity = Result.getInt("Capacity");
					VenueAddress = Result.getString("Street") + ", " + Result.getString("City") + ", " + Result.getString("State") + ", " + Result.getString("Country");
					VenueWebLink = Result.getString("VenueWebLink");
					BandId = Result.getInt("BandID");
					ResultSet RatingResult = null;
					RatingResult = CRS.executeQuery("SELECT ROUND(AVG(Rating),0) As Rating FROM Attended WHERE ConcertID =" + ConcertID);
					while (RatingResult.next()) {
						OverAllRating = RatingResult.getInt("Rating");
					}
				}
				Result = null;
				Result = S.executeQuery("SELECT A.UserID, A.Comment, A.Rating, "
						+ "U.UserFName, U.UserLName, A.Status "
						+ "FROM Attended As A INNER JOIN "
						+ "Users As U ON A.UserID = U.UserID "
						+ "WHERE ConcertID = "
						+ this.ConcertID);
				if (Result.next()) {	
					if(Result.getInt("UserID") != NavigatorID){
						Followers F = new Followers();
						F.UserID = Result.getInt("UserID");
						F.UserName = Result.getString("UserFName") + " " + Result.getString("UserLName"); 
						F.Rating = Result.getInt("Rating");
						F.Comment = Result.getString("Comment");
						F.Status = Result.getString("Status");
						UsersFollowing.add(F);
					} else {
						NavComment = Result.getString("Comment");
						NavRating = Result.getInt("Rating");
						NavName = Result.getString("UserFName") + " " + Result.getString("UserLName");
						NavStatus = Result.getString("Status");						
					}	
					FollowerCount++;
				}
				Result = null;
				Result = S.executeQuery("SELECT BandName, BandWebSite From Band WHERE BandID = " + BandId);
				while (Result.next()) {
					BandName = Result.getString("BandName");
					BandWebsite = Result.getString("BandWebSite");
				}
				Result = null;
				Result = S.executeQuery("SELECT F.FollowingID, U.UserFName "
						+ "+ ' ' + u.UserLName As Username "
						+ "FROM Follow As F INNER JOIN "
						+ "Users As U ON F.FollowingID = U.UserID "
						+ "WHERE FollowingType = 'user' "
						+ "AND FollowerType = 'user' "
						+ "AND FollowerID = " + this.NavigatorID );
				while (Result.next()) {
					NavFollowerName.add(Result.getString("Username"));
					NavFollowerID.add(Result.getInt("FollowingID"));
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
	public void doFollow(int rating, String Comment, String status){
		if (this.ConcertID != -1) {
			connect C = new connect();
			Statement S = C.getQuery();
			try {
				S.executeUpdate("IF EXISTS "
						+ "(SELECT UserID FROM Attended WHERE UserID = " + NavigatorID + " AND ConcertID = " + this.ConcertID + ") "
						+ "UPDATE Attended SET Comment = '" + Comment + "', "
						+ "Rating = " + rating + " , "
						+ "Status = '" + status + "', "
						+ "AttendDate = '" + this.ConcertStartDate + "' "
						+ "WHERE UserID = " + NavigatorID + " " 
						+ "AND ConcertID = "
						+ this.ConcertID + " "
						+ "ELSE "
						+ "INSERT INTO Attended "
						+ "(UserID, ConcertID, AttendDate, Rating, Comment, Status) "
						+ "VALUES ( " + NavigatorID + " , " + this.ConcertID + " "
						+ ", '" + this.ConcertStartDate + "', " + rating + " "
						+ ", '" + Comment + "', '" + status + "')");
			} catch (SQLException e) {
				C.closeConnection();				
			}
			C.closeConnection();
		}
	}
	
	public void doRec(String ListName, int Id, String Comment){
		if (this.ConcertID != -1) {
			connect C = new connect();
			Statement S = C.getQuery();
			try {
				S.executeUpdate("INSERT INTO RecommendedConcertList "
						+ "(ListID, ListName, UserID, CreationDT, ConcertID, Comment) "
						+ "VALUES ( (Select Top 1 MAX(ListID) + 1 FROM RecommendedConcertList) , "
						+ "'" + ListName + "' , " + NavigatorID + " , GETDATE(), " + this.ConcertID + " , '" + Comment + "' )");
				S.executeUpdate("INSERT INTO UserRecommendations "
						+ "(FromUserID, ToUserID, ListID, RecommendationDT) "
						+ "VALUES ( " + NavigatorID + " , " + Id + " , "
						+ "(Select Top 1 MAX(ListID) FROM RecommendedConcertList) , GETDATE())");
			} catch (SQLException e) {
				C.closeConnection();				
			}
			C.closeConnection();
		}
	}
}
