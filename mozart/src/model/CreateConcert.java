package model;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Date;
public class CreateConcert {
	private String Concertname = "";
	private int BandId = -1;
	private String ConcertStartDate = "";
	private String ConcertEndDate = "";
	private String Venue = "";
	private String TicketAmount = "";
	private String TicketsStartDate = "";
	private String TicketWebLink = "";

	public CreateConcert(String userfname, int bandid, String dob,
			String useremail, String usercity, String loginname, String password,String password1) {
		Concertname = userfname;
		BandId = bandid;
		ConcertStartDate = dob;
		ConcertEndDate = useremail;
		Venue = usercity;
		TicketAmount = loginname;
		TicketsStartDate = password;
		TicketWebLink = password1;
	}

	public int DoLogin() {
		if (this.Concertname != "" && this.BandId != -1
				&& this.ConcertStartDate != "" && this.TicketAmount != ""
				&& this.Venue != "" && this.ConcertEndDate != ""
				&& this.TicketsStartDate != null) {
			connect C = new connect();
			CallableStatement callableStatement = null;

			String insertStoreProc = "{call ConcertDeclared(?,?,?,?,?,?,?,?)}";

			try {
				
				callableStatement = C.DatabaseConnection
						.prepareCall(insertStoreProc);
				callableStatement.setString(1, Concertname);
				callableStatement.setInt(2, BandId);
				callableStatement.setDate(3, java.sql.Date.valueOf(ConcertStartDate.toString()));
				callableStatement.setDate(4, java.sql.Date.valueOf(ConcertEndDate.toString()));
				callableStatement.setInt(5, Integer.parseInt(Venue));
				callableStatement.setInt(6, Integer.parseInt(TicketAmount));
				callableStatement.setDate(7, java.sql.Date.valueOf(TicketsStartDate.toString()));
				callableStatement.setString(8, TicketWebLink);
				callableStatement.executeUpdate();
				System.out.println("Record is inserted into concert table!");

			} catch (SQLException e) {
				C.closeConnection();
			} finally {

				if (callableStatement != null) {
					try {
						callableStatement.close();
					} catch (SQLException e) {
						C.closeConnection();
					}
				}

				if (C.DatabaseConnection != null) {
					try {
						C.DatabaseConnection.close();
					} catch (SQLException e) {
						C.closeConnection();
					}
				}
			}
			C.closeConnection();
			return 1;
		} else {
			return 0;
		}
	}
}
