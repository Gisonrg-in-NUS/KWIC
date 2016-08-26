package main.archit2.controller;

import main.archit2.filters.Alphabetizer;
import main.archit2.filters.CircularShift;
import main.archit2.filters.Input;
import main.archit2.filters.Output;
import main.archit2.pipes.StringArrayPipe;

public class PipesAndFiltersController {
	StringArrayPipe pipeForInputCircularShift = new StringArrayPipe();
	StringArrayPipe pipeForCircularShiftAlphabetizer = new StringArrayPipe();
	StringArrayPipe pipeForAlphabetizerOutput = new StringArrayPipe();

	Input input = new Input(null, pipeForInputCircularShift);
	CircularShift circularShift = new CircularShift(pipeForInputCircularShift, pipeForCircularShiftAlphabetizer);
	Alphabetizer alphabetizer = new Alphabetizer(pipeForCircularShiftAlphabetizer, pipeForAlphabetizerOutput);
	Output output = new Output(pipeForAlphabetizerOutput, null);
	Thread thread1 = new Thread(input, "Input thread");
	Thread thread2 = new Thread(circularShift, "circularShift");
	Thread thread3 = new Thread(alphabetizer, "Alphabetizer");
	Thread thread4 = new Thread(output, "Output thread");

	public void run() {
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
	}

	public Input getInput() {
		return input;
	}

	public Output getOutput() {
		return output;
	}
}
