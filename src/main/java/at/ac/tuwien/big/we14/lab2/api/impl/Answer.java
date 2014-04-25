package at.ac.tuwien.big.we14.lab2.api.impl;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.big.we14.lab2.api.AnswerStatus;
import at.ac.tuwien.big.we14.lab2.api.Choice;

/**
 * represents the answer of one player to one question
 * 
 * @author FAUser
 * 
 */
public class Answer {

	private List<Choice> selectedChoices; // TODO vielleicht die einzelnen
											// choices gar nicht speichern
	private AnswerStatus status;
	private long time = -1;

	public Answer() {
		this.selectedChoices = new ArrayList<Choice>();
		setStatus(AnswerStatus.UNKNOWN);
	}

	/**
	 * adds a selected choice to the list
	 * 
	 * @param choice
	 */
	public void addSelectedChoice(Choice choice) {
		selectedChoices.add(choice);
	}

	public List<Choice> getAllSelectedChoices() {
		return selectedChoices;
	}

	public Choice getSelectedChoice(int index) {
		return selectedChoices.get(index);
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public AnswerStatus getStatus() {
		return status;
	}

	public void setStatus(AnswerStatus status) {
		this.status = status;
	}

}
