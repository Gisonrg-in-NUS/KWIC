package main.archit2.filters;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.archit2.helper.IgnoreHelper;
import main.archit2.pipes.Pipe;
import main.archit2.pipes.StringArrayPipe;

public class CircularShift implements Filters<String[], String[]> {

	public String[] data;
	public StringArrayPipe inputChannel;
	public StringArrayPipe outputChannel;

	public CircularShift(Pipe<?> inputChannel, Pipe<?> outputChannel) {
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

	public String combineStrings(List<String> tmpArr) {
		String ans = "";
		int size = tmpArr.size();
		for (String tmpStr : tmpArr) {
			ans += tmpStr;
			size--;
			if (size > 0) {
				ans += " ";
			}
		}
		return ans;
	}

	public LinkedList<String> CircularShiftForString(String str) {
		String[] strings = str.split(" ");
		List<String> tmpArr = new LinkedList<String>();
		int length = 0;
		for (String tmpStr : strings) {
			length++;
			tmpArr.add(tmpStr);
		}
		LinkedList<String> ans = new LinkedList<String>();
		for (int i = 0; i < length; i++) {
			if (!IgnoreHelper.ifIgnore(tmpArr.get(0))) {
				ans.add(combineStrings(tmpArr));
			}
			String first = tmpArr.get(0);
			tmpArr.remove(0);
			tmpArr.add(first);
		}

		return ans;
	}

	public String[] CircularShiftForStrings() {
		List<String> ans = new ArrayList<String>();
		List<String> subans;

		for (String tmpStr : data) {
			subans = CircularShiftForString(tmpStr);
			for (String str : subans) {
				ans.add(str);
			}

		}
		String[] results = new String[ans.size()];
		ans.toArray(results);
		return results;

	}

	@Override
	public void run() {
		while (true) {
			try {
				data = read();
				String[] generatedStrings = CircularShiftForStrings();
				write(generatedStrings);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
