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
import model.SignupBand;

@WebServlet("/SignupBand")
public class SignupBandController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignupBandController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// protected void doGet(HttpServletRequest request, HttpServletResponse
	// response) throws ServletException, IOException {
	// // TODO Auto-generated method stub
	// }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String UserFName = "";
		String UserLName = "";
		String UserDOB = "";
		String UserEMail = "";
		String LoginName = "";
		String Password = "";
		HttpSession Session = null;
		int Result = 0;
		RequestDispatcher Redirect = null;

		UserFName = request.getParameter("fname");
		UserLName = request.getParameter("lname");
		UserDOB = request.getParameter("dob");
		UserEMail = request.getParameter("email");

		LoginName = request.getParameter("logname");
		Password = request.getParameter("pass");
		Login L = new Login(LoginName, Password);
		Result = L.DoLogin(false);
		if (Result != 0) {
			SignupBand S = new SignupBand(UserFName, UserLName, UserDOB,
					UserEMail, LoginName, Password);
			Result = S.CreateBand();
			if (Result != 0) {
				Session = request.getSession(false);
				if (Session != null) {
					Session.invalidate();
				}
				request.setAttribute("Success", "true");
				Redirect = request.getRequestDispatcher("/SignupBand.jsp");
				Redirect.forward(request, response);
			}
		} else {
			request.setAttribute("error", "true");
			request.setAttribute("errortext",
					"Login name not available \\nselect a different login name");
			request.setAttribute("fname", UserFName);
			request.setAttribute("lname", UserLName);
			request.setAttribute("dob", UserDOB);
			request.setAttribute("email", UserEMail);
			Redirect = request.getRequestDispatcher("/SignupBand.jsp");
			Redirect.forward(request, response);
		}
	}
}
