package models;

public class Word {
	private int spamAppareances = 0;
	private int hamAppareances = 0;
	private double spamProbability = 0;
	private double hamProbability = 0;
	private String word;

	public void addSpamAppareances(){
		this.spamAppareances++;
	}
	
	public void addHamAppareances() {
		this.hamAppareances++;
	}
	
	public int getSpamAppareances() {
		return spamAppareances;
	}
	public void setSpamAppareances(int spamAppareances) {
		this.spamAppareances = spamAppareances;
	}
	public int getHamAppareances() {
		return hamAppareances;
	}
	public void setHamAppareances(int hamAppareances) {
		this.hamAppareances = hamAppareances;
	}
	public double getSpamProbability() {
		return spamProbability;
	}
	public void setSpamProbability(double spamProbability) {
		this.spamProbability = spamProbability;
	}
	public double getHamProbability() {
		return hamProbability;
	}
	public void setHamProbability(double hamProbability) {
		this.hamProbability = hamProbability;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	@Override
	public String toString() {
		return "Ham: " + hamAppareances + " Spam: " + spamAppareances + " SpamP: " + spamProbability + " HamP: " + hamProbability +  " Word: " + word;
	}
}
