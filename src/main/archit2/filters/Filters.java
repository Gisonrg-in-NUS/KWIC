package main.archit2.filters;

public interface Filters<T1, T2> extends Runnable {
	public T1 read() throws InterruptedException;

	public void write(T2 output);
}
