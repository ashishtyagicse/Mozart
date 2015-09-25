<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mozart Login</title>
<link rel="stylesheet" type="text/css" href="CSS/Login.css">
<script type="text/javascript">
	function AlreadyLogedIn() {
<%			HttpSession Session = null;
			Session = request.getSession(false);
			if (Session.getAttribute("userid") != null) {
				switch ((String) Session.getAttribute("type")) {
					case "user" :
						out.print("window.location = '/mozart/Home.jsp';");
						break;
					case "artist" :
						out.print("window.location = '/mozart/ArtistHome.jsp';");
						break;
					case "band" :
						out.print("window.location = '/mozart/BandHome.jsp';");
						break;
					default :
						out.print("window.location = '/mozart/Login.jsp';");
						break;
				}
			}%>
	}
	function flipView() {
		AlreadyLogedIn();
		document
				.getElementById('signup')
				.addEventListener(
						'click',
						function(event) {
							document.title = "Mozart Signup";
							event.preventDefault();
							document.getElementById('side-2').className = 'flip flip-side-1';
							document.getElementById('side-1').className = 'flip flip-side-2';

						}, false);

		document.getElementById('Login').addEventListener('click',
				function(event) {
					document.title = "Mozart Login";
					event.preventDefault();
					document.getElementById('side-2').className = 'flip';
					document.getElementById('side-1').className = 'flip';

				}, false);
	}
</script>
</head>
<body id="body" onload="flipView();">
	<div id="banner-top">
		<div id="banner">
			<H1 id="heading-mozart">Welcome To Mozart</H1>
			<H3 style="margin: 0px; padding: 10px">Please Login to proceed</H3>
		</div>
	</div>
	<div style="position: relative;" align="center">
		<div id="side-1" class="flip">
			<div align="center" id="main">
				<div align="center" id="Login-main">
					<div align="center" id="Login-Heading">
						<H2 style="margin: 0px">Login</H2>
					</div>
					<form action="Login" method="post">
						<div id="formrowone">
							<div style="float: left;">Login Name:</div>
							<div style="float: right;">
								<input type="text" name="username" id="UserName" />
							</div>
						</div>
						<BR>
						<div id="formrowtwo">
							<div style="float: left;">Password:</div>
							<div style="float: right;">
								<input type="password" name="password" id="Password" />
							</div>
						</div>
						<BR>
						<div id="submit" align="center">
							<input type="submit" value="Login" id="submitbutton">
						</div>
						<BR> <BR>
					</form>
				</div>
			</div>
			<div align="center">
				<h3>
					<a id="signup" href="#"> Create an account </a>
				</h3>
			</div>
		</div>
		<div id="side-2" class="flip">
			<div align="center" id="main">
				<div align="center" id="Login-main" style="width: 250px">
					<div align="center" id="Login-Heading">
						<H2 style="margin: 0px">Signup As</H2>
					</div>
					<form action="Login" method="post">
						<div id="Signupformrowone">
							<div style="float: left;">User:</div>
							<div style="float: right;">
								<input type="radio" name="Signupas" value="USER"
									checked="checked" />
							</div>
						</div>
						<BR>
						<div id="Signupformrowone">
							<div style="float: left;">Artist:</div>
							<div style="float: right;">
								<input type="radio" name="Signupas" value="ARTIST" />
							</div>
						</div>
						<BR>
						<div id="Signupformrowtwo">
							<div style="float: left;">Band:</div>
							<div style="float: right;">
								<input type="radio" name="Signupas" value="BAND" />
							</div>
						</div>
						<BR>
						<div id="submit" align="center" style="width: 200px;">
							<input type="submit" value="Continue To Signup" id="submitbutton">
						</div>
						<BR> <BR>
					</form>
				</div>
			</div>
			<div align="center">
				<h3>
					<a id="Login" href="#"> Login </a>
				</h3>
			</div>
		</div>
	</div>
</body>
</html>