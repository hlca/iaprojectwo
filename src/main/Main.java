package main;

import filter.Problem;

public class Main {
	public static void main(String[] args) {
		
		Problem p = new Problem();
		
		p.read();
		p.classify();
		p.calculateProbabilities();
		p.check();
		p.test();
		p.classifyNew();
	}
}
