package at.ac.tuwien.big.we14.lab2.api.impl;

import at.ac.tuwien.big.we14.lab2.api.Player;

public class SimplePlayer implements Player {
	
	private String name;
	private int wonRounds;
	
	public SimplePlayer(String name) {
		this.name = name;
		wonRounds = 0;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getNumberOfWonRounds() {
		return wonRounds;
	}

	@Override
	public void setNumberOfWonRounds(int number) {
		this.wonRounds = number;
		
	}

	@Override
	public void incrementNumberOfWonRounds() {
		wonRounds++;
		
	}

}
