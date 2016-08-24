package main.archit2.filters;

import main.archit2.pipes.Pipe;
import main.archit2.pipes.StringArrayPipe;

public class Input implements Filters<String[], String[]> {

	public String[] data;
	public StringArrayPipe inputChannel;
	public StringArrayPipe outputChannel;

	public Input(Pipe inputChannel, Pipe outputChannel) {
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

	@Override
	public void write(String[] output) {
		outputChannel.push(output);
	}

}
