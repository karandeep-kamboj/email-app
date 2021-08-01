package AllServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

;

/**
 * Servlet implementation class deletemail
 */
@WebServlet("/Deletemail")
public class Deletemail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deletemail() {
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
				PreparedStatement ps=con.prepareStatement("delete from messageinfo where id=?");
				ps.setInt(1,id);
				int i=ps.executeUpdate();
				if(i>0){
					out.println("Mail successfully deleted permanently!");
					request.getRequestDispatcher("Delete").forward(request, response);
				}
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
