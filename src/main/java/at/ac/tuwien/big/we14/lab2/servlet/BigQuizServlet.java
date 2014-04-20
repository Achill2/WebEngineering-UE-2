package at.ac.tuwien.big.we14.lab2.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.beans.QuizData;
import at.ac.tuwien.big.we14.lab2.api.impl.ServletQuizFactory;

public class BigQuizServlet extends HttpServlet {
	
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action == null) {
			return;
		}
		
		// start the Game, go to the question.jpa
		if(action.equals("startGame")) { 
			// test Aufruf TODO 
			
			QuizFactory factory = new ServletQuizFactory(this.getServletContext());
			
			
			HttpSession session = request.getSession(true);
	        session.setAttribute("raceData", new QuizData(factory.createQuestionDataProvider()));
			
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/question.jsp");
            dispatcher.forward(request, response);
            

		}
		
	}  
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// test
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<h1>test</h1>");
		
	}
	
	
	
}