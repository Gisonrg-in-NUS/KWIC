package main.archit1.controller;

import java.util.List;
import java.util.Set;

import main.archit1.data.LineStorage;
import main.archit1.module.alphabetizer.Alphabetizer;
import main.archit1.module.shifter.CircularShifter;

public class ImplicitInvocationController {
	final private Alphabetizer alphabetizer;
	final private CircularShifter shifter;

	private LineStorage rawInputLines;
	private LineStorage resultLines;

	public ImplicitInvocationController() {
		// Storage
		rawInputLines = new LineStorage();
		resultLines = new LineStorage();
		
		// Sub-modules
		shifter = new CircularShifter(resultLines);
		alphabetizer = new Alphabetizer();

		// Set up observation
		rawInputLines.addObserver(shifter);
		resultLines.addObserver(alphabetizer);
	}

	public List<String> run(List<String> input, Set<String> ignoreWords) {
		rawInputLines.clearLines();
		resultLines.clearLines();

		// Set ignore words
		shifter.setIgnoreWords(ignoreWords);
		
		// Add data line by line
		for (String line : input) {
			rawInputLines.addLine(line);
		}
		
		return resultLines.getAll();
	}
}
