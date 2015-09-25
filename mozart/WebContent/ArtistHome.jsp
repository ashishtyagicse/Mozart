<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.ArtistHome"%>
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
	ArtistHome H = null;
	ArrayList<ArtistHome.Follower> UF = null;
	ArrayList<ArtistHome.Band> UB = null;
	ArrayList<ArtistHome.SystemRec> SYS = null;
	ArrayList<ArtistHome.UserRec> USR = null;
	
	if(FlagLogedIn){
	if(request.getParameter("Aid") != null){
		H = new ArtistHome(Integer.valueOf(request.getParameter("Aid")), (int) Session.getAttribute("userid"), (String) Session.getAttribute("type"),  true);
		FlagNavigation = true;
	} else {
		H = new ArtistHome((int) Session.getAttribute("userid"), (int) Session.getAttribute("userid"), "", false);
	}
	if (H != null) {
		if(FlagNavigation && request.getParameter("doFollow") != null && request.getParameter("doFollow").equals("true")){
			H.doFollow();
		}
		FlagHaveData = H.GetData();
		if (FlagHaveData == 1) {
	UF = H.UserFollowing;
	UB = H.BandFollowing;
	SYS = H.SysRecom;
	USR = H.UserRecom;
		}
	}
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="CSS/Home.css">
<title>Welcome <%
	if (FlagLogedIn && H.ArtistFullName != null) {
		out.print(H.ArtistFullName);
	}
%>
</title>
<script type="text/javascript">
	
<%if (!FlagLogedIn || H.ArtisId == -1) {
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
			<a href="Login?Lout=true" id="logout">Log Out</a>
			<a
			<%if (FlagLogedIn) {
				switch ((String) Session.getAttribute("type")) {
				case "user":
					out.print("href=\"UserProfile.jsp\"");
					break;
				case "artist":
					out.print("href=\"ArtistProfile.jsp\"");
					break;
				case "band":
					out.print("href=\"BandProfile.jsp\"");
					break;
				default:
					out.print("href=\"Login.jsp\"");
					break;
				}
				%>
				><img id="setting" src="./icons/settingsicon.png"></a>
				<a 
				<%														
									switch ((String) Session.getAttribute("type")) {
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
								}
									
				%>
				><img id="Home" src="./icons/homeicon.png"></a>				
		</div>
		<div>
			<div id="mainbodydiv">
				<div id="photodiv">
					<div id="mainpicdiv">
						<div style="position: relative;">
							<img id="mainpic" alt="Main Picture"
								src="./backgrounds/UserTimelinepic.png"> <img id="mainpic"
								src="./backgrounds/Backhighlight.png">
						</div>
					</div>
					<div id="profilepicdiv">
						<img id="profilepic" alt="Profile Picture"
							src="./backgrounds/user.png">
					</div>
					<div id="statsmaindiv" align="left">
						<div id="username">
							<%
								out.print(H.ArtistFullName);
							%>
						</div>
						<div id="userband"
							style="position: absolute; margin-top: 180px; margin-left: 35px; width: 150px; font-weight: bold; font-size: larger;">
							<%
								out.print("<a href=\"BandHome.jsp?Bid=" + H.BandId
											+ "\" style=\"color: inherit;\">" + H.BandName + "</a>");
							%>
						</div>
						<div id="following">
							<%
								out.print("Following " + H.NoOfFollowing);
							%>
						</div>
						<div id="follow">
							<%
								out.print("Followers " + H.NoOfFollowers);
							%>
						</div>
						<div id="bands">
							<%
								out.print("Bands " + H.NumberOfBands);
							%>
						</div>
						<% 
								if(H.FlagNavigation && !H.NavigatorFollow){
								out.print("<div id=\"NavFollow\">");								
								out.print("<form action=\"ArtistHome.jsp\" method=\"get\">");
								out.print("<input type=\"hidden\" name=\"doFollow\" value=\"true\" />");
								out.print("<input type=\"hidden\" name=\"Aid\" value=\""+ H.ArtisId +"\" />"); 
								out.print("<input type=\"submit\" value=\"Follow\" />");
								out.print("</form>");								
								out.print("</div>");
								}
						%>
					</div>
				</div>
				<div id="aboutdiv" align="left">
					<div id="aboutheading" align="left">
						<img alt="" src="./icons/about.png"> About
					</div>
					<div class="contentdivbelowabout">
						<%
							out.print(H.ArtistFullName + ": ");
								if (H.Age != null) {
									out.print(H.Age + " years old ");
								}
								if (H.Gender != null) {
									out.print(H.Gender + " ");
								}
								if (H.MaritalStatus != null && H.NavigatorFollow) {
									out.print(H.MaritalStatus + " ");
								}
								if (H.InterestedIn != null && H.NavigatorFollow) {
									out.print("<BR> Interested In " + H.InterestedIn);
								}
								if ((H.City != null || H.Address != null) && H.NavigatorFollow) {
									out.print("<BR> Lives in ");
									if (H.City != null) {
										out.print(" " + H.City);
									}
									if (H.Address != null) {
										out.print(" " + H.Address);
									}
								}
								if ((H.Phone != null || H.Email != null) && H.NavigatorFollow) {
									out.print("<BR> Contact Info ");
									if (H.Email != null) {
										out.print("<BR> Email: " + H.Email);
									}
									if (H.Phone != null) {
										out.print("<BR> Phone: " + H.Phone);
									}
								}
								if (H.MotherTongue != null) {
									out.print("<BR> Speaks " + H.MotherTongue);
								}
								if (H.PoliticalView != null) {
									out.print("<BR> Political view " + H.PoliticalView);
								}
								if (H.ReligiousView != null) {
									out.print("<BR> Believes In " + H.ReligiousView);
								}
								if (H.HomeTown != null) {
									out.print("<BR> Originally from " + H.HomeTown);
								}
								if (H.University != null && H.NavigatorFollow) {
									out.print("<BR> Attended " + H.University);
								}
								if (H.School != null && H.NavigatorFollow) {
									out.print("<BR> Completed Initial Study at " + H.School);
								}
								if (H.Music != null) {
									out.print("<BR>Music Interest: ");
									for (String S : H.Music) {
										out.print(S + "  ");
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
							if (UB.size() > 0) {
									if (UB.size() > 6) {
										out.print("<div style=\"height: 600px; overflow: hidden; width: 98%;\">");
										out.print("<div style=\"height: 600px; padding-bottom: 15px; overflow: scroll; width: 102%;\">");
									}
									for (ArtistHome.Band B : UB) {
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
							if (UF.size() > 0) {
									if (UF.size() > 6) {
										out.print("<div style=\"height: 600px; overflow: hidden; width: 98%;\">");
										out.print("<div style=\"height: 600px; padding-bottom: 15px; overflow: scroll; width: 102%;\">");
									}
									for (ArtistHome.Follower F : UF) {
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
							if (SYS.size() > 0) {
									if (SYS.size() > 5) {
										out.print("<div style=\"height: 500px; overflow: hidden; width: 98%;\">");
										out.print("<div style=\"height: 500px; padding-bottom: 15px; overflow: scroll; width: 108%;\">");
									}
									for (ArtistHome.SystemRec S : SYS) {
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
							if (USR.size() > 0) {
									if (USR.size() > 5) {
										out.print("<div style=\"height: 500px; overflow: hidden; width: 98%;\">");
										out.print("<div style=\"height: 500px; padding-bottom: 15px; overflow: scroll; width: 108%;\">");
									}
									for (ArtistHome.UserRec U : USR) {
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