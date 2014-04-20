package at.ac.tuwien.big.we14.lab2.api.impl;

import java.util.List;
import java.util.Random;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Question;

/**
 * holds the information of one question round
 * @author FAUser
 *
 */
public class Round {
	
	private Category category;
	private int indexOfCurrentQuestion = -1;
	private Question[] questions;
	private Random randomGenerator;

	
	public Round(int numberOfQuestions, Category category) {
		this.category = category;
		
		randomGenerator = new Random();
		questions = new Question[numberOfQuestions];
		
		
		// initialize questions etc
		initializeQuestions(numberOfQuestions, category);
		indexOfCurrentQuestion = 0;
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
	

	
}
