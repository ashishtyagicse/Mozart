package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import model.BandHome.Concerts;

public class search {
	private String NavigationType = "";
	public int UserId = -1;	
	public String Key = "";
	
	public ArrayList<String> Music = new ArrayList<String>();
	public class Users {
		public String FollowerName = "";
		public int FollowerID = -1;
		public ArrayList<String> FollowersRecentConcerts = new ArrayList<String>();
		public ArrayList<String> FollowersRecentBands = new ArrayList<String>();
		public ArrayList<String> FRConcertsID = new ArrayList<String>();
		public ArrayList<String> FRBandsID = new ArrayList<String>();

		public Users() {
		}
	}
	public ArrayList<Users> UserSearched = new ArrayList<search.Users>();
	public ArrayList<Users> ArtistSearched = new ArrayList<search.Users>();
	
	public class Band {
		public String BandName = "";
		public int BandID = -1;
		public ArrayList<String> FBandUpcomingConcert = new ArrayList<String>();
		public ArrayList<String> FBandUConcertDate = new ArrayList<String>();
		public ArrayList<Integer> FBandConcertId = new ArrayList<Integer>();

	}
	public ArrayList<Band> BandsSearched = new ArrayList<search.Band>();
	
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
	public ArrayList<Concerts> ConcertsSearched = new ArrayList<search.Concerts>();
	
	
	public class SystemRec {
		public String BandName = "";	
		public int BandId = -1;
		public String ConcertName = "";
		public String ConcertDate = "";
		public int ConcertId = -1;
	}
	public ArrayList<SystemRec> SysRecom = new ArrayList<search.SystemRec>();
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
	public ArrayList<UserRec> UserRecom = new ArrayList<search.UserRec>();
	
	
	public search(int userId ,String type, String key) {
		this.UserId = userId;
		this.NavigationType = type;
		this.Key = key;
	}

	public int GetData() {
		if (this.UserId != -1) {
			connect C = new connect();
			Statement S = C.getQuery();
			Statement US = C.getQuery();
			Statement BS = C.getQuery();
			try {
				ResultSet Result = null;
				//User known
				ArrayList<Users> Known = new ArrayList<search.Users>();
				Result = S.executeQuery("Select UserFName, UserLName, UserID "
						+ "From Users Where UserID in ("
						+ "SELECT DISTINCT FollowingID AS ID "
						+ "FROM Follow WHERE FollowerType = '" + this.NavigationType + "' "
						+ "AND FollowingType = 'user' AND FollowerID = " + this.UserId + " "
						+ "UNION "
						+ "SELECT DISTINCT FollowerID AS ID	"
						+ "FROM Follow	WHERE FollowerType = 'user'	"
						+ "AND FollowingType = '" + this.NavigationType + "' " 
						+ "AND FollowingID = " + this.UserId + " "						
						+ ") AND ( UserFName LIKE '%" + this.Key + "%' OR UserLName LIKE '%" + this.Key + "%')");
				while (Result.next()) {
					Users F = new Users();
					int FID = Result.getInt("UserID");
					ResultSet FResult = null;
					F.FollowerName = Result.getString("UserFName") + " "
							+ Result.getString("UserLName");
					F.FollowerID = FID;
					FResult = US
							.executeQuery("Select Distinct Top 1 C.ConcertID, A.Status, A.Rating "
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
							.executeQuery("Select Distinct Top 1 FollowingID, FollowStartDate "
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
					Known.add(F);
				}			
				//Users unknown
				Result = null;
				ArrayList<Users> UnKnown = new ArrayList<search.Users>();
				Result = S.executeQuery("Select Top 20 UserFName, UserLName, UserID "
						+ "From Users Where ( UserFName LIKE '%" + this.Key + "%' OR UserLName LIKE '%" + this.Key + "%')");
				while (Result.next()) {
					Users F = new Users();
					int FID = Result.getInt("UserID");
					ResultSet FResult = null;
					F.FollowerName = Result.getString("UserFName") + " "
							+ Result.getString("UserLName");
					F.FollowerID = FID;
					FResult = US
							.executeQuery("Select Distinct Top 1 C.ConcertID, A.Status, A.Rating "
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
							.executeQuery("Select Distinct Top 1 FollowingID, FollowStartDate "
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
					UnKnown.add(F);
				}				
				ArrayList<Users> TempList = new ArrayList<search.Users>();
				TempList.addAll(Known);
				TempList.addAll(UnKnown);
				Set<Integer> titles = new HashSet<Integer>();
				for( Users U : TempList ) {
				    if(titles.add(U.FollowerID)) {
				        this.UserSearched.add(U);
				    }
				}
				
				
				//Artist known
				Result = null;
				ArrayList<Users> ArtistKnown = new ArrayList<search.Users>();
				Result = S.executeQuery("Select ArtistFName, ArtistLName, ArtistID "
						+ "From Artist Where ArtistID in ("
						+ "SELECT DISTINCT FollowingID AS ID "
						+ "FROM Follow WHERE FollowerType = '" + this.NavigationType + "' "
						+ "AND FollowingType = 'user' AND FollowerID = " + this.UserId + " "
						+ "UNION "
						+ "SELECT DISTINCT FollowerID AS ID	"
						+ "FROM Follow	WHERE FollowerType = 'artist'	"
						+ "AND FollowingType = '" + this.NavigationType + "' " 
						+ "AND FollowingID = " + this.UserId + " "						
						+ ") AND ( ArtistFName LIKE '%" + this.Key + "%' OR ArtistLName LIKE '%" + this.Key + "%')");
				while (Result.next()) {
					Users F = new Users();
					int FID = Result.getInt("ArtistID");
					ResultSet FResult = null;
					F.FollowerName = Result.getString("ArtistFName") + " "
							+ Result.getString("ArtistLName");
					F.FollowerID = FID;
					FResult = null;
					FResult = US
							.executeQuery("Select Distinct Top 1 FollowingID, FollowStartDate "
									+ "From Follow Where FollowerType = 'artist' "
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
					ArtistKnown.add(F);
				}	
				// Artist unknown
				Result = null;
				ArrayList<Users> ArtistUnKnown = new ArrayList<search.Users>();
				Result = S.executeQuery("Select Top 20 ArtistFName, ArtistLName, ArtistID "
						+ "From Artist Where ( ArtistFName LIKE '%" + this.Key + "%' OR ArtistLName LIKE '%" + this.Key + "%')");
				while (Result.next()) {
					Users F = new Users();
					int FID = Result.getInt("ArtistID");
					ResultSet FResult = null;
					F.FollowerName = Result.getString("ArtistFName") + " "
							+ Result.getString("ArtistLName");
					F.FollowerID = FID;					
					FResult = null;
					FResult = US
							.executeQuery("Select Distinct Top 1 FollowingID, FollowStartDate "
									+ "From Follow Where FollowerType = 'artist' "
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
					ArtistUnKnown.add(F);
				}				
				ArrayList<Users> ArtistTempList = new ArrayList<search.Users>();
				ArtistTempList.addAll(ArtistKnown);
				ArtistTempList.addAll(ArtistUnKnown);
				Set<Integer> Artisttitles = new HashSet<Integer>();
				for( Users U : ArtistTempList ) {
				    if(Artisttitles.add(U.FollowerID)) {
				        this.ArtistSearched.add(U);
				    }
				}
								
				//Bands known
				ArrayList<Band> BandsKnown = new ArrayList<search.Band>();
				Result = null;
				Result = S.executeQuery("Select BandName, BandID "
						+ "From Band Where BandID IN ( "
						+ "Select FollowingID From Follow "
						+ "Where FollowerType = '" + this.NavigationType + "' AND FollowingType = 'band' and FollowerID = "
						+ this.UserId + " ) AND ( BandName LIKE '%" + this.Key + "%' OR BandBioData LIKE '%" + this.Key + "%' ) ");
				while (Result.next()) {
					Band B = new Band();
					int FBandID = Result.getInt("BandID");
					B.BandName = Result.getString("BandName");
					B.BandID = FBandID;
					ResultSet FBResult = null;
					FBResult = BS
							.executeQuery("Select Top 1 ConcertID, ConcertStartDT, ConcertName "
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
					BandsKnown.add(B);
				}
				//Band unknown
				ArrayList<Band> BandsUnKnown = new ArrayList<search.Band>();
				Result = null;
				Result = S.executeQuery("Select BandName, BandID "
						+ "From Band Where ( BandName LIKE '%" + this.Key + "%' OR BandBioData LIKE '%" + this.Key + "%' ) ");
				while (Result.next()) {
					Band B = new Band();
					int FBandID = Result.getInt("BandID");
					B.BandName = Result.getString("BandName");
					B.BandID = FBandID;
					ResultSet FBResult = null;
					FBResult = BS
							.executeQuery("Select Top 1 ConcertID, ConcertStartDT, ConcertName "
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
					BandsUnKnown.add(B);
				}				
				ArrayList<Band> BandsTempList = new ArrayList<search.Band>();
				BandsTempList.addAll(BandsKnown);
				BandsTempList.addAll(BandsUnKnown);
				Set<Integer> Bandstitles = new HashSet<Integer>();
				for( Band U : BandsTempList ) {
				    if(Bandstitles.add(U.BandID)) {
				        this.BandsSearched.add(U);
				    }
				}
							
				//concerts upcoming
				ArrayList<Concerts> UpcomingConcerts = new ArrayList<search.Concerts>();
				ArrayList<Concerts> PastConcerts = new ArrayList<search.Concerts>();
				Result = null;
				Result = S.executeQuery("SELECT C.ConcertName, C.ConcertID, "
						+ "C.ConcertStartDT, C.ConcertEndDT, V.VenueName, C.VenueID "
						+ "FROM Venue As V INNER JOIN Concert As C "
						+ "ON V.VenueID = C.VenueID "
						+ "WHERE C.ConcertStartDT > GETDATE() "
						+ "AND C.ConcertName like '%" + this.Key + "%'");
				while (Result.next()) {
					Concerts upcomingconcerts = new Concerts();
					int UCID = Result.getInt("ConcertID");
					upcomingconcerts.ConcertName = Result.getString("ConcertName");
					upcomingconcerts.ConcertStartDate = Result.getDate("ConcertStartDT").toString().substring(0,10);
					upcomingconcerts.ConcertEndDate = Result.getDate("ConcertEndDT").toString().substring(0,10);
					upcomingconcerts.ConcertID = UCID;
					upcomingconcerts.VenueID = Result.getInt("VenueID");
					upcomingconcerts.VenureName = Result.getString("VenueName");
					UpcomingConcerts.add(upcomingconcerts);
				}
				//concerts already done
				Result = null;
				Result = S.executeQuery("SELECT C.ConcertName, C.ConcertID, "
						+ "C.ConcertStartDT, C.ConcertEndDT, V.VenueName, C.VenueID "
						+ "FROM Venue As V INNER JOIN Concert As C "
						+ "ON V.VenueID = C.VenueID "
						+ "WHERE C.ConcertStartDT <= GETDATE() "
						+ "AND C.ConcertName like '%" + this.Key + "%'");
				while (Result.next()) {
					Concerts upcomingconcerts = new Concerts();
					int UCID = Result.getInt("ConcertID");
					upcomingconcerts.ConcertName = Result.getString("ConcertName");
					upcomingconcerts.ConcertStartDate = Result.getDate("ConcertStartDT").toString().substring(0,10);
					upcomingconcerts.ConcertEndDate = Result.getDate("ConcertEndDT").toString().substring(0,10);
					upcomingconcerts.ConcertID = UCID;
					upcomingconcerts.VenueID = Result.getInt("VenueID");
					upcomingconcerts.VenureName = Result.getString("VenueName");
					PastConcerts.add(upcomingconcerts);
				}				
				int c1 = 0, c2 = 0;
			    while(c1 < UpcomingConcerts.size() || c2 < PastConcerts.size()) {
			        if(c1 < UpcomingConcerts.size())
			            this.ConcertsSearched.add( UpcomingConcerts.get(c1++));
			        if(c2 < PastConcerts.size())
			        	this.ConcertsSearched.add(PastConcerts.get(c2++));
			    }				
				
				//system recommendations
				Result = null;
				Result = S.executeQuery("exec SystemRecom " + this.UserId + " , " + "\"" + this.NavigationType + "\"" );
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
						+ "SELECT ListID FROM UserRecommendations WHERE ToUserID = " + this.UserId + " AND FromUserID <>  " + this.UserId + " ) "
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
