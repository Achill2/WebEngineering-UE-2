package at.ac.tuwien.big.we14.lab2.api;

public enum AnswerStatus {
	CORRECT("correct", "Richtig"), INCORRECT("incorrect", "Falsch"), UNKNOWN("unknown", "Unbekann");
	
	
	private String code;
	private String name;
	private AnswerStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.name;
	}
}
