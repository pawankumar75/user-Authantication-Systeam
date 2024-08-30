package com.pawan.reg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class NewPassword extends HttpServlet 
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		HttpSession session = req.getSession();
		String newPassword = req.getParameter("password");
		String confPassword = req.getParameter("confPassword");
		RequestDispatcher dispatcher = null;
		if (newPassword != null && confPassword != null && newPassword.equals(confPassword)) {

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/signup?useSSL=false", "root","12345");
				PreparedStatement pst = con.prepareStatement("update users set upass = ? where uemail = ? ");
				pst.setString(1, newPassword);
				pst.setString(2, (String) session.getAttribute("email"));

				int rowCount = pst.executeUpdate();
				if (rowCount > 0) {
					req.setAttribute("status", "resetSuccess");
					dispatcher = req.getRequestDispatcher("login.jsp");
				} else {
					req.setAttribute("status", "resetFailed");
					dispatcher = req.getRequestDispatcher("login.jsp");
				}
				dispatcher.forward(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

}
