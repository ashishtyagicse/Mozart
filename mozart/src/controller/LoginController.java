package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Login;

@WebServlet("/Login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getParameter("Lout") != null
				&& req.getParameter("Lout").equals("true")) {
			HttpSession Session = null;
			Session = req.getSession(false);
			if (Session != null) {
				Session.invalidate();
				resp.sendRedirect("/mozart/Login.jsp");
			}
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String UserName = "";
		String Password = "";
		String Signupas = "";
		HttpSession Session = null;
		if (request.getParameter("username") != null) {
			UserName = request.getParameter("username").toLowerCase();
			Password = request.getParameter("password");
			Login L = new Login(UserName, Password);
			int Result = L.DoLogin(true);
			if (Result != 0) {
				Session = request.getSession();
				Session.setAttribute("loginname", UserName);
				Session.setAttribute("userid", L.UserId);
				Session.setAttribute("type", L.AccountType);
				switch (L.AccountType) {
				case "user":
					response.sendRedirect("/mozart/Home.jsp");
					break;
				case "artist":
					response.sendRedirect("/mozart/ArtistHome.jsp");
					break;
				case "band":
					response.sendRedirect("/mozart/BandHome.jsp");
					break;
				default:
					response.sendRedirect("/mozart/Login.jsp");
					break;
				}
			} else {

			}
		} else if (request.getParameter("Signupas") != null) {
			Signupas = request.getParameter("Signupas");
			Session = request.getSession(false);
			if (Session != null) {
				Session.invalidate();
			}
			switch (Signupas) {
			case "USER":
				response.sendRedirect("/mozart/Signup.jsp");
				break;
			case "ARTIST":
				response.sendRedirect("/mozart/SignupArtist.jsp");
				break;
			case "BAND":
				response.sendRedirect("/mozart/SignupBand.jsp");
				break;
			}
		}
	}
}
