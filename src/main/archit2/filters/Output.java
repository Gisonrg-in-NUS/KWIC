package main.archit2.filters;

import javax.swing.JTextArea;

import main.archit2.pipes.Pipe;
import main.archit2.pipes.StringArrayPipe;

public class Output implements Filters<String[], String[]> {

	public String[] data;
	public StringArrayPipe inputChannel;
	public StringArrayPipe outputChannel;

	public Output(Pipe inputChannel, Pipe outputChannel) {
		this.inputChannel = (StringArrayPipe) inputChannel;
		this.outputChannel = (StringArrayPipe) outputChannel;
	}

	private JTextArea writeArea;

	@Override
	public void run() {
		while (true) {
			try {
				data = read();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String text = "";
			int size = data.length;
			for (String tmpStr : data) {
				text += tmpStr;
				size--;
				if (size != 0)
					text += "\n";
			}
			writeArea.setText(text);
		}
	}

	public void setJTextArea(JTextArea writeArea) {
		this.writeArea = writeArea;
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
