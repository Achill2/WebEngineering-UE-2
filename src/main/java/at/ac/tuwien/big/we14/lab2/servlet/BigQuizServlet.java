package at.ac.tuwien.big.we14.lab2.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BigQuizServlet extends HttpServlet {
	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// test
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<h1>test</h1>");
		
	}
	
	
	
}