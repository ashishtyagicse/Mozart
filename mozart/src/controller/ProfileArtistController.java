package controller;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ArtistProfile;

/**
 * Servlet implementation class SignupController
 */
@WebServlet("/ProfileArtistController")

public class ProfileArtistController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileArtistController() {
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
		String Bio = "";
		
		UserFName = request.getParameter("fname");
		UserLName = request.getParameter("lname");
		UserDOB = request.getParameter("dob");		
		UserEMail = request.getParameter("email");
		UserCity = request.getParameter("city");
		LoginName = request.getParameter("logname");
		Password = request.getParameter("pass");
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
		Bio = request.getParameter("bio");
		HttpSession session = request.getSession();
		RequestDispatcher Redirect = null;		
		ArtistProfile S = new ArtistProfile(UserFName, UserLName, UserDOB, UserEMail, UserCity, LoginName, Password,marital,Phone, Address,Gender,InterestedIn,ReligiousViews,PoliticalViews,MotherTongue,University,School, Bio,request.getParameterValues("music"), (int) session.getAttribute("userid") );		
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
