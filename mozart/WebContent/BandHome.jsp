<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.BandHome"%>
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
	BandHome H = null;
	ArrayList<BandHome.Concerts> UC = null;
	ArrayList<BandHome.Concerts> PC = null;
	ArrayList<BandHome.Followers> UF = null;
	ArrayList<BandHome.Followers> AF = null;
	if(FlagLogedIn){
	if(request.getParameter("Bid") != null){
		H = new BandHome(Integer.valueOf(request.getParameter("Bid")), (int) Session.getAttribute("userid"), (String) Session.getAttribute("type"),  true);
		FlagNavigation = true;
	} else {
		H = new BandHome((int) Session.getAttribute("userid"), (int) Session.getAttribute("userid"), "", false);
	}	
	if (H != null) {
		if(FlagNavigation && request.getParameter("doFollow") != null && request.getParameter("doFollow").equals("true")){
			H.doFollow();
		}
		FlagHaveData = H.GetData();		
		if (FlagHaveData == 1) {
				UC = H.UpcomingConcerts;
				PC = H.PastConcerts;
				UF = H.UsersFollowing;
				AF = H.ArtistFollowing;
			}
		}
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="CSS/BandHome.css">
<title>Welcome <%
	if (FlagLogedIn && H.BandName != null) {
		out.print(H.BandName);
	}
%>
</title>
<script type="text/javascript">
	
<%if (!FlagLogedIn || H.BandID == -1) {
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
			<a	href="Login?Lout=true" id="logout">Log Out</a>
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
				}%>
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
				<a href="CreateConcert.jsp"><img alt="" src="./icons/upcoming.png" style="float: right; margin-right: 10px; height: 40px; margin-top: 5px "></a>			
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
								out.print(H.BandName);
							%>
						</div>
						<div id="follow">
							<%
								out.print("Followers " + H.Followers);
							%>
						</div>
						<div id="bands">
							<%
								out.print("Artists: ");
									int I = 0;
									for (String A : H.Artist) {
										out.print("<a href=\"ArtistHome.jsp?Aid="
												+ H.ArtistID.get(I)
												+ "\" style=\"color: inherit;\" >" + A
												+ "</a>&nbsp;&nbsp;");
										I++;
									}
							%>
						</div>
						<% 
								if(H.FlagNavigation && !H.NavigatorFollow && !(Session.getAttribute("type").equals("band"))){
								out.print("<div id=\"NavFollow\">");								
								out.print("<form action=\"BandHome.jsp\" method=\"get\">");
								out.print("<input type=\"hidden\" name=\"doFollow\" value=\"true\" />");
								out.print("<input type=\"hidden\" name=\"Bid\" value=\""+ H.BandID +"\" />"); 
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
							out.print(H.BandName + ": ");
								if (H.BandBiodata != null) {
									out.print("<BR>" + H.BandBiodata);
								}
								if (H.BandCreationDate != null) {
									out.print("<BR>Established on: " + H.BandCreationDate);
								}
								if (H.Address != null) {
									out.print("<BR>Originally From " + H.Address);
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
								if (H.Music != null) {
									out.print("<BR>Plays: ");
									for (String S : H.Music) {
										out.print(S + "  ");
									}
								}
						%>
					</div>
				</div>
				<div id="bandsdiv" align="left">
					<div id="aboutheading" align="left">
						<img alt="" src="./icons/upcoming.png"
							style="height: 25px; margin-left: 5px;"> Upcoming Concerts
					</div>
					<div class="contentdivbelowabout">
						<%
							if (UC != null && UC.size() > 0) {
									if (UC.size() > 6) {
										out.print("<div style=\"height: 600px; overflow: hidden; width: 98%;\">");
										out.print("<div style=\"height: 600px; padding-bottom: 15px; overflow: scroll; width: 102%;\">");
									}
									for (BandHome.Concerts C : UC) {
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
				<div id="usersdiv" align="left">
					<div id="aboutheading" align="left">
						<img alt="" src="./icons/past.png"
							style="height: 25px; margin-left: 5px;"> Past Concerts
					</div>
					<div class="contentdivbelowabout">
						<%
							if (PC != null && PC.size() > 0) {
									if (PC.size() > 6) {
										out.print("<div style=\"height: 600px; overflow: hidden; width: 98%;\">");
										out.print("<div style=\"height: 600px; padding-bottom: 15px; overflow: scroll; width: 102%;\">");
									}
									for (BandHome.Concerts C : PC) {
										if (PC.size() > 6) {
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
											out.print("Held On: " + C.ConcertStartDate + "<BR>");
										} else {
											out.print("Was From: " + C.ConcertStartDate
													+ "  To: " + C.ConcertEndDate + "<BR>");
										}
										out.print("Average Rating: ");
										for (I = 0; I < C.OverAllRating; I++) {
											out.print("<img src=\"./icons/starrated.png\">");
										}
										for (I = 0; I < (5 - C.OverAllRating); I++) {
											out.print("<img src=\"./icons/starnotrated.png\">");
										}
										out.print("<div class=\"starRate\"><b></b></div>");
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
			</div>
			<div id="recommendationbardiv">
				<div id="systemrecdiv" align="left">
					<div id="aboutheading" align="left">
						<img alt="" src="./icons/artisticon.png"
							style="height: 25px; margin-left: 5px;"> Artist Followers
					</div>
					<div class="contentdivbelowabout">
						<%
							if (AF != null && AF.size() > 0) {
									if (AF.size() > 10) {
										out.print("<div style=\"height: 500px; overflow: hidden; width: 98%;\">");
										out.print("<div style=\"height: 500px; padding-bottom: 15px; overflow: scroll; width: 108%;\">");
									}
									for (BandHome.Followers A : AF) {
										out.print("<div class=\"item22\" align=\"left\">");
										out.print("<div align=\"left\" class=\"item1\">");
										out.print("<img src=\"./backgrounds/band.png\" style=\"height: 40px;width: 40px;\">");
										out.print("</div>");
										out.print("<div class=\"item222\" align=\"left\">");
										out.print("<B><a href=\"ArtistHome.jsp?Aid=" + A.UserID
												+ "\">" + A.UserName + "</a></B><BR>");
										out.print("</div>");
										out.print("</div>");
									}
									if (AF.size() > 10) {
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
							style="height: 25px; margin-left: 5px;"> User Followers
					</div>
					<div class="contentdivbelowabout">
						<%
							if (UF != null && UF.size() > 0) {
									if (UF.size() > 10) {
										out.print("<div style=\"height: 500px; overflow: hidden; width: 98%;\">");
										out.print("<div style=\"height: 500px; padding-bottom: 15px; overflow: scroll; width: 108%;\">");
									}
									for (BandHome.Followers U : UF) {
										out.print("<div class=\"item22\" align=\"left\">");
										out.print("<div align=\"left\" class=\"item1\">");
										out.print("<img src=\"./backgrounds/user.png\" style=\"height: 40px;width: 40px;\">");
										out.print("</div>");
										out.print("<div class=\"item222\" align=\"left\">");
										out.print("<B><a href=\"Home.jsp?Uid=" + U.UserID
												+ "\">" + U.UserName + "</a></B><BR>");
										out.print("</div>");
										out.print("</div>");
									}
									if (UF.size() > 10) {
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