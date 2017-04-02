package filter;

import java.util.ArrayList;
import models.Word;

public class NaiveBayes {
	private ArrayList<Word> words = new ArrayList<Word>();
	private int totalSpam = 0;
	private int totalHam = 0;
	
	public ArrayList<Word> getWords() {
		return words;
	}
	public void setWords(ArrayList<Word> words) {
		this.words = words;
	}
	public int getTotalSpam() {
		return totalSpam;
	}
	public void setTotalSpam(int totalSpam) {
		this.totalSpam = totalSpam;
	}
	public int getTotalHam() {
		return totalHam;
	}
	public void setTotalHam(int totalHam) {
		this.totalHam = totalHam;
	}
	
	public void addHam() {
		this.totalHam += 1;
	}
	
	public void addSpam() {
		this.totalSpam += 1;
	}
}
