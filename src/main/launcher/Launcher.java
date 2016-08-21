package main.launcher;

import main.control.MasterControl;
import main.ui.MainUiWindow;

public class Launcher {
	public static void main(String[] args) {
		MasterControl controller = new MasterControl();
		new MainUiWindow(controller);
	}
}
