<%@page import="javax.websocket.Session"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="model.connect,java.sql.*" %>
    
    <%
    
    connect C = new connect();
    Statement stmt = C.DatabaseConnection.createStatement();
    Statement stmt1 = C.DatabaseConnection.createStatement();
    Statement StMusic = C.getQuery();
    HttpSession Session1 = null;
    Session1 = request.getSession();
    ResultSet rs = stmt.executeQuery("SELECT * from Band where BandId="+Session1.getAttribute("userid"));
    ResultSet rs1 = stmt1.executeQuery("SELECT * from Logins where LoginId="+Session1.getAttribute("userid"));
    ResultSet mresult = StMusic.executeQuery("SELECT GenreID, GenreName From Genre");
    while (rs.next()){
    %>	
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Your Profile</title>
<link rel="stylesheet" type="text/css" href="CSS/editprofile.css">
</head>
<body id="body" >
	<div id="banner-top">
		<div id="banner">
			<H1 id="heading-mozart">Welcome To Mozart</H1>
			<H3 style="margin: 0px; padding: 10px">Fill the form below to
				create a new Profile</H3>
		</div>
	</div>
	<div align="center">
		<div align="center" id="main">
			<div align="center" id="Signup-main">
				<div align="center" id="Signup-Heading">
					<H2 style="margin: 0px">Update Your Profile</H2>
				</div>
	<form action="ProfileBandController" method="post">
	<div id="ErrorText" style="margin-bottom: 5px; display: inline-block; width: 300px; color: red; visibility: hidden;">
					</div>
					
	<div id="formrowone">
		<div style="float: left;">Band name:</div>
		<div style="float: right;">
		<input id= "fname" name="fname" type="text" value="<%out.print(rs.getString(2)); %>" size="30" /><br />
		</div>
	</div>
	<BR>
	
	<div id="formrowone">
		<div style="float: left;">Band Biodata:</div>
		<div style="float: right;">
		<input id="lname"  name="lname" type="text" value="<%out.print(rs.getString(3)); %>" size="30" /><br />
	</div>
	</div>
		<BR>
		
	<div id="formrowone">
		<div style="float: left;">Band Music:</div>
		<div style="float: right;">
		
		<select name="music" multiple="multiple" >
		<%
			while (mresult.next()) {
			out.print("<Option value=\"" + mresult.getString(1) + "\">");
			out.print(mresult.getString(2));
			out.print("</Option>");
			}
		%>
		</select>		
	</div>
	</div>
		<BR>	
		
	<div id="formrowone">
		<div style="float: left;">Date Of Birth:</div>
		<div style="float: right;">
		<input id="dob"  name="dob" type="text" value="<%out.print(rs.getString(5)); %>" size="30" /><br />
	</div>
	</div>
	<BR>
	
	
	<div id="formrowone">
		<div style="float: left;">Email:</div>
		<div style="float: right;">
		<input id="email"  name="email" type="text" value="<%out.print(rs.getString(9)); %>" size="30" /><br />
	</div>
	</div>
	
	<div id="formrowone">
		<div style="float: left;">Phone:</div>
		<div style="float: right;">
		<input id="Phone"  name="Phone" type="text" value="<%out.print(rs.getString(7)); %>" size="30" /><br />
	</div>
	</div>
	
	<div id="formrowone">
		<div style="float: left;">Address:</div>
		<div style="float: right;">
		<input id="Address"  name="Address" type="text" value="<%out.print(rs.getString(8)); }%>" size="30" /><br />
	</div>
	</div>
	
	
	<div id="formrowone">
		<div style="float: left;">Login Name:</div>
		<div style="float: right;">
		<input id="logname"  name="logname" type="text" value="<%while (rs1.next()){out.print(rs1.getString(1)); }%>" size="30" /><br />
	</div>
	</div>
	
	
	<div id="formrowone">
		<div style="float: left;">Password:</div>
		<div style="float: right;">
		<input id="pass"  name="pass" type="text" value="" size="30" /><br />
	</div>
	</div>
	
	
	<div id="formrowone">
		<div style="float: left;">Confirm Password:</div>
		<div style="float: right;">
		<input id="cpass"  name="cpass" type="text" value="" size="30" /><br />
	</div>
	</div>
	<br>
	<div id="submit" align="center">
	<input id="submit_button" type="submit" value="Edit Profile" />
		</div>
			<BR> <BR>
</form>	
</div>
		</div>
		<div align="center">
			<h3>
				<a id="Home" href="/mozart/BandHome.jsp">Home</a>
			</h3>
		</div>
	</div>
</body>
</html>