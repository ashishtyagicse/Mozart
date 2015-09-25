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

import model.Signup;
import model.UserProfile;

/**
 * Servlet implementation class SignupController
 */
@WebServlet("/ProfileUserController")
public class ProfileUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileUserController() {
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
		String marital = "";
		String Phone = "";
		String Address = "";
		String Gender = "";
		String InterestedIn = "";
		String ReligiousViews = "";
		String PoliticalViews = "";
		String MotherTongue = "";
		String University = "";
		String School = "";
		
		UserFName = request.getParameter("fname");
		UserLName = request.getParameter("lname");
		UserDOB = request.getParameter("dob");		
		UserEMail = request.getParameter("email");
		UserCity = request.getParameter("city");
		LoginName = request.getParameter("logname");
		marital = request.getParameter("marital");
		Phone = request.getParameter("Phone");
		Address = request.getParameter("Address");
		Gender = request.getParameter("Gender");
		InterestedIn = request.getParameter("InterestedIn");
		ReligiousViews = request.getParameter("ReligiousViews");
		PoliticalViews = request.getParameter("PoliticalViews");
		MotherTongue = request.getParameter("MotherTongue");
		University = request.getParameter("University");
		School = request.getParameter("School");
		Password = request.getParameter("pass");
		HttpSession session = request.getSession();
		
		
		RequestDispatcher Redirect = null;		
		UserProfile S = new UserProfile(UserFName, UserLName, UserDOB, UserEMail, UserCity, LoginName, Password,marital,Phone, Address,Gender,InterestedIn,ReligiousViews,PoliticalViews,MotherTongue,University,School,request.getParameterValues("music"), (int) session.getAttribute("userid") );		
		int Result = S.DoLogin();		
		if (Result != 0) {			
			Redirect = request.getRequestDispatcher("/Home.jsp");		
		} else {
			Redirect = request.getRequestDispatcher("/Login.jsp");
		}
		Redirect.forward(request, response);
		
	}

}

