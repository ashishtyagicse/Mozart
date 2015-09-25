package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import model.BandProfile;
import model.Signup;

/**
 * Servlet implementation class SignupController
 */
@WebServlet("/ProfileBandController")
public class ProfileBandController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileBandController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//	}

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
		String Phone = "";
		
		
		UserFName = request.getParameter("fname");
		UserLName = request.getParameter("lname");
		UserDOB = request.getParameter("dob");		
		UserEMail = request.getParameter("email");
		UserCity = request.getParameter("Address");
		LoginName = request.getParameter("logname");
		Password = request.getParameter("pass");
		Phone = request.getParameter("Phone");
		HttpSession session = request.getSession();
		
		RequestDispatcher Redirect = null;		
		BandProfile S = new BandProfile(UserFName, UserLName, UserDOB, UserEMail, UserCity, LoginName, Password,Phone,request.getParameterValues("music"), (int) session.getAttribute("userid") );		
		int Result = S.DoLogin();		
		if (Result != 0) {			
			Redirect = request.getRequestDispatcher("/Home.jsp");
			request.setAttribute("UserName", LoginName);			
		} else {
			Redirect = request.getRequestDispatcher("/Login.jsp");
		}
		Redirect.forward(request, response);
		
	}

}

