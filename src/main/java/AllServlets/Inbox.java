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
 * Servlet implementation class Inbox
 */
@WebServlet("/Inbox")
public class Inbox extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Inbox() {
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
			out.print("<h1>Inbox</h1>");
			String msg=(String)request.getAttribute("msg");
			if(msg!=null){
				out.print("<p>"+msg+"</p>");
			}
			
			try{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emailersystem", "root", "");
				PreparedStatement ps=con.prepareStatement("select * from messageinfo where receiver=? and draft='no' order by id desc");
				ps.setString(1,email);
				ResultSet rs=ps.executeQuery();
				out.println("<br><br>");
				out.print("<table border='1px' style='width:500px;'>");
				out.print("<tr><td>Sender</td><td>Subject</td><td>Action</td></tr>");
				while(rs.next()){
					out.print("<tr><td>"+rs.getString("sender")+"</td><td><a href='Viewmail?id="+rs.getString(1)+"' title='Click to View Mail'>"+rs.getString("subject")+"</a></td><td><a href='Deletemail?id="+rs.getString(1)+"'>Delete Mail</a></td></tr>");
				}
				out.print("</table>");
				
				con.close();
			}catch(Exception e){out.print(e);}
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
