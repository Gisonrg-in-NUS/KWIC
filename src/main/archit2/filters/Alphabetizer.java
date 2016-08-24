package main.archit2.filters;

import java.util.ArrayList;

import main.archit2.helper.IgnoreHelper;
import main.archit2.pipes.Pipe;
import main.archit2.pipes.StringArrayPipe;

public class Alphabetizer implements Filters<String[], String[]> {

	public String[] data;
	public StringArrayPipe inputChannel;
	public StringArrayPipe outputChannel;

	public Alphabetizer(Pipe inputChannel, Pipe outputChannel) {
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
			while (larger(first, array.get(i)) && i < end) {
				i++;
			}
			while (larger(array.get(j), first) && j >= beg) {
				j--;
			}
			if (i < j) {
				String tmpStr = array.get(i);
				array.set(i, array.get(j));
				array.set(j, tmpStr);
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
		ArrayList<String> ans = new ArrayList<String>();
		for (String tmpStr : words) {
			if (IgnoreHelper.ifIgnore(tmpStr)) {
				ans.add(tmpStr.toLowerCase());
			} else {
				String newStr = tmpStr.substring(0, 1).toUpperCase();
				if (tmpStr.length() > 1) {
					newStr += tmpStr.substring(1, tmpStr.length());
				}
				ans.add(newStr);
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
			System.out.println(tmpStr);
			dataSet.add(tmpStr);
		}
		quickSort(dataSet);
		return dataSet.toArray(data);
	}

	@Override
	public void run() {
		while (true) {
			try {
				data = read();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] generatedStrings = sort();
			for (int i = 0; i < generatedStrings.length; i++) {
				generatedStrings[i] = formalize(generatedStrings[i]);
			}
			System.out.println(generatedStrings);
			write(generatedStrings);
		}
	}
}
