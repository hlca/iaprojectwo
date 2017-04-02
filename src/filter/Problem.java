package filter;

import java.util.ArrayList;

import models.Word;
import utils.FileReader;
import models.Sentence;

public class Problem {

	private String filePath = "/Users/hsingli/Downloads/test_corpus.txt";
	private String testingFilePath = "/Users/hsingli/Desktop/testingSet.txt";
	private ArrayList<Sentence> sentences = new ArrayList<Sentence>();
	private ArrayList<Sentence> trainingSet = new ArrayList<Sentence>();
	private ArrayList<Sentence> validationSet = new ArrayList<Sentence>();
	private ArrayList<Sentence> testingSet = new ArrayList<Sentence>();
	private ArrayList<Sentence> set = new ArrayList<Sentence>();
	
	private ArrayList<Word> filterWords = new ArrayList<Word>();
	
	private double spamProbability = 0;
	private double hamProbability = 0;
	
	private double initialK = 1;
	
	public void classifyNew() {
		//reading the file
		FileReader fr = new FileReader();
		ArrayList<String> lines = fr.readFile(testingFilePath);
		
		for(String line: lines) {
			Sentence s = new Sentence("none", line);
			set.add(s);
		}
		
		evaluate(initialK, set);
		ArrayList<String> finalString = new ArrayList<String>();
		for(Sentence s: set) {
			finalString.add(s.getCalculatedType() + "\t" + s.getSentence());
		}
		
		FileReader.writeFile(finalString);
	}
	
	public void calculateProbabilities() {
		System.out.println("Se calculan las probabilidades");
		int spamOccurences = 0;
		int hamOccurences = 0;
		int spamSentences = 0;
		int hamSentences = 0;
		for(Sentence sentence: trainingSet) {
			String[] words = sentence.getSentence().split(" ");
			String type = sentence.getType();
			
			if(type.equals("ham")){
				hamSentences++;
			}else if(type.equals("spam")){
				spamSentences++;
			}
			
			for(String word: words) {
				//Revisamos que no esté vacío
				if(word.length() > 0){
					boolean added = false;
					for(int i = 0; i<filterWords.size(); i++){
						Word filterWord = filterWords.get(i);
					//for(Word filterWord: filterWords) {
						//Ya existe en la lista
						if(filterWord.getWord().equals(word)) {
							if(type.equals("spam")) {
								filterWord.addSpamAppareances();
								spamOccurences++;
								added = true;
							}else if(type.equals("ham")){
								filterWord.addHamAppareances();
								hamOccurences++;
								added = true;
							}
						}
						if(added) filterWords.set(i, filterWord);
					}	
					if(!added) {
						Word newWord = new Word();
						newWord.setWord(word);
						if(type.equals("spam")) {
							newWord.addSpamAppareances();
							spamOccurences++;
						}else if(type.equals("ham")) {
							newWord.addHamAppareances();
							hamOccurences++;
						}
						filterWords.add(newWord);
						added = true;
					}
				}
			}
		}
		
		for(Word wordF: filterWords) {
			wordF.setHamProbability((double) wordF.getHamAppareances()/(double) hamOccurences);
			wordF.setSpamProbability((double) wordF.getSpamAppareances()/(double) spamOccurences);
		}
		spamProbability = (float) spamSentences/(float) (spamSentences + hamSentences);
		hamProbability = (float) hamSentences/(float) (spamSentences + hamSentences);
	}
	
	public double validate(double k, ArrayList<Word> sentence) {
		double sum = Math.log(spamProbability/hamProbability);
		for(Word word: sentence) {
			sum += Math.log((word.getSpamProbability() + k)/(word.getHamProbability() + k));
		}
		return sum;
	}
	
	
	public int evaluate(double k, ArrayList<Sentence> sentences) {
		int correctas = 0;
		for(Sentence sentence: sentences) {
			ArrayList<Word> evaluationWords = new ArrayList<Word>();
			String[] words = sentence.getSentence().split(" ");
			
			for(String word: words) {
				for(Word filterWord: filterWords) {
					if(filterWord.getWord().equals(word)){
						evaluationWords.add(filterWord);
					}
				}
			}
			double calculation = validate(0, evaluationWords);
			if(sentence.getType().equals("spam")){
				if(calculation > 0) {
					correctas ++;
				}
			}
			if(sentence.getType().equals("ham")){
				if(calculation < 0) {
					correctas ++;
				}
			}
			if(sentence.getType().equals("none")) {
				if(calculation > 0) {
					sentence.setCalculatedType("spam");
				}else {
					sentence.setCalculatedType("ham");
				}
			}
		}
		return correctas;
	}
	
	public void test() {
		System.out.println("Se testea");
		int correctas = evaluate(initialK, testingSet);
		System.out.println("Las correctas fueron: " + correctas);
		System.out.println("Las incorrectas fueron: " + (testingSet.size() - correctas));
	}
	
	
	public void check() {
		System.out.println("Se calcula el mejor K");
		int correctas = 0;
		int bestCorrectas = -1;
		double bestK = initialK;
		boolean bandera = true;
		while (bandera) {
			int currentCorrectas = evaluate(bestK, validationSet);
			
			int plusCorrectas = evaluate(bestK + 1, validationSet);
			
			int minusCorrectas = evaluate(bestK - 1, validationSet);
			
			if(currentCorrectas >= plusCorrectas && currentCorrectas >= minusCorrectas){
				correctas = currentCorrectas;
			}
			if(plusCorrectas >= currentCorrectas && plusCorrectas >= minusCorrectas){
				correctas = plusCorrectas;
				bestK++;
			}
			if(minusCorrectas >= currentCorrectas && minusCorrectas >= plusCorrectas){
				correctas = minusCorrectas;
				bestK--;
			}
			
			if(correctas > bestCorrectas) {
				bestCorrectas = correctas;
			}else if(correctas == bestCorrectas) {
				initialK = bestK;
				bandera = false;
			}
		}
	}
	
	public void classify() {
		System.out.println("Inicia la clasificación");
		int counter = 0;
		
		ArrayList<Sentence> spamSet = new ArrayList<Sentence>();
		ArrayList<Sentence> hamSet = new ArrayList<Sentence>();
		
		for(Sentence sentence: sentences) {
			if(sentence.getType().equals("ham")) {
				hamSet.add(sentence);
			}else {
				spamSet.add(sentence);
			}
		}
		
		for(Sentence sentence: hamSet) {
			if(counter < 8) {
				trainingSet.add(sentence);
				counter ++;
			}else if(counter < 9) {
				validationSet.add(sentence);
				counter ++;
			}else if(counter < 10) {
				testingSet.add(sentence);
				counter = 0;
			}
		}
		
		counter = 0;
		
		for(Sentence sentence: spamSet) {
			if(counter < 8) {
				trainingSet.add(sentence);
				counter ++;
			}else if(counter < 9) {
				validationSet.add(sentence);
				counter ++;
			}else if(counter < 10) {
				testingSet.add(sentence);
				counter = 0;
			}
		}
	}
	
	public void read() {
		FileReader fileReader = new FileReader();
		ArrayList<String> lines = fileReader.readFile(filePath);
		for(String line: lines) {
			String[] lineDetail = line.split("\t");
			Sentence sentence = new Sentence(lineDetail[0], stringCleaner(lineDetail[1]).toLowerCase());
			
			sentences.add(sentence);
		}
	}
	
	public String stringCleaner(String sentence) {
		sentence = sentence.replace(".", " ");
		sentence = sentence.replace(",", " ");
		sentence = sentence.replace("-", " ");
		sentence = sentence.replace("Â", " ");
		sentence = sentence.replace("?", " ");
		sentence = sentence.replace("!", " ");
		sentence = sentence.replace("¡", " ");
		sentence = sentence.replace("&gt;", " ");
		sentence = sentence.replace("&lt;", " ");
		sentence = sentence.replace(";", " ");
		sentence = sentence.replace(":", " ");
		sentence = sentence.replace("*", " ");
		sentence = sentence.replace("(", " ");
		sentence = sentence.replace(")", " ");
		sentence = sentence.replace("#", " ");
		sentence = sentence.replace("'", " ");
		sentence = sentence.replace("\"", " ");
		sentence = sentence.replace("\"", " ");
		sentence = sentence.replace("/", " ");
		sentence = sentence.replace("=D", " ");
		sentence = sentence.replace("=", " ");
		sentence = sentence.replace("¼", " ");
		sentence = sentence.replace("|", " ");
		return sentence;
	}
}
