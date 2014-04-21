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
			
			QuizFactory.INSTANCE = ServletQuizFactory.init(this.getServletContext()); // TODO auslagern
			
			HttpSession session = request.getSession(true);
	        session.setAttribute("quizData", new QuizData());
			
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/question.jsp");
            dispatcher.forward(request, response);
            

		} else if (action.equals("nextRound")) {
			// check if there is a nextRound or if the game ends
			QuizData data = (QuizData)request.getSession().getAttribute("quizData");
			if (data.nextRound()) {
				// next round
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/question.jsp");
	            dispatcher.forward(request, response);
			} else {
				// game ends
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/finish.jsp");
	            dispatcher.forward(request, response);
			}
		}
		
	}  
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// get next question or show end of round/end of game
		
		QuizData data = (QuizData)req.getSession().getAttribute("quizData");
		if (data.getCurrentRound().nextQuestion()) {
			// another question exists in this round
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/question.jsp");
            dispatcher.forward(req, res);
            // TODO wirklich wieder aufrufen oder gibt es ein question.jsp refresh?!
		} else {
			// round is over, show statistics
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/roundcomplete.jsp");
            dispatcher.forward(req, res);
			
		}
		
	}
	
	
	
}