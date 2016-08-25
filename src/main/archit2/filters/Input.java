package main.archit2.filters;

import main.archit2.pipes.Pipe;
import main.archit2.pipes.StringArrayPipe;

public class Input implements Filters<String[], String[]> {

	public String[] data;
	public StringArrayPipe inputChannel;
	public StringArrayPipe outputChannel;

	public Input(Pipe<?> inputChannel, Pipe<?> outputChannel) {
		this.inputChannel = (StringArrayPipe) inputChannel;
		this.outputChannel = (StringArrayPipe) outputChannel;
	}

	@Override
	public void run() {
		// Do nothing for input

	}

	@Override
	public String[] read() throws InterruptedException {
		if (inputChannel == null) {
			return null;
		}
		return (String[]) inputChannel.pop();
	}

	public static String eliminateSpace(String str){
		if (str.equals("")) return "";
		String newString = "";
		boolean lastSpace = false;
		int begin = 0;
		int end = str.length();
		while (str.charAt(begin) == ' ' && begin+1 < str.length()) begin++;
		while (str.charAt(end-1) == ' ' && end-1 > 0) end--;
		
		for (int i = begin; i< end; i++){
			if (str.charAt(i) == ' '){
				if (lastSpace){
					
				}else{
					newString += str.charAt(i);
					lastSpace = true;
				}
			}else{
				newString += str.charAt(i);
				lastSpace = false;
			}
		}
		return newString;
	}
	
	@Override
	public void write(String[] output) {
		int numOfQulifiedLines = 0;
		for (int i = 0; i< output.length; i++){
			String newString = eliminateSpace(output[i]);
			if (!newString.equals("")){
				numOfQulifiedLines++;
			}
			output[i] = newString;
		}
		String[] finalOutput = new String[numOfQulifiedLines];
		int index = 0;
		for (int i = 0; i< output.length; i++){
			if (output[i] != ""){
				finalOutput[index] = output[i];
				index++;
			}
		}
		outputChannel.push(finalOutput);
	}

}
