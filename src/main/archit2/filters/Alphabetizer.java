package main.archit2.filters;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import main.archit2.helper.IgnoreHelper;
import main.archit2.pipes.Pipe;
import main.archit2.pipes.StringArrayPipe;

class WordComparator<String> implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		java.lang.String str0 = (java.lang.String) arg0.toString();
		java.lang.String str1 = (java.lang.String) arg1.toString();
		java.lang.String lower1 = str0.toLowerCase();
		java.lang.String lower2 = str1.toLowerCase();
		return lower1.compareTo(lower2);
	}
	
}

public class Alphabetizer implements Filters<String[], String[]> {

	public String[] data;
	public StringArrayPipe inputChannel;
	public StringArrayPipe outputChannel;

	public Alphabetizer(Pipe<?> inputChannel, Pipe<?> outputChannel) {
		this.inputChannel = (StringArrayPipe) inputChannel;
		this.outputChannel = (StringArrayPipe) outputChannel;
	}

	@Override
	public String[] read() throws InterruptedException {
		return (String[]) inputChannel.pop();
	}

	@Override
	public void write(String[] output) {
		outputChannel.push(output);

	}

	private static int partition(ArrayList<String> array, int beg, int end) {
		String first = array.get(beg);
		int i = beg, j = end;
		while (i < j) {
			while (larger(first, array.get(i)) && i < j) {
				i++;
			}
			while (larger(array.get(j), first) && j > i) {
				j--;
			}
			if (i < j) {
				String tmpStr = array.get(i);
				array.set(i, array.get(j));
				array.set(j, tmpStr);
				j--;
			}
		}
		if (j != beg) {
			String tmpStr = array.get(i);
			array.set(i, array.get(j));
			array.set(j, tmpStr);
		}
		return j;
	}

	public static void quickSort(ArrayList<String> array) {
		if (array != null) {
			quickSort(array, 0, array.size() - 1);
		}
	}

	private static void quickSort(ArrayList<String> array, int beg, int end) {
		if (beg >= end || array == null)
			return;
		int p = partition(array, beg, end);
		quickSort(array, beg, p - 1);
		quickSort(array, p + 1, end);
	}

	public static char toLowerCase(char ch) {
		if ((ch >= 'A') && (ch <= 'Z')) {
			return (char) (ch - 'A' + 'a');
		}
		return ch;

	}

	public static boolean larger(String s1, String s2) {
		int len;
		len = s1.length() < s2.length() ? s1.length() : s2.length();
		for (int i = 0; i < len; i++) {
			if (toLowerCase(s1.charAt(i)) < toLowerCase(s2.charAt(i))) {
				return false;
			} else if (toLowerCase(s1.charAt(i)) > toLowerCase(s2.charAt(i))) {
				return true;
			}
		}
		return s1.length() > s2.length();
	}

	public static String formalize(String str) {
		String[] words = str.split(" ");
		int first = 1;
		ArrayList<String> ans = new ArrayList<String>();
		
		for (String tmpStr : words) {
			if (IgnoreHelper.ifIgnore(tmpStr)) {
				ans.add(tmpStr.toLowerCase());
			} else {
				if (first == 1){
					String newStr = tmpStr.substring(0, 1).toUpperCase();
					if (tmpStr.length() > 1) {
						newStr += tmpStr.substring(1, tmpStr.length());
					}
					ans.add(newStr);
					first = 0;
				}else{
					ans.add(tmpStr);
				}
			}
		}
		String finalStr = "";
		int size = ans.size();
		for (String word : ans) {
			size--;
			finalStr += word;
			if (size > 0) {
				finalStr += " ";
			}
		}
		return finalStr;
	}

	public String[] sort() {
		ArrayList<String> dataSet = new ArrayList<String>();
		for (String tmpStr : data) {
			dataSet.add(tmpStr);
		}
		Collections.sort(dataSet,new WordComparator());
		//quickSort(dataSet);
		return dataSet.toArray(data);
	}

	@Override
	public void run() {
		while (true) {
			try {
				data = read();
				String[] generatedStrings = sort();
				ArrayList<String> noDuplicate = new  ArrayList<String>();
				for (int i = 0; i < generatedStrings.length; i++) {
					generatedStrings[i] = formalize(generatedStrings[i]);
					if (i ==0 ){
						noDuplicate.add(generatedStrings[i]);
					}else if (!generatedStrings[i].equals(generatedStrings[i-1])){
						noDuplicate.add(generatedStrings[i]);
					}
				}
				String[] finalStrs = new String[noDuplicate.size()];
				noDuplicate.toArray(finalStrs);
				//write(finalStrs); //This is for remove duplicate array
				write(generatedStrings);
			} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
