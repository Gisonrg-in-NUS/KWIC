package main.archit1.module.alphabetizer;

import java.util.Observable;

import main.archit1.data.Line;
import main.archit1.data.LineStorage;
import main.archit1.event.LineStorageChangeEvent;
import main.archit1.module.BaseModule;

public class Alphabetizer implements BaseModule {
	@Override
	public void update(Observable o, Object arg) {
		LineStorage storage = (LineStorage) o;
		LineStorageChangeEvent event = (LineStorageChangeEvent) arg;
		Line line = storage.get(event.getChangedLine());
		switch (event.getEventType()) {
		case ADD:
			line.capitalizeFirstWord();
			alphabetize(storage, line, event.getChangedLine());
			break;
		default:
			break;
		}
	}

	private void alphabetize(LineStorage storage, Line line, int lineNumber) {
		
		for (int i = 0; i <= (lineNumber - 1); i++) {
			if (line.compareTo(storage.get(i)) <= 0) {
				storage.insert(i, line);
				storage.delete(lineNumber + 1);
				break;
			}
		}
	}
}
