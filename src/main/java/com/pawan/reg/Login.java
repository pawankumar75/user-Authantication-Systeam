package com.pawan.reg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Login extends HttpServlet 
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		String email1=req.getParameter("username");
		String pass1 = req.getParameter("password");
		
		HttpSession session= req.getSession();
		RequestDispatcher dispatcher=null;
		
		if(email1 == null || email1.equals("")) 
		{
			req.setAttribute("status", "invalidEmail");
			dispatcher = req.getRequestDispatcher("login.jsp"); 
			dispatcher.forward(req, resp);
		}

		if(pass1 == null || pass1.equals("")) 
		{
			req.setAttribute("status", "invalidpassword");
			dispatcher = req.getRequestDispatcher("login.jsp"); 
			dispatcher.forward(req, resp);
		}
		
		try 
		{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/signup", "root", "12345");
		PreparedStatement ps = con.prepareStatement("select*from users where uemail=? and upass=?");
		
		ps.setString(1, email1);
		ps.setString(2, pass1);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next()) 
		{
			session.setAttribute("name", rs.getString("uname"));
			dispatcher = req.getRequestDispatcher("index.jsp");
		}
		else {
			req.setAttribute("status", "failed");
			dispatcher = req.getRequestDispatcher("login.jsp");
		}
		dispatcher.forward(req, resp);
		
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
	}

}
