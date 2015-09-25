<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Concert"%>
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
	Concert H = null;
	ArrayList<Concert.Followers> U = null;	
	if(FlagLogedIn){
	if(request.getParameter("Cid") != null){
		H = new Concert(Integer.valueOf(request.getParameter("Cid")), (int) Session.getAttribute("userid"), (String) Session.getAttribute("type"));
		FlagNavigation = true;
	}
	if (H != null) {
		if(FlagNavigation && request.getParameter("doFollow") != null && request.getParameter("doFollow").equals("true")){
		H.doFollow(Integer.valueOf(request.getParameter("rating")), request.getParameter("comment"), request.getParameter("status") );
			response.sendRedirect("/mozart/Concert.jsp?Cid=" + H.ConcertID);
		}
		if(FlagNavigation && request.getParameter("doRec") != null && request.getParameter("doRec").equals("true")){
			H.doRec(request.getParameter("Listname"), Integer.valueOf(request.getParameter("RecUserID")), request.getParameter("comment"));
			response.sendRedirect("/mozart/Concert.jsp?Cid=" + H.ConcertID);
		}
		FlagHaveData = H.GetData();		
		if (FlagHaveData == 1) {
		U = H.UsersFollowing;				
	}
		}
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="CSS/Concert.css">
<title>Welcome <%
	if (FlagLogedIn && H.ConcertName != null) {
		out.print(H.ConcertName);
	}
%>
</title>
<script type="text/javascript">
	
<%if (!FlagLogedIn || H.ConcertID == -1) {
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
			<a href="Login?Lout=true" id="logout">Log Out</a> <a
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
				}%>><img
				id="setting" src="./icons/settingsicon.png"></a> <a
				<%switch ((String) Session.getAttribute("type")) {
				case "user":
					out.print("href=\"Home.jsp\"");
					break;
				case "artist":
					out.print("href=\"ArtistHome.jsp\"");
					break;
				case "band":
					out.print("href=\"BandHome.jsp\"");
					break;
				default:
					out.print("href=\"Login.jsp\"");
					break;
				}%>><img
				id="Home" src="./icons/homeicon.png"></a>
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
							src="./backgrounds/concert.jpg">
					</div>
					<div id="statsmaindiv" align="left">
						<div id="username">
							<%
								out.print(H.ConcertName);
							%>
						</div>
						<div id="follow">
							<%
								out.print("Followers " + H.FollowerCount);
							%>
						</div>
						<div id="bands">
							<%
								out.print("By: <a href=\"BandHome.jsp?Bid=" + H.BandId
											+ "\" style=\"color: inherit;\" >" + H.BandName
											+ "</a>&nbsp;&nbsp;");
							%>
						</div>
					</div>
				</div>
				<div id="aboutdiv" align="left">
					<div id="aboutheading" align="left">
						<img alt="" src="./icons/about.png"> About this concert
					</div>
					<div class="contentdivbelowabout">
						<%
							out.print("<BR>" + H.ConcertName + ": ");
								if (H.ConcertStartDate != null || H.ConcertEndDate != null) {
									if (H.ConcertStartDate != null && H.ConcertEndDate != null
											&& !H.ConcertStartDate.equals(H.ConcertEndDate)) {
										out.print("<BR>From: " + H.ConcertStartDate + " Till: "
												+ H.ConcertEndDate);
									} else {
										out.print("<BR>On: " + H.ConcertStartDate);
									}
								}
								out.print("<BR>Performed By" + H.BandName);
								out.print(" <a href=\"" + H.BandWebsite + "\">Website</a>");
								out.print("<BR>At: " + H.VenureName);
								out.print("<BR>Address: " + H.VenueAddress + "&nbsp;&nbsp;");
								out.print("<a href=\"" + H.VenueWebLink + "\">Directions</a>");
								out.print("<BR>Ticket Rates: " + H.TicketAmount + "$ each");
								out.print("<BR>Tickets Available: "
										+ (H.VenueCapacity - H.SoldTickets));
								out.print("<BR>Ticket sales start on: " + H.TicketSalesDate);
								out.print("<BR>For Booking Tickets <a href=\""
										+ H.TicketWebLink + "\">Click Here</a>");
								if (H.OverAllRating != -1) {
									int I = 0;
									out.print("<BR>Average Rating: ");
									for (I = 0; I < H.OverAllRating; I++) {
										out.print("<img src=\"./icons/starrated.png\">");
									}
									for (I = 0; I < (5 - H.OverAllRating); I++) {
										out.print("<img src=\"./icons/starnotrated.png\">");
									}
								} else {
									out.print("<BR>Average Rating: Not available");
								}
						%>
					</div>
				</div>
				<div id="bandsdiv" align="left">
					<div id="aboutheading" align="left">
						<img alt="" src="./icons/commentsandratingicon.jpg"
							style="height: 25px; margin-left: 5px;"> Comments and
						Ratings
					</div>
					<div class="contentdivbelowabout">
						<%
							if (U != null && U.size() > 0) {
									if (U.size() > 6) {
										out.print("<div style=\"height: 600px; overflow: hidden; width: 98%;\">");
										out.print("<div style=\"height: 600px; padding-bottom: 15px; overflow: scroll; width: 102%;\">");
									}
								}

								if ((Session.getAttribute("type").equals("user"))) {
									if (U != null && U.size() > 6) {
										out.print("<div class=\"item\" align=\"left\" style=\"width: 97%  !important\">");
									} else {
										out.print("<div class=\"item\" align=\"left\">");
									}
									out.print("<div align=\"left\" class=\"item1\">");
									out.print("<img src=\"./backgrounds/user.png\" style=\"height: 70px;width: 70px;\">");
									out.print("</div>");
									
									out.print("<div class=\"item2\" align=\"left\">");
									out.print("<B>Rate, Give comments for this Concert:</B>");

									out.print("<form action=\"Concert.jsp\" method=\"get\">");
									out.print("<input type=\"hidden\" name=\"doFollow\" value=\"true\" />");
									out.print("<input type=\"hidden\" name=\"Cid\" value=\""
											+ H.ConcertID + "\" />");
									out.print("<div style=\"display: inline-block;\">");
									out.print("Rate as:<BR> 1 Star <input type=\"radio\" name=\"rating\" value=\"1\" ");
									if (H.NavRating == 1) {
										out.print("checked=\"checked\"");
									}
									out.print(" >");
									out.print("<BR> 2 Star <input type=\"radio\" name=\"rating\" value=\"2\" ");
									if (H.NavRating == 2) {
										out.print("checked=\"checked\"");
									}
									out.print(" >");
									out.print("<BR> 3 Star <input type=\"radio\" name=\"rating\" value=\"3\" ");
									if (H.NavRating == 3) {
										out.print("checked=\"checked\"");
									}
									out.print(" >");
									out.print("<BR> 4 Star <input type=\"radio\" name=\"rating\" value=\"4\" ");
									if (H.NavRating == 4) {
										out.print("checked=\"checked\"");
									}
									out.print(" >");
									out.print("<BR> 5 Star <input type=\"radio\" name=\"rating\" value=\"5\" ");
									if (H.NavRating == 5) {
										out.print("checked=\"checked\"");
									}
									out.print(" >");
									out.print("</div>");
									out.print("<BR>");
									out.print("<div style=\"display: inline-block; margin-top: 10px\">");
									out.print("Tell us about your plan to attend this concert:");
									out.print("<table><tr>");
									out.print("<td>Attended</td><td><input type=\"radio\" name=\"status\" value=\"Attended\" ");
									if (H.NavStatus.equals("Attended")) {
										out.print("checked=\"checked\"");
									}
									out.print(" ></td></tr>");
									out.print("<tr><td>Planning</td><td><input type=\"radio\" name=\"status\" value=\"Planning\" ");
									if (H.NavStatus.equals("Planning")) {
										out.print("checked=\"checked\"");
									}
									out.print(" ></td></tr>");
									out.print("<tr><td>None </td><td><input type=\"radio\" name=\"status\" value=\"None\" ");
									if (H.NavStatus.equals("None")) {
										out.print("checked=\"checked\"");
									}
									out.print(" >");
									out.print("</td></tr></table>");
									out.print("</div>");

									out.print("<BR><div style=\"display: inline-block; margin-bottom: 10px \">");
									out.print("Any comments for other users:");
									out.print("<BR><input type=\"text\" name=\"comment\" value=\""
											+ H.NavComment + "\" style=\"width: 400px\" >");
									out.print("</div>");
									out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"submit\" value=\"Submit\" /><BR>");
									out.print("</form>");
									
									
									
									out.print("<form action=\"Concert.jsp\" method=\"get\">");
									out.print("<input type=\"hidden\" name=\"doRec\" value=\"true\" />");
									out.print("<input type=\"hidden\" name=\"Cid\" value=\""
											+ H.ConcertID + "\" />");									
									out.print("<div style=\" margin-top: 10px; margin-bottom: 10px\">");
									out.print("ListName:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"text\" name=\"Listname\" >");
									out.print("<BR>Recommend To: <select name=\"RecUserID\">");
									int I = 0;
									for(String Uname: H.NavFollowerName){
										out.print("<option value=\"" + H.NavFollowerID.get(I) + "\">");
										out.print(Uname);
										out.print("</option>");
										I++;
									}
									out.print("</select>");
									out.print("<BR>Comment:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type=\"text\" name=\"comment\" >");
									out.print("<BR><input type=\"submit\" value=\"Recommend\" /><BR>");
									out.print("</div>");									
									out.print("</form>");

									out.print("</div>");
									out.print("</div>");
								}

								if (U != null && U.size() > 0) {
									for (Concert.Followers C : U) {
										if (U.size() > 6) {
											out.print("<div class=\"item\" align=\"left\" style=\"width: 97%  !important\">");
										} else {
											out.print("<div class=\"item\" align=\"left\">");
										}
										out.print("<div align=\"left\" class=\"item1\">");
										out.print("<img src=\"./backgrounds/user.png\" style=\"height: 70px;width: 70px;\">");
										out.print("</div>");
										out.print("<div class=\"item2\" align=\"left\">");
										out.print("<B><a href=\"Home.jsp?Uid=" + C.UserID
												+ "\" style=\"color: inherit;\" >" + C.UserName
												+ "</a></B>");
										if (C.Status.equals("Attended")) {
											out.print(" Attended this");
										} else if (C.Status.equals("Planning")) {
											out.print(" is planning to attend");
										}
										out.print("<BR>Comment: " + C.Comment);
										if (C.Rating != -1) {
											int I = 0;
											out.print("<BR>Rated as: ");
											for (I = 0; I < C.Rating; I++) {
												out.print("<img src=\"./icons/starrated.png\">");
											}
											for (I = 0; I < (5 - C.Rating); I++) {
												out.print("<img src=\"./icons/starnotrated.png\">");
											}
										} else {
											out.print("<BR>Not rated this yet");
										}
										out.print("</div>");
										out.print("</div>");
									}
								}

								if (U != null && U.size() > 6) {
									out.print("</div>");
									out.print("</div>");
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