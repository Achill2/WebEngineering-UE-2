package at.ac.tuwien.big.we14.lab2.api.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.impl.JSONQuestionDataProvider;
import at.ac.tuwien.big.we14.lab2.api.impl.Round;
import at.ac.tuwien.big.we14.lab2.api.impl.ServletQuizFactory;

public class QuizData {
	
	public static final int NUMBEROFROUNDS = 5; 
	public static final int NUMBEROFQUESTIONSPERROUND = 3;
	
	// player - vorerst nur String
	private String player1;
	private String player2;
	
	private QuestionDataProvider provider;
	
	private List<Category> categories;
	
	private List<Round> rounds;
	
	private Random randomGenerator;
	
	
	
	public QuizData() {
		player1 = "Player 1";
		player2 = "Player 2";
		
		provider = QuizFactory.INSTANCE.createQuestionDataProvider();
		categories = provider.loadCategoryData();
		
		rounds = new ArrayList<Round>();
		
		randomGenerator = new Random();
		
		// initialize first round
		nextRound();
		
	}
	
	/**
	 * selects a category randomly that has not yet been selected for a Round 
	 * @return the category fulfilling the criteria or null if no such category exists
	 */
	private Category selectCategory() {
		
		boolean valid = false;
		Category c = null;
		
		while(!valid) {
			int randomIndex = randomGenerator.nextInt(categories.size());
			c = categories.get(randomIndex);
			valid = true;
			// check if the category has already been selected in another Round
			for (int i = 0; i < rounds.size(); i++) {
				if (rounds.get(i).getCategory().equals(c)) {
					valid = false;
				}
			}
		}
		
		return c;
		
	}
	
	/**
	 * if the current round is not the last round a new round is initialized and
	 * 'true' is returned and a new Round is added to the list. If the current round is the last round 'false' is returned
	 * @return
	 */
	public boolean nextRound() {
		if (rounds.size() < NUMBEROFROUNDS) {
			
			// initialize next round
			Round nextRound = new Round(NUMBEROFQUESTIONSPERROUND, selectCategory());
			rounds.add(nextRound);
			return true;
		}
		return false;
	}
	
	/**
	 * @return the current round
	 */
	public Round getCurrentRound() {
		return rounds.get(rounds.size()-1);
	}
	
	// -------- mainly for test purpose
	public String getPlayer1Name() {
		return player1;
	}
	
	public String getPlayer2Name() {
		return player2;
	}
	
	
	

}
