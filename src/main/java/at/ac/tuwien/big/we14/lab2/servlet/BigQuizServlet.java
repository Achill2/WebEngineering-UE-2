package at.ac.tuwien.big.we14.lab2.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import at.ac.tuwien.big.we14.lab2.api.AnswerStatus;
import at.ac.tuwien.big.we14.lab2.api.Choice;
import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.Winner;
import at.ac.tuwien.big.we14.lab2.api.beans.QuizData;
import at.ac.tuwien.big.we14.lab2.api.impl.Answer;
import at.ac.tuwien.big.we14.lab2.api.impl.ServletQuizFactory;

public class BigQuizServlet extends HttpServlet {
	
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action == null) {
			return;
		}
		
//		// TODO remove test
//		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		out.println("<html>");
//		out.println("<body>");
//		out.println("<h1>Hello " + action + "</h1>");
//		out.println("</body>");
//		out.println("</html>");
		
		
		// start the Game, go to the question.jpa
		if(action.equals("Quiz starten") || action.equals("Neues Spiel")) { 
			// test Aufruf TODO 
			
			QuizFactory.INSTANCE = ServletQuizFactory.init(this.getServletContext()); // TODO auslagern
			
			HttpSession session = request.getSession(true);
	        session.setAttribute("quizData", new QuizData());
			
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/question.jsp");
            dispatcher.forward(request, response);
            

		} else if (action.equals("Weiter")) {
			// check if there is a nextRound or if the game ends
			QuizData data = (QuizData)request.getSession().getAttribute("quizData");
			if (data.nextRound()) {
				// next round
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/question.jsp");
	            dispatcher.forward(request, response);
			} else {
				// game ends
				// calculate Winner
				calculateWinnerOfGame(data);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/finish.jsp");
	            dispatcher.forward(request, response);
	            
	            // TODO use local storage to save last game played etc.
			}
		}
		
	}  
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// get next question or show end of round/end of game
		
		QuizData data = (QuizData)req.getSession().getAttribute("quizData");
		
		// check answers
		String[] selected_choices = req.getParameterValues("choice");
		boolean correct_answer = checkAnswer(selected_choices, data);
		// get the time
		long time_left = Long.parseLong(req.getParameter("timeleftvalue"));
		long time_taken = data.getCurrentRound().getCurrentQuestion().getMaxTime() - time_left;
		
		
		// TODO get which user ?! 
		
		// set the answer status and time-value
		int index = data.getCurrentRound().getIndexOfCurrentQuestion();
		if (correct_answer) {
			data.getCurrentRound().getAnswersPlayer1().get(index).setStatus(AnswerStatus.CORRECT);
		} else {
			data.getCurrentRound().getAnswersPlayer1().get(index).setStatus(AnswerStatus.INCORRECT);
		}
		data.getCurrentRound().getAnswersPlayer1().get(index).setTime(time_taken);
		
		// check if there is another question in this round or if the round is over
		if (data.getCurrentRound().nextQuestion()) {
			// another question exists in this round
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/question.jsp");
            dispatcher.forward(req, res);
            // TODO wirklich wieder aufrufen oder gibt es ein question.jsp refresh?!
		} else {
			// round is over, show statistics
			calculateWinnerOfRound(data);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/roundcomplete.jsp");
            dispatcher.forward(req, res);
			
		}
		
	}
	
	/**
	 * check if the answer consisting of the selected choices is correct and returns true if so
	 * @param selected_choices - the choices selected by the user
	 * @param data - the quizData-session object containing question and choices-information
	 * @return true if the answer is correct, false otherwise
	 */
	private boolean checkAnswer(String[] selected_choices, QuizData data) {
		
		List<Choice> correct_choices = data.getCurrentRound().getCurrentQuestion().getCorrectChoices();
		List<Integer> correct_ids = new ArrayList<Integer>();
		for (Choice c : correct_choices) {
			correct_ids.add(c.getId());
		}
		
		// (at least one answer is correct so no selected choices (null) mean incorrect answer
		if (selected_choices==null) {  	
			return false;
		}
		
		List<String> selected_ids = Arrays.asList(selected_choices);
		// check if enough choices have been selected
		if (correct_choices.size() != selected_ids.size()) {
			return false;
		}
		
		boolean correct_answer = true;
		if (correct_answer) {
			for (int i = 0; correct_answer && i < selected_ids.size(); i++) {
				int id = Integer.parseInt(selected_ids.get(i));
				if (!correct_ids.contains(id)) {
					correct_answer = false;
					return false;
				}
			}
		}
		
		return correct_answer;
	}
	
	/**
	 * calculates the winner of the current Round
	 * @param data the QuizData of the session
	 */
	private void calculateWinnerOfRound(QuizData data) {
		List<Answer> answersPlayer1 = data.getCurrentRound().getAnswersPlayer1();
		List<Answer> answersPlayer2 = data.getCurrentRound().getAnswersPlayer2();
		
		long time_sum_player1 = 0;
		long time_sum_player2 = 0;
		
		List<Answer> correctAnswersPlayer1 = new ArrayList<Answer>();
		for (Answer a : answersPlayer1) {
			if (a.getStatus() == AnswerStatus.CORRECT) {
				correctAnswersPlayer1.add(a);
				time_sum_player1 += a.getTime();
			}
		}
		
		List<Answer> correctAnswersPlayer2 = new ArrayList<Answer>();
		for (Answer a : answersPlayer2) {
			if (a.getStatus() == AnswerStatus.CORRECT) {
				correctAnswersPlayer2.add(a);
				time_sum_player2 += a.getTime();
			}
		}
		
		// TODO
		if (correctAnswersPlayer1.size() > correctAnswersPlayer2.size()) {
			// player 1 wins the round
			data.getCurrentRound().setWinner(Winner.PLAYER1);
			data.getPlayer1().incrementNumberOfWonRounds();
		} else if (correctAnswersPlayer1.size() < correctAnswersPlayer2.size()) {
			// player 2 wins the round
			data.getCurrentRound().setWinner(Winner.PLAYER2);
			data.getPlayer2().incrementNumberOfWonRounds();
		} else {
			// same amount of correct answers -> look at the time
			if (time_sum_player1 < time_sum_player2) {
				// player 1 wins
				data.getCurrentRound().setWinner(Winner.PLAYER1);
				data.getPlayer1().incrementNumberOfWonRounds();
			} else if (time_sum_player1 > time_sum_player2) {
				// player 2 wins
				data.getCurrentRound().setWinner(Winner.PLAYER2);
				data.getPlayer2().incrementNumberOfWonRounds();
			} else {
				// draw
				data.getCurrentRound().setWinner(Winner.DRAW);
			}
			
		}
	}
	
	/**
	 * calculates the winner of the game
	 * @param data the QuizData from current session
	 */
	private void calculateWinnerOfGame(QuizData data) {
		int points_player1 = data.getPlayer1().getNumberOfWonRounds();
		int points_player2 = data.getPlayer2().getNumberOfWonRounds();
		
		if (points_player1 > points_player2) {
			// player 1 wins
			data.setWinner(Winner.PLAYER1);
		} else if (points_player1 < points_player2 ) {
			// player 2 wins
			data.setWinner(Winner.PLAYER2);
		} else {
			// draw
			data.setWinner(Winner.DRAW);
		}
		
	}
	
	
	
}