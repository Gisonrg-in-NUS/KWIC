package main.event;

public class BaseEvent {
	final String name;
	
	public BaseEvent(String name) {
		this.name = name;
	}
	
	public String getEventName() {
		return name;
	}
}
