package main.control;

import java.util.List;
import java.util.Set;

import main.data.LineStorage;
import main.module.alphabetizer.Alphabetizer;
import main.module.shifter.CircularShifter;

public class MasterControl {
	final private Alphabetizer alphabetizer;
	final private CircularShifter shifter;

	private LineStorage rawInputLines;
	private LineStorage resultLines;

	public MasterControl() {
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
