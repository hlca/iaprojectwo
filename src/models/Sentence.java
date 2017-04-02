package models;

public class Sentence {
	private String type;
	private String calculatedType; 
	private String sentence;
	private double probability = 0;
	
	public Sentence(String type, String sentence) {
		super();
		this.type = type;
		this.sentence = sentence;
	}
	
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public String getCalculatedType() {
		return calculatedType;
	}

	public void setCalculatedType(String calculatedType) {
		this.calculatedType = calculatedType;
	}
	
	
}
