<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="model.connect,java.sql.*"%>

<%
	connect C = new connect();
	Statement stmt = C.DatabaseConnection.createStatement();

	HttpSession Session1 = null;
	Session1 = request.getSession();
	ResultSet rs = stmt
			.executeQuery("SELECT VenueID,VenueName from Venue");	
	
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Account</title>
<link rel="stylesheet" type="text/css" href="CSS/Signup.css">

</head>
<body id="body">
	<div id="banner-top">
		<div id="banner">
			<H1 id="heading-mozart">Welcome To Mozart</H1>
			<H3 style="margin: 0px; padding: 10px">Fill the form below to
				create a new Concert</H3>
		</div>
	</div>
	<div align="center">
		<div align="center" id="main">
			<div align="center" id="Signup-main">
				<div align="center" id="Signup-Heading">
					<H2 style="margin: 0px">Create Concert</H2>
				</div>
				<form action="CreateConcertController" method="post">
					<div id="ErrorText"
						style="margin-bottom: 5px; display: inline-block; width: 300px; color: red; visibility: hidden;">
					</div>
					<div id="formrowone">
						<div style="float: left;">Concert Name:</div>
						<div style="float: right;">
							<input id="cname" name="cname" type="text" value="" />
						</div>
					</div>
					<BR>

					<div id="formrowone">
						<div style="float: left;">Concert Start Date:</div>
						<div style="float: right;">
							<input id="concertsdt" name="concertsdt" type="text" value="" />
						</div>
					</div>
					<BR>
					<div id="formrowone">
						<div style="float: left;">Concert End Date:</div>
						<div style="float: right;">
							<input id="concertedt" name="concertedt" type="text" value="" />
						</div>
					</div>
					<BR>
					<div id="formrowone">
						<div style="float: left;">Venue:</div>
						<div style="float: right;">

							<select name="select">
									<% while (rs.next()) { 
									out.print("<Option value=\"" + rs.getString(1) + "\">");								
									out.print(rs.getString(2));
									out.print("</Option>");
										}
									%>
								

							</select>
						</div>
					</div>
					<BR>
					<div id="formrowone">
						<div style="float: left;">Ticket Amount:</div>
						<div id="lognamediv" style="float: right;">
							<input id="ticketcost" name="ticketcost" type="text" value="" />
						</div>
					</div>
					<BR>
					<div id="formrowone">
						<div style="float: left;">Tickets Start Date:</div>
						<div style="float: right;">
							<input id="ticketsdt" name="ticketsdt" type="text" value="" />
						</div>
					</div>
					<BR>
					<div id="formrowtwo">
						<div style="float: left;">Ticket Web Link:</div>
						<div style="float: right;">
							<input id="ticketlink" name="ticketlink" type="text" value="" />
						</div>
					</div>
					<BR>
					<div id="submit" align="center">
						<input type="submit" value="Create Concert" id="submitbutton">
					</div>
					<BR> <BR>
				</form>
			</div>
		</div>
		<div align="center">
			<h3>
				<a id="Login" href="/mozart/BandHome.jsp">Home</a>
			</h3>
		</div>
	</div>
</body>
</html>