<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Band Account</title>
<link rel="stylesheet" type="text/css" href="CSS/Signup.css">
<script type="text/javascript">
function servervalidate() {
	if(<% if(request.getAttribute("Success") == null) {out.print("false");} else {out.print(request.getAttribute("Success"));}%>){
		window.alert("Signup Successfull \nAs part of our security policy we will \ndo an backround verification for this account \nand once done will send your login details by email.");
		window.location = '/mozart/Login.jsp';
	}
	if (<% if(request.getAttribute("error") == null) {out.print("false");} else {out.print(request.getAttribute("error"));}%>) {
		document.getElementById('ErrorText').innerHTML += '<% out.print(request.getAttribute("errortext"));%>';
		document.getElementById('ErrorText').style.visibility="visible";
		document.getElementById('fname').value= '<% out.print(request.getAttribute("fname"));%>';
		document.getElementById('lname').value= '<% out.print(request.getAttribute("lname"));%>';
		document.getElementById('dob').value= '<% out.print(request.getAttribute("dob"));%>';
		document.getElementById('email').value= '<% out.print(request.getAttribute("email"));%>';		
		document.getElementById('lognamediv').style.border="double";
		document.getElementById('lognamediv').style.borderColor="red";
	} else {
		document.getElementById('fname').value= '';
		document.getElementById('lname').value= '';
		document.getElementById('dob').value= '';
		document.getElementById('email').value= '';		
		document.getElementById('lognamediv').style.border="none";
		document.getElementById('lognamediv').style.borderColor="";
	}
}
</script>
</head>
<body id="body" onload="servervalidate();">
	<div id="banner-top">
		<div id="banner">
			<H1 id="heading-mozart">Welcome To Mozart</H1>
			<H3 style="margin: 0px; padding: 10px">Fill the form below to
				create an Band account</H3>
		</div>
	</div>
	<div align="center">
		<div align="center" id="main">
			<div align="center" id="Signup-main">
				<div align="center" id="Signup-Heading">
					<H2 style="margin: 0px">Band Signup</H2>
				</div>
				<form action="SignupBand" method="post">
					<div id="ErrorText" style="margin-bottom: 5px; display: inline-block; width: 300px; color: red; visibility: hidden;">
					</div>
					<div id="formrowone">
						<div style="float: left;">Band name:</div>
						<div style="float: right;">
							<input id="fname" name="fname" type="text" value="" />
						</div>
					</div>
					<BR>
					<div id="formrowone">
						<div style="float: left;">Band Biodata:</div>
						<div style="float: right;">
							<input id="lname" name="lname" type="text" value=""  
								style="height: 100px; word-break: break-word;" />
						</div>
					</div>
					<BR>
					<div id="formrowone">
						<div style="float: left;">Band Create Date :</div>
						<div style="float: right;">
							<input id="dob" name="dob" type="text" value="" />
						</div>
					</div>
					<BR>
					<div id="formrowone">
						<div style="float: left;">Email:</div>
						<div style="float: right;">
							<input id="email" name="email" type="text" value="" />
						</div>
					</div>
					<BR>
					<div id="formrowone">
						<div style="float: left;">Login Name:</div>
						<div style="float: right;" id="lognamediv">
							<input id="logname" name="logname" type="text" value="" />
						</div>
					</div>
					<BR>
					<div id="formrowone">
						<div style="float: left;">Password:</div>
						<div style="float: right;">
							<input id="pass" name="pass" type="text" value="" />
						</div>
					</div>
					<BR>
					<div id="formrowtwo">
						<div style="float: left;">Confirm Password:</div>
						<div style="float: right;">
							<input id="cpass" name="cpass" type="text" value="" />
						</div>
					</div>
					<BR>
					<div id="submit" align="center">
						<input type="submit" value="Sign Up" id="submitbutton">
					</div>
					<BR> <BR>
				</form>
			</div>
		</div>
		<div align="center">
			<h3>
				<a id="Login" href="/mozart/Login.jsp">Login</a>
			</h3>
		</div>
	</div>
</body>
</html>