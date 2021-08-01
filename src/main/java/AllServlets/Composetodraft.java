package AllServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Composetodraft
 */
@WebServlet("/Composetodraft")
public class Composetodraft extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Composetodraft() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		  LocalDateTime now = LocalDateTime.now();    
		String receiver=request.getParameter("to");
		String subject=request.getParameter("subject");
		String message=request.getParameter("message");
		message=message.replaceAll("\n","<br/>");
		String email=(String)request.getSession(false).getAttribute("email");
		int status=0;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emailersystem", "root", "");
			PreparedStatement ps=con.prepareStatement("insert into messageinfo(sender,receiver,subject,message,draft,messagedate) values(?,?,?,?,?,?)");
			ps.setString(1,email);
			ps.setString(2,receiver);
			ps.setString(3,subject);
			ps.setString(4,message);
			ps.setString(5,"yes");
			ps.setString(6,dtf.format(now));
			
			status=ps.executeUpdate();
						
		}catch(Exception e){System.out.println(e);}
		if(status>0){
			request.setAttribute("msg","message successfully saved into draft");
			request.getRequestDispatcher("Draft").forward(request, response);
		}		
		
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
