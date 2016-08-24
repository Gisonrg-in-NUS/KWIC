package main.archit2.pipes;

import java.util.ArrayList;
import java.util.List;

public class StringArrayPipe implements Pipe<String[]>{
	public List<String[]> buffer = new ArrayList<String[]>();

	@Override
	public void push(String[] input) {
		synchronized (buffer) {
			buffer.add(input);		
			buffer.notify();
		}
	}

	@Override
	public String[] pop() throws InterruptedException {
		synchronized (buffer) {
			if (buffer.size() == 0){
				buffer.wait();
			}
			String[] out = buffer.remove(0);
			return out;
		}
		
	}

}
