package main.archit2.pipes;

import java.util.ArrayList;
import java.util.List;

public interface Pipe<T> {
	public void push(T input);
	public T pop() throws InterruptedException;
	
}
