package main.archit1.event;

public class LineStorageChangeEvent extends BaseEvent {
	public enum LineStorageChangeType {
		ADD, DELETE;
	}

	final private LineStorageChangeType type;
	final private int line;

	public LineStorageChangeEvent(LineStorageChangeType type, int line) {
		super(LineStorageChangeEvent.class.getName());
		this.type = type;
		this.line = line;
	}

	public LineStorageChangeType getEventType() {
		return type;
	}

	public int getChangedLine() {
		return line;
	}
}
