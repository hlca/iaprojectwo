package main;

import filter.Problem;

public class Main {
	public static void main(String[] args) {
		
//		Problem p = new Problem();
//		
//		p.read();
//		p.classify();
//		p.calculateProbabilities();
//		p.check();
//		p.test();
//		p.classifyNew();
		int x = 0;
		String insert = "INSERT INTO 'akj dkfj as' JALKJKDLSJF <html>";
		for(int i =0; i<100000; i++){
			insert = "INSERT INTO 'akj dkfj as' JALKJKDLSJF <html>";
			while(insert.matches(".*(\')(.*) (.*)(\').*")) {
				insert = reemplazo(insert);
			}
			
		}
		System.out.println(insert);
		//System.out.println(reemplazo("INSERT INTO 'akj dkfj as' JALKJKDLSJF <html>"));
	}
	
	public static String reemplazo(String s) {
		//s.replaceAll(regex, replacement)
		return s.replaceAll("(\')(.*) (.*)(\')", "$1$2"+"#" + "$3$4");
	}
}
