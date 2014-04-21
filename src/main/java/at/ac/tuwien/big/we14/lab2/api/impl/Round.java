package at.ac.tuwien.big.we14.lab2.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.Winner;

/**
 * holds the information of one question round
 * @author FAUser
 *
 */
public class Round {
	
	private Category category;
	private int indexOfCurrentQuestion = -1;
	private Question[] questions;
	private List<Answer> answersPlayer1;
	private List<Answer> answersPlayer2;
	
	private Random randomGenerator;
	
	private Winner winner;

	
	public Round(int numberOfQuestions, Category category) {
		this.category = category;
		
		randomGenerator = new Random();
		questions = new Question[numberOfQuestions];
		
		answersPlayer1 = new ArrayList<Answer>();
		answersPlayer2 = new ArrayList<Answer>();
		
		
		// initialize questions etc
		initializeQuestions(numberOfQuestions, category);
		indexOfCurrentQuestion = 0;
		
		// initialize answers
		for(int i = 0; i < questions.length; i++) {
			answersPlayer1.add(new Answer());
			answersPlayer2.add(new Answer());
		}
		
		setWinner(Winner.NOTFINISHED);
	}
	
	/**
	 * initialize the questions for this round of the given category
	 * @param category
	 */
	private void initializeQuestions(int numberOfQuestions, Category category) {
		int i = 0;
		while (i < numberOfQuestions) {
			Question q = selectQuestion(category);
			// check if the question has already been selected
			boolean valid = true;
			for (int j = 0; j < i; j++) {
				if (questions[j].equals(q)) {
					valid = false;
				}
			}
			if (valid) {
				questions[i] = q;
				i++;
			}
		}
	}
	
	/**
	 * randomly selects a question from the given category
	 * @param category
	 * @return
	 */
	private Question selectQuestion(Category category) {
		List<Question> questionList = category.getQuestions();
		int randomIndex = randomGenerator.nextInt(questionList.size());
		return questionList.get(randomIndex);
	}
	
	public String getCategoryName() {
		return category.getName();
	}
	
	public Category getCategory() {
		return category;
	}

	public Question[] getQuestions() {
		return questions;
	}

	/**
	 * returns the current Question or Null when there is no current Question yet
	 * @return
	 */
	public Question getCurrentQuestion() {
		if (indexOfCurrentQuestion >= 0) {
			return questions[indexOfCurrentQuestion];
		} else {
			return null;
		}
	}
	
	/**
	 * if the current Question is not the last question true is returned, false otherwise
	 * @return
	 */
	public boolean nextQuestion() {
		if (indexOfCurrentQuestion != (questions.length - 1)) {
			indexOfCurrentQuestion++;
			return true;
		} else {
			return false;
		}
		
	}

	public List<Answer> getAnswersPlayer2() {
		return answersPlayer2;
	}

	public List<Answer> getAnswersPlayer1() {
		return answersPlayer1;
	}

	public int getIndexOfCurrentQuestion() {
		return indexOfCurrentQuestion;
	}

	public Winner getWinner() {
		return winner;
	}

	public void setWinner(Winner winner) {
		this.winner = winner;
	}


	
	




	

	
}
