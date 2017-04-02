package utils;

public class Cleaner {
	public static String cleanWord(String word) {
		word.replace("Â£", "£");
		word.replace("Ãœ", "Ü");
		
		return word;
	}
}
