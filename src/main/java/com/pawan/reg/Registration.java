package com.pawan.reg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Registration extends HttpServlet
{
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
	    String name1= req.getParameter("name");
	    String email1= req.getParameter("email");
	    String pass1= req.getParameter("pass");
	    String repass1= req.getParameter("re_pass");
	    String contact1= req.getParameter("contact");
	    
	    RequestDispatcher dispatcher=null;
	    Connection con = null;
	    

		if(name1 == null || name1.equals("")) 
		{
			req.setAttribute("status", "invalidname");
			dispatcher = req.getRequestDispatcher("registration.jsp"); 
			dispatcher.forward(req, resp);
		}
		if(email1 == null || email1.equals("")) 
		{
			req.setAttribute("status", "invalidemail");
			dispatcher = req.getRequestDispatcher("registration.jsp"); 
			dispatcher.forward(req, resp);
		}
		if(pass1 == null || pass1.equals("")) 
		{
			req.setAttribute("status", "invalipassword");
			dispatcher = req.getRequestDispatcher("registration.jsp"); 
			dispatcher.forward(req, resp);
		}
		else if(!pass1.equals(repass1))
		{
			req.setAttribute("status", "isNotEquallPassword");
			dispatcher = req.getRequestDispatcher("registration.jsp"); 
			dispatcher.forward(req, resp);
		}
//		if(contact1 == null && contact1.length()>10 ) 
//		{
//			req.setAttribute("status", "invalidcontact");
//			dispatcher = req.getRequestDispatcher("registration.jsp"); 
//			dispatcher.forward(req, resp);
//		}
		 if(contact1.length()>10)
		{
			req.setAttribute("status", "invalidnumber");
			dispatcher = req.getRequestDispatcher("registration.jsp"); 
			dispatcher.forward(req, resp);
		}
	    
	    try 
	    {
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/signup?useSSL=false","root","12345");
	    	PreparedStatement ps = con.prepareStatement("insert into users(uname,uemail,upass,ucontact) values(?,?,?,?)");
	    	
	    	ps.setString(1, name1);
	    	ps.setString(2, email1);
	    	ps.setString(3, pass1);
	    	ps.setString(4, contact1); 
	    	
	    	int rowCount = ps.executeUpdate();
	    	dispatcher= req.getRequestDispatcher("registration.jsp");
	    	
	    	if(rowCount>0) 
	    	{
	    		req.setAttribute("status", "success");
	    		
	    	}
	    	else 
	    	{
	    		req.setAttribute("status", "failed");
	    	}
	    	dispatcher.forward(req, resp);
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    finally 
	    {
	    	try 
	    	{
	    		con.close();
	    	}
	    	catch(Exception e) 
	    	{
	    		e.printStackTrace();
	    	}
	    	
	    }
		
	}

}
