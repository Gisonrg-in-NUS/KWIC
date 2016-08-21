package main.module.shifter;

import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.data.LineStorage;
import main.data.Line;
import main.event.LineStorageChangeEvent;
import main.module.BaseModule;

public class CircularShifter implements BaseModule {
	final private LineStorage resultStorage;
	private Set<String> ignoreWords = new HashSet<>();

	public CircularShifter(LineStorage storage) {
		resultStorage = storage;
	}

	public Set<String> getIgnoreWords() {
		return ignoreWords;
	}

	public void setIgnoreWords(Set<String> ignoreWords) {
		this.ignoreWords = ignoreWords;
	}

	@Override
	public void update(Observable o, Object arg) {
		LineStorage storage = (LineStorage) o;
		LineStorageChangeEvent event = (LineStorageChangeEvent) arg;
		switch (event.getEventType()) {
		case ADD:
			Line line = storage.get(event.getChangedLine());
			makeIgnoreWordsLowercase(line);
			doShift(line);
			break;
		default:
			break;
		}
	}

	private void doShift(Line line) {
		for (int i = 0; i < line.size(); i++) {
			// Ignore shift starting with ignored word.
			if (isIgnoreWord(line.getWord(i))) {
				continue;
			}
			List<String> newLine = Stream
					.concat(line.getWordsFromIndexToEnd(i).stream(), line.getWordsFromStartToIndex(i).stream())
					.collect(Collectors.toList());
			resultStorage.addLine(newLine);
		}
	}

	private void makeIgnoreWordsLowercase(Line line) {
		for (int i = 0; i < line.size();i++) {
			String word = line.getWord(i);
			if (isIgnoreWord(word)) {
				line.setWord(i, word.toLowerCase());
			}
		}
	}
	
	private boolean isIgnoreWord(String word) {
		return ignoreWords.contains(word.toLowerCase());
	}
}
