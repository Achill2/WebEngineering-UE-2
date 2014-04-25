package at.ac.tuwien.big.we14.lab2.api.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Player;
import at.ac.tuwien.big.we14.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.Winner;
import at.ac.tuwien.big.we14.lab2.api.impl.Round;
import at.ac.tuwien.big.we14.lab2.api.impl.SimplePlayer;

public class QuizData {

	public static final int NUMBEROFROUNDS = 5;
	public static final int NUMBEROFQUESTIONSPERROUND = 3;

	// player - vorerst nur String
	private Player player1;
	private Player player2;
	private Winner winner;

	private QuestionDataProvider provider;

	private List<Category> categories;

	private List<Round> rounds;

	private Random randomGenerator;

	public QuizData() {
		player1 = new SimplePlayer("Peter");
		player2 = new SimplePlayer("Susi");

		setWinner(Winner.NOTFINISHED);

		provider = QuizFactory.INSTANCE.createQuestionDataProvider();
		categories = provider.loadCategoryData();

		rounds = new ArrayList<Round>();

		randomGenerator = new Random();

		// initialize first round
		nextRound();

	}

	/**
	 * selects a category randomly that has not yet been selected for a Round
	 * 
	 * @return the category fulfilling the criteria or null if no such category
	 *         exists
	 */
	private Category selectCategory() {

		boolean valid = false;
		Category c = null;

		while (!valid) {
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
	 * 'true' is returned and a new Round is added to the list. If the current
	 * round is the last round 'false' is returned
	 * 
	 * @return
	 */
	public boolean nextRound() {
		if (rounds.size() < NUMBEROFROUNDS) {

			// initialize next round
			Round nextRound = new Round(NUMBEROFQUESTIONSPERROUND,
					selectCategory());
			rounds.add(nextRound);
			return true;
		}
		return false;
	}

	/**
	 * @return the current round
	 */
	public Round getCurrentRound() {
		return rounds.get(getNumberOfCurrentRound() - 1);
	}

	/**
	 * 
	 * @return number of current Round
	 */
	public int getNumberOfCurrentRound() {
		return rounds.size();
	}

	// -------- mainly for test purpose
	public String getPlayer1Name() {
		return player1.getName();
	}

	public String getPlayer2Name() {
		return player2.getName();
	}

	public Player getPlayer2() {
		return player2;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Winner getWinner() {
		return winner;
	}

	public void setWinner(Winner winner) {
		this.winner = winner;
	}

}
