package main.archit2.pipes;

public interface Pipe<T> {
	public void push(T input);
	public T pop() throws InterruptedException;
	
}
