package AllServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Viewmail
 */
@WebServlet("/Viewmail")
public class Viewmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Viewmail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String email= (String) request.getSession().getAttribute("email");
		out.println("<html><body><h1>Welcome !"); 
		request.getRequestDispatcher("welcome.html").include(request, response);
		
		HttpSession session=request.getSession(false);
		if(session==null){
			response.sendRedirect("index.html");
		}else{
			
			
			int id=Integer.parseInt(request.getParameter("id"));
			
			try{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emailersystem", "root", "");
				PreparedStatement ps=con.prepareStatement("select * from messageinfo where id=?");
				ps.setInt(1,id);
				ResultSet rs=ps.executeQuery();
				if(rs.next()){
					out.print("<h1>"+rs.getString("subject")+"</h1><hr/>");
					out.print("<p><b>Message:</b><br/> "+rs.getString("message")+" <br/> <b>By: "+rs.getString("sender")+"</b></p>");
					out.print("<p><a href='Deletemail?id="+rs.getString(1)+"'>Delete Mail</a></p>");			
									
				}
				
				con.close();
			}catch(Exception e){out.print(e);}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
