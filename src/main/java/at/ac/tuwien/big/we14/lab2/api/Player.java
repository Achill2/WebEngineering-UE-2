package at.ac.tuwien.big.we14.lab2.api;

public interface Player {
	
	public String getName();
	
	public int getNumberOfWonRounds();
	
	public void setNumberOfWonRounds(int number);
	
	public void incrementNumberOfWonRounds();
}
