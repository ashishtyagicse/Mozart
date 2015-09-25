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

import model.CreateConcert;

@WebServlet("/CreateConcertController")
public class CreateConcertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateConcertController() {
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
		String Concertname = "";		
		int BandId = -1;
		String ConcertStartDate = "";		
		String ConcertEndDate = "";
		String Venue = "";		
		String TicketAmount = "";
		String TicketsStartDate = "";		
		String TicketWebLink="";
		HttpSession Session = request.getSession(false);
		
		Concertname = request.getParameter("cname");
		BandId = (int) Session.getAttribute("userid");
		ConcertStartDate = request.getParameter("concertsdt");		
		ConcertEndDate = request.getParameter("concertedt");
		Venue = request.getParameter("select");
		TicketAmount = request.getParameter("ticketcost");
		TicketsStartDate = request.getParameter("ticketsdt");
		TicketWebLink = request.getParameter("ticketlink");
		
		
		
		RequestDispatcher Redirect = null;		
		CreateConcert S = new CreateConcert(Concertname, BandId, ConcertStartDate, ConcertEndDate, Venue, TicketAmount, TicketsStartDate,TicketWebLink);		
		int Result = S.DoLogin();		
		if (Result != 0) {			
			Redirect = request.getRequestDispatcher("/BandHome.jsp");						
		} else {
			Redirect = request.getRequestDispatcher("/Login.jsp");
		}
		Redirect.forward(request, response);
		
	}

}
