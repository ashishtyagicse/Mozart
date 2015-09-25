<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.search"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	HttpSession Session = null;	
	boolean FlagLogedIn = false;
	Session = request.getSession(false);
	if (Session.getAttribute("userid") != null) {
		FlagLogedIn = true;
	}
	
	int FlagHaveData = 0;
	boolean FlagNavigation = false;
	search H = null;
	ArrayList<search.Users> UF = null;
	ArrayList<search.Band> UB = null;
	ArrayList<search.Concerts> UC = null;
	ArrayList<search.Users> AF = null;
	ArrayList<search.SystemRec> SYS = null;
	ArrayList<search.UserRec> USR = null;
	
	if(FlagLogedIn){	
	H = new search((int) Session.getAttribute("userid"), (String) Session.getAttribute("type"), (String) request.getParameter("key"));
		
	if (H != null) {
		FlagHaveData = H.GetData();
		if (FlagHaveData == 1) {
	UF = H.UserSearched;
	UB = H.BandsSearched;
	SYS = H.SysRecom;
	USR = H.UserRecom;
	UC = H.ConcertsSearched;
	AF = H.ArtistSearched;
		}
	}
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="CSS/Search.css">
<title>Welcome <%
	if (FlagLogedIn && (String) request.getParameter("key") != null) {
		out.print("Search: " + (String) request.getParameter("key"));
	}
%>
</title>
<script type="text/javascript">
	
<%if (!FlagLogedIn || H.UserId == -1) {
		out.print("function AlreadyLogedIn() {\n window.alert(\"Invalid user session please Login again.\"); "
				+ "\n window.location = '/mozart/Login.jsp';}");
	}%>
	
</script>
</head>
<body onload="AlreadyLogedIn();">
	<div align="center">
		<div id="topbardiv">
			<form action="Search.jsp" method="get">
				<input id="search" type="text" name="key" /> <input
					id="searchbutton" type="submit" value="" />
			</form>
			<a href="Login?Lout=true" id="logout" >Log Out</a>
			<a 
			<%if (FlagLogedIn) {									
									switch ((String) Session.getAttribute("type")) {
									case "user" :
										out.print("href=\"UserProfile.jsp\"");
										break;
									case "artist" :
										out.print("href=\"ArtistProfile.jsp\"");
										break;
									case "band" :
										out.print("href=\"BandProfile.jsp\"");
										break;
									default :
										out.print("href=\"Login.jsp\"");
										break;
								}%>
			><img id="setting" src="./icons/settingsicon.png"></a>
			<a 
			<%switch ((String) Session.getAttribute("type")) {
									case "user" :
										out.print("href=\"Home.jsp\"");
										break;
									case "artist" :
										out.print("href=\"ArtistHome.jsp\"");
										break;
									case "band" :
										out.print("href=\"BandHome.jsp\"");
										break;
									default :
										out.print("href=\"Login.jsp\"");
										break;
								}%>
			><img id="Home" src="./icons/homeicon.png"></a>			
		</div>
		<div>
			<div id="mainbodydiv">							
				<div id="bandsdiv" align="left">
					<div id="aboutheading" align="left">
						<img alt="" src="./icons/bandicon.png"
							style="height: 25px; margin-left: 5px;"> Concerts
					</div>
					<div class="contentdivbelowabout">
						<%
							if (UC != null && UC.size() > 0) {
									if (UC.size() > 6) {
										out.print("<div style=\"height: 600px; overflow: hidden; width: 98%;\">");
										out.print("<div style=\"height: 600px; padding-bottom: 15px; overflow: scroll; width: 102%;\">");
									}
									for (search.Concerts C : UC) {
										if (UC.size() > 6) {
											out.print("<div class=\"item\" align=\"left\" style=\"width: 97%  !important\">");
										} else {
											out.print("<div class=\"item\" align=\"left\">");
										}
										out.print("<div align=\"left\" class=\"item1\">");
										out.print("<img src=\"./backgrounds/concert.jpg\" style=\"height: 70px;width: 70px;\">");
										out.print("</div>");
										out.print("<div class=\"item2\" align=\"left\">");
										out.print("<B><a href=\"Concert.jsp?Cid=" + C.ConcertID
												+ "\" style=\"color: inherit;\" >"
												+ C.ConcertName + "</a></B><BR>");
										if (C.ConcertStartDate.equals(C.ConcertEndDate)) {
											out.print("On: " + C.ConcertStartDate + "<BR>");
										} else {
											out.print("From: " + C.ConcertStartDate
													+ "  Till: " + C.ConcertEndDate + "<BR>");
										}
										out.print("At: " + C.VenureName);
										out.print("</div>");
										out.print("</div>");
									}
									if (UC.size() > 6) {
										out.print("</div>");
										out.print("</div>");
									}
								}
						%>
					</div>
				</div>
				<div id="bandsdiv" align="left">
					<div id="aboutheading" align="left">
						<img alt="" src="./icons/bandicon.png"
							style="height: 25px; margin-left: 5px;"> Bands
					</div>
					<div class="contentdivbelowabout">
						<%
							if (UB != null && UB.size() > 0) {
													if (UB.size() > 6) {
														out.print("<div style=\"height: 600px; overflow: hidden; width: 98%;\">");
														out.print("<div style=\"height: 600px; padding-bottom: 15px; overflow: scroll; width: 102%;\">");
													}
													for (search.Band B : UB) {
														if (UB.size() > 6) {
															out.print("<div class=\"item\" align=\"left\" style=\"width: 97%  !important\">");
														} else {
															out.print("<div class=\"item\" align=\"left\">");
														}
														out.print("<div align=\"left\" class=\"item1\">");
														out.print("<img src=\"./backgrounds/band.png\" style=\"height: 70px;width: 70px;\">");
														out.print("</div>");
														out.print("<div class=\"item2\" align=\"left\">");
														out.print("<B><a href=\"BandHome.jsp?Bid="
																+ B.BandID
																+ "\" style=\"text-decoration: none; color: inherit;\" >"
																+ B.BandName + "</a></B><BR>");
														if (B.FBandUpcomingConcert.size() == 0) {
															out.print("Upcoming Concerts: None<BR>");
														} else {
															out.print("Upcoming Concerts:");
															int i = 0;
															for (String C : B.FBandUpcomingConcert) {
																out.print("<BR><a href=\"Concert.jsp?Cid="
																		+ B.FBandConcertId.get(i) + "\">" + C
																		+ "</a>");
																out.print(" on " + B.FBandUConcertDate.get(i));
																i++;
															}
														}
														out.print("</div>");
														out.print("</div>");
													}
													if (UB.size() > 6) {
														out.print("</div>");
														out.print("</div>");
													}
												}
						%>
					</div>
				</div>
				<div id="usersdiv" align="left">
					<div id="aboutheading" align="left">
						<img alt="" src="./icons/usericon.png"
							style="height: 25px; margin-left: 5px;"> Friends
					</div>
					<div class="contentdivbelowabout">
						<%
							if (UF != null && UF.size() > 0) {
													if (UF.size() > 6) {
														out.print("<div style=\"height: 600px; overflow: hidden; width: 98%;\">");
														out.print("<div style=\"height: 600px; padding-bottom: 15px; overflow: scroll; width: 102%;\">");
													}
													for (search.Users F : UF) {
														if (UF.size() > 6) {
															out.print("<div class=\"item\" align=\"left\" style=\"width: 97%  !important\">");
														} else {
															out.print("<div class=\"item\" align=\"left\">");
														}
														out.print("<div align=\"left\" class=\"item1\">");
														out.print("<img src=\"./backgrounds/user.png\" style=\"height: 70px;width: 70px;\">");
														out.print("</div>");
														out.print("<div class=\"item2\" align=\"left\">");
														out.print("<B><a href=\"Home.jsp?Uid="
																+ F.FollowerID
																+ "\" style=\"text-decoration: none; color: inherit;\" >"
																+ F.FollowerName + "</a></B><BR>");
														if (F.FollowersRecentConcerts.size() == 0) {
															out.print("Recently Liked Concerts: None");
														} else {
															out.print("Recently Liked Concerts:<BR>");
															int i = 0;
															for (String C : F.FollowersRecentConcerts) {
																out.print("<a href=\"Concert.jsp?Cid="
																		+ F.FRConcertsID.get(i) + "\">" + C
																		+ "</a>&nbsp;&nbsp;");
																i++;
															}
														}
														if (F.FollowersRecentBands.size() == 0) {
															out.print("<BR>Recently Liked Bands: None<BR>");
														} else {
															out.print("<BR>Recently Liked Bands:<BR>");
															int i = 0;
															for (String C : F.FollowersRecentBands) {
																out.print("<a href=\"BandHome.jsp?Bid="
																		+ F.FRBandsID.get(i) + "\">" + C
																		+ "</a>&nbsp;&nbsp;");
																i++;
															}
														}
														out.print("</div>");
														out.print("</div>");
													}
													if (UF.size() > 6) {
														out.print("</div>");
														out.print("</div>");
													}
												}
						%>
					</div>
				</div>
			
				<div id="usersdiv" align="left" style="margin-bottom: 200px !important;">
					<div id="aboutheading" align="left">
						<img alt="" src="./icons/artisticon.png"
							style="height: 25px; margin-left: 5px;"> Artists
					</div>
					<div class="contentdivbelowabout">
						<%
							if (AF != null && AF.size() > 0) {
													if (AF.size() > 6) {
														out.print("<div style=\"height: 600px; overflow: hidden; width: 98%;\">");
														out.print("<div style=\"height: 600px; padding-bottom: 15px; overflow: scroll; width: 102%;\">");
													}
													for (search.Users F : AF) {
														if (UF.size() > 6) {
															out.print("<div class=\"item\" align=\"left\" style=\"width: 97%  !important\">");
														} else {
															out.print("<div class=\"item\" align=\"left\">");
														}
														out.print("<div align=\"left\" class=\"item1\">");
														out.print("<img src=\"./backgrounds/user.png\" style=\"height: 70px;width: 70px;\">");
														out.print("</div>");
														out.print("<div class=\"item2\" align=\"left\">");
														out.print("<B><a href=\"ArtistHome.jsp?Aid="
																+ F.FollowerID
																+ "\" style=\"text-decoration: none; color: inherit;\" >"
																+ F.FollowerName + "</a></B>");														
														if (F.FollowersRecentBands.size() == 0) {
															out.print("<BR>Recently Liked Bands: None<BR>");
														} else {
															out.print("<BR>Recently Liked Bands:<BR>");
															int i = 0;
															for (String C : F.FollowersRecentBands) {
																out.print("<a href=\"BandHome.jsp?Bid="
																		+ F.FRBandsID.get(i) + "\">" + C
																		+ "</a>&nbsp;&nbsp;");
																i++;
															}
														}
														out.print("</div>");
														out.print("</div>");
													}
													if (UF.size() > 6) {
														out.print("</div>");
														out.print("</div>");
													}
												}
						%>
					</div>
				</div>
			</div>
			<div id="recommendationbardiv">
				<div id="systemrecdiv" align="left">
					<div id="aboutheading" align="left">
						<img alt="" src="./icons/systemicon.png"
							style="height: 25px; margin-left: 5px;"> System
						Recommendations
					</div>
					<div class="contentdivbelowabout">
						<%
							if (SYS != null && SYS.size() > 0 && !(Session.getAttribute("type").equals("band"))) {
									if (SYS.size() > 5) {
										out.print("<div style=\"height: 500px; overflow: hidden; width: 98%;\">");
										out.print("<div style=\"height: 500px; padding-bottom: 15px; overflow: scroll; width: 108%;\">");
									}
									for (search.SystemRec S : SYS) {
										out.print("<div class=\"item22\" align=\"left\">");
										out.print("<div align=\"left\" class=\"item1\">");
										out.print("<img src=\"./backgrounds/concert.jpg\" style=\"height: 70px;width: 70px;\">");
										out.print("</div>");
										out.print("<div class=\"item222\" align=\"left\">");
										out.print("<B><a href=\"Concert.jsp?Cid=" + S.ConcertId
												+ "\">" + S.ConcertName + "</a></B><BR>");
										out.print("on: "
												+ S.ConcertDate
												+ "<BR> Performed By "
												+ "<a href=\"BandHome.jsp?Bid="
												+ S.BandId
												+ "\" style=\"text-decoration: none; color: inherit;\" >"
												+ S.BandName + "</a><BR>");
										out.print("</div>");
										out.print("</div>");
									}
									if (SYS.size() > 5) {
										out.print("</div>");
										out.print("</div>");
									}
								}
						%>
					</div>
				</div>
				<div id="userrecdiv" align="left">
					<div id="aboutheading" align="left">
						<img alt="" src="./icons/usericon.png"
							style="height: 25px; margin-left: 5px;"> User
						Recommendations
					</div>
					<div class="contentdivbelowabout">
						<%
							if (USR != null && USR.size() > 0 && !(Session.getAttribute("type").equals("band"))) {
									if (USR.size() > 5) {
										out.print("<div style=\"height: 500px; overflow: hidden; width: 98%;\">");
										out.print("<div style=\"height: 500px; padding-bottom: 15px; overflow: scroll; width: 108%;\">");
									}
									for (search.UserRec U : USR) {
										out.print("<div class=\"item22\" align=\"left\">");
										out.print("<div align=\"left\" class=\"item1\">");
										out.print("<img src=\"./backgrounds/user.png\" style=\"height: 70px;width: 70px;\">");
										out.print("</div>");
										out.print("<div class=\"item222\" align=\"left\">");
										out.print("<B><a href=\"Home.jsp?Uid="
												+ U.UserID
												+ "\" style=\"text-decoration: none; color: inherit;\" >"
												+ U.UserName + "</a></B><BR>");
										if (U.ListName.length() > 20) {
											out.print("REC: " + U.ListName.substring(0, 18)
													+ "... <BR>");
										} else {
											out.print("REC: " + U.ListName + " <BR>");
										}
										out.print("<a href=\"BandHome.jsp?Bid=" + U.BandId
												+ "\">" + U.BandName + "</a>'s ");
										out.print("<BR><a href=\"Concert.jsp?Cid="
												+ U.ConcertId + "\">" + U.ConcertName + "</a>");
										if (U.ConcertName.length() > 14) {
											out.print("<BR>on "
													+ U.ConcertDate.toString().substring(0, 10));
										} else {
											out.print(" on "
													+ U.ConcertDate.toString().substring(0, 10));
										}
										if (U.Comment.length() <= 40) {
											out.print("<BR>Comment: " + U.Comment);
										} else if (U.Comment.contains(" ")) {
											out.print("<BR>Comment: "
													+ U.Comment.substring(0, 37) + "...");
										}
										out.print("</div>");
										out.print("</div>");
									}
									if (USR.size() > 5) {
										out.print("</div>");
										out.print("</div>");
									}
								}
							}
						%>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>