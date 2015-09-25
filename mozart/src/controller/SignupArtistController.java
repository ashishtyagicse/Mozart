package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.http.HttpSession;

import model.Login;
import model.Signup;
import model.SignupArtist;

@WebServlet("/SignupArtist")

public class SignupArtistController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupArtistController() {
        super();
    }
    
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String UserFName = "";		
		String UserLName = "";
		String UserDOB = "";		
		String UserEMail = "";
		String UserCity = "";		
		String LoginName = "";
		String Password = "";
		HttpSession Session = null;
		int Result = 0;
		
		UserFName = request.getParameter("fname");
		UserLName = request.getParameter("lname");
		UserDOB = request.getParameter("dob");		
		UserEMail = request.getParameter("email");
		UserCity = request.getParameter("city");
		LoginName = request.getParameter("logname");
		Password = request.getParameter("pass");		
		Login L = new Login(LoginName, Password);
		Result = L.DoLogin(false);
		if (Result != 0) {
			SignupArtist S = new SignupArtist(UserFName, UserLName, UserDOB, UserEMail, UserCity, LoginName, Password);	
			Result = S.CreateArtist();
			if (Result != 0) {				
				Result = L.DoLogin(true);
				if (Result != 0) {
					Session = request.getSession(false);
					if (Session != null) {
						Session.invalidate();
					}
					Session = request.getSession();
					Session.setAttribute("loginname", LoginName);
					Session.setAttribute("userid", L.UserId);
					Session.setAttribute("type", L.AccountType);
					response.sendRedirect("/mozart/HomeArtist.jsp");
				} else {
					response.sendRedirect("/mozart/Login.jsp");
				}
			}
		} else {
			request.setAttribute("error", "true");			
			request.setAttribute("errortext", "Login name not available \\nselect a different login name");
			request.setAttribute("fname", UserFName);
			request.setAttribute("lname", UserLName);
			request.setAttribute("dob", UserDOB);
			request.setAttribute("email", UserEMail);
			request.setAttribute("city", UserCity);
			RequestDispatcher Redirect = null;
			Redirect = request.getRequestDispatcher("/SignupArtist.jsp");
			Redirect.forward(request, response);		
		}		
	}
}