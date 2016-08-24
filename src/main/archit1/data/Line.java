package main.archit1.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Line implements Comparable<Line> {
	private List<String> words;

	public Line(String words) {
		words = words.trim(); // trim extra spaces.
		this.words = words.isEmpty() ? new ArrayList<String>() : Arrays.asList(words.split("\\s"));
	}
	
	public Line(List<String> words) {
		this.words = words;
	}

	public int size() {
		return words.size();
	}

	public String getWord(int index) {
		return words.get(index);
	}
	
	public void setWord(int index, String word) {
		words.set(index, word);
	}

	public List<String> getWordsFromStartToIndex(int index) {
		return new ArrayList<String>(words.subList(0, index));
	}

	public List<String> getWordsFromIndexToEnd(int index) {
		return new ArrayList<String>(words.subList(index, words.size()));
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (String word : words) {
			builder.append(word);
			builder.append(" ");
		}
		builder.setLength(builder.length() - 1);
		return builder.toString();
	}

	@Override
	public int compareTo(Line o) {
		return this.words.get(0).toLowerCase().compareTo(o.getWord(0).toLowerCase());
	}
}
