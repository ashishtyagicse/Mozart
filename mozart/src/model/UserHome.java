package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserHome {
	public boolean FlagNavigation = false;
	public int NavigationID = -1;
	public String NavigationType = "";
	public boolean NavigatorFollow = false;
	public int UserId = -1;
	public String UserFullName = "";
	public String City = "";
	public String Gender = "";
	public String HomeTown = "";
	public String University = "";
	public String School = "";
	public String Age = "";
	public String MaritalStatus = "";
	public String Address = "";
	public String Phone = "";
	public String InterestedIn = "";
	public String ReligiousView = "";
	public String PoliticalView = "";
	public String MotherTongue = "";
	public String Email = "";	
	public int NumberOfBands = 0;
	public int NoOfFollowing = 0;
	public int NoOfFollowers = 0;
	public boolean UserTrust = false;
	
	public ArrayList<String> Music = new ArrayList<String>();
	public class Follower {
		public String FollowerName = "";
		public int FollowerID = -1;
		public ArrayList<String> FollowersRecentConcerts = new ArrayList<String>();
		public ArrayList<String> FollowersRecentBands = new ArrayList<String>();
		public ArrayList<String> FRConcertsID = new ArrayList<String>();
		public ArrayList<String> FRBandsID = new ArrayList<String>();

		public Follower() {
		}
	}
	public ArrayList<Follower> UserFollowing = new ArrayList<UserHome.Follower>();
	public class Band {
		public String BandName = "";
		public int BandID = -1;
		public ArrayList<String> FBandUpcomingConcert = new ArrayList<String>();
		public ArrayList<String> FBandUConcertDate = new ArrayList<String>();
		public ArrayList<Integer> FBandConcertId = new ArrayList<Integer>();

	}
	public ArrayList<Band> BandFollowing = new ArrayList<UserHome.Band>();
	public class SystemRec {
		public String BandName = "";	
		public int BandId = -1;
		public String ConcertName = "";
		public String ConcertDate = "";
		public int ConcertId = -1;
	}
	public ArrayList<SystemRec> SysRecom = new ArrayList<UserHome.SystemRec>();
	public class UserRec {
		public String UserName = "";		
		public String ConcertName = "";
		public String ConcertDate = "";
		public int ConcertId = -1;
		public String ListName = "";
		public int UserID = -1;
		public String Comment = "";
		public int BandId = -1;
		public String BandName = "";
	}
	public ArrayList<UserRec> UserRecom = new ArrayList<UserHome.UserRec>();
	
	
	public UserHome(int userId, int NavigatorID ,String type, boolean Flag) {
		this.UserId = userId;
		this.NavigationID = NavigatorID;
		this.NavigationType = type;
		this.FlagNavigation = Flag;
	}

	public int GetData() {
		if (this.UserId != -1) {
			connect C = new connect();
			Statement S = C.getQuery();
			Statement US = C.getQuery();
			Statement BS = C.getQuery();
			try {
				ResultSet Result = null;
				Result = S.executeQuery("Select Top 1 UserFName ,"
						+ "UserLName, UserCity, Gender, UserEMail,"
						+ "HomeTown, University, School, Age, "
						+ "Phone, Address, InterestedIN, "
						+ "ReligiousViews, PoliticalViews, MotherTongue, "
						+ "MaritalStatus from Users Where UserID = "
						+ this.UserId);
				if (Result.next()) {					
					this.UserFullName = Result.getString("UserFName") + " "
							+ Result.getString("UserLName");
					this.City = Result.getString("UserCity");
					this.Gender = Result.getString("Gender");
					this.HomeTown = Result.getString("HomeTown");
					this.University = Result.getString("University");
					this.School = Result.getString("School");
					this.Age = String.valueOf(Result.getInt("Age"));
					this.MaritalStatus = Result.getString("MaritalStatus");
					this.Phone = Result.getString("Phone");
					this.Address = Result.getString("Address");
					this.InterestedIn = Result.getString("InterestedIN");
					this.MotherTongue = Result.getString("MotherTongue");
					this.ReligiousView = Result.getString("ReligiousViews");
					this.PoliticalView = Result.getString("PoliticalViews");
					this.Email = Result.getString("UserEMail");
				}
				Result = null;
				Result = S.executeQuery("Select GenreName "
						+ "from Genre where GenreID in ("
						+ "Select Distinct GenreID from GenreUserBandMap "
						+ "Where FollowerType = 'user' AND FollowerID = "
						+ this.UserId + ")");
				while (Result.next()) {
					this.Music.add(Result.getString("GenreName"));
				}
				Result = null;
				Result = S.executeQuery("Select UserFName, UserLName, UserID "
						+ "From Users Where UserID in ("
						+ "Select Distinct FollowingID From Follow "
						+ "Where FollowerType = 'user' AND FollowingType = 'user' "
						+ "AND FollowerID ="
						+ this.UserId + " )");
				while (Result.next()) {
					Follower F = new Follower();
					int FID = Result.getInt("UserID");
					ResultSet FResult = null;
					F.FollowerName = Result.getString("UserFName") + " "
							+ Result.getString("UserLName");
					F.FollowerID = FID;
					FResult = US
							.executeQuery("Select Distinct Top 3 C.ConcertID, A.Status, A.Rating "
									+ "From Attended As A Inner Join Concert As C "
									+ "On A.ConcertID = C.ConcertID "
									+ "Where A.UserID = "
									+ FID
									+ " Order By A.Status, A.Rating Desc");
					String ConcertID = "";
					while (FResult.next()) {
						ConcertID += "," + FResult.getInt("ConcertID");
					}
					if (!ConcertID.isEmpty()) {
						ConcertID = ConcertID.substring(1);
						FResult = null;
						FResult = US
								.executeQuery("Select ConcertName, ConcertID From Concert Where ConcertID IN ("
										+ ConcertID + ")");
						while (FResult.next()) {
							F.FollowersRecentConcerts.add(FResult
									.getString("ConcertName"));
							F.FRConcertsID.add(FResult.getString("ConcertID"));
						}
					}
					FResult = null;
					FResult = US
							.executeQuery("Select Distinct Top 3 FollowingID, FollowStartDate "
									+ "From Follow Where FollowerType = 'user' "
									+ "AND FollowingType = 'band' AND FollowerID = "
									+ FID
									+ " order By FollowStartDate");
					String BandID = "";
					while (FResult.next()) {
						BandID += "," + FResult.getInt("FollowingID");
					}
					if (!BandID.isEmpty()) {
						BandID = BandID.substring(1);
						FResult = null;
						FResult = US
								.executeQuery("Select BandName, BandID From Band Where BandID IN ("
										+ BandID + ")");
						while (FResult.next()) {
							F.FollowersRecentBands.add(FResult
									.getString("BandName"));
							F.FRBandsID.add(FResult.getString("BandID"));
						}
					}
					this.UserFollowing.add(F);
				}				
				Result = null;
				Result = S.executeQuery("Select BandName, BandID "
						+ "From Band Where BandID IN ( "
						+ "Select FollowingID From Follow "
						+ "Where FollowerType = 'user' AND FollowingType = 'band' and FollowerID = "
						+ this.UserId + " )");
				while (Result.next()) {
					Band B = new Band();
					int FBandID = Result.getInt("BandID");
					B.BandName = Result.getString("BandName");
					B.BandID = FBandID;
					ResultSet FBResult = null;
					FBResult = BS
							.executeQuery("Select Top 3 ConcertID, ConcertStartDT, ConcertName "
									+ "From Concert Where ConcertStartDT > GETDATE() "
									+ "AND BandID = "
									+ FBandID
									+ " Order By ConcertStartDT ");
					while (FBResult.next()) {
						B.FBandUpcomingConcert.add(FBResult
								.getString("ConcertName"));
						B.FBandUConcertDate.add(FBResult.getDate(
								"ConcertStartDT").toString());
						B.FBandConcertId.add(FBResult.getInt("ConcertID"));
					}
					this.BandFollowing.add(B);
				}
				this.NumberOfBands = this.BandFollowing.size();
				
				Result = null;
				Result = S.executeQuery("exec SystemRecom " + this.NavigationID + " , " + "\"user\"" );
				while (Result.next()) {
					SystemRec R = new SystemRec();
					R.BandName = Result.getString("BandName");
					R.ConcertName = Result.getString("ConcertName");
					R.ConcertDate = Result.getDate("ConcertStartDT").toString();
					R.ConcertId = Result.getInt("ConcertID");
					R.BandId = Result.getInt("BandID");
					this.SysRecom.add(R);
				}
				
				Result = null;
				Result = S.executeQuery("SELECT R.ListID, R.ListName, R.UserID, R.Comment, R.ConcertID, B.BandID, B.BandName, " 
						+ "C.ConcertName, C.ConcertStartDT, U.UserFName, U.UserLName FROM "
						+ "RecommendedConcertList AS R INNER JOIN Users AS U ON U.UserID = R.UserID "
						+ "INNER JOIN Concert AS C ON C.ConcertID = R.ConcertID "
						+ "INNER JOIN Band As B ON C.BandID = B.BandID "
						+ "WHERE R.ListID IN ( "
						+ "SELECT ListID FROM UserRecommendations WHERE ToUserID = " + this.NavigationID + " AND FromUserID <>  " + this.NavigationID + " ) "
						+ "AND C.ConcertStartDT > GETDATE() "
						+ "ORDER BY C.ConcertStartDT");
				while (Result.next()) {
					UserRec R = new UserRec();
					R.ListName = Result.getString("ListName");
					R.UserName = Result.getString("UserFName") + " " + Result.getString("UserLName");
					R.ConcertName = Result.getString("ConcertName");
					R.ConcertDate = Result.getString("ConcertStartDT");
					R.ConcertId  = Result.getInt("ConcertID");
					R.Comment = Result.getString("Comment");
					R.UserID = Result.getInt("UserID");
					R.BandId = Result.getInt("BandID");
					R.BandName = Result.getString("BandName");
					this.UserRecom.add(R);
				}
				Result = null;
				Result = S.executeQuery("SELECT count(*) As C FROM Follow WHERE FollowingType = 'user' AND FollowingID = " + this.UserId);
				while (Result.next()) {
					this.NoOfFollowers = Result.getInt("C");
				}
				Result = null;
				Result = S.executeQuery("SELECT count(*) As C FROM Follow "
						+ "WHERE FollowerType = 'user' "
						+ "AND FollowingType <> 'band' "
						+ "AND FollowerID = " + this.UserId);
				while (Result.next()) {
					this.NoOfFollowing = Result.getInt("C");
				}
				if (FlagNavigation) {
					Result = null;
					Result = S.executeQuery("IF EXISTS "
							+ "(SELECT * FROM Follow WHERE "
							+ "FollowerType = '" + this.NavigationType + "' "
							+ "AND FollowingType = 'user' "
							+ "AND FollowerID = " + this.NavigationID + " "
							+ "AND FollowingID = " + this.UserId + ") "
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
				Result = null;
				Result = S.executeQuery("SELECT Score From Users Where UserID = " + UserId);
				while (Result.next()) {
					if(Result.getInt("Score") > 6){
						UserTrust = true;
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
		if (this.UserId != -1) {
			connect C = new connect();
			Statement S = C.getQuery();
			try {
				S.executeUpdate("INSERT INTO Follow (FollowerType, "
						+ "FollowingType, FollowerID, FollowingID, FollowStartDate) "
						+ "VALUES (" + " '" + this.NavigationType 
						+ "', 'user' , " + this.NavigationID + " , " + this.UserId +" , GETDATE())");
			} catch (SQLException e) {
				C.closeConnection();				
			}
			C.closeConnection();
		}
	}
}
