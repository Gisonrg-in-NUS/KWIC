package main.launcher;

import main.ui.ManView;
import main.ui.UiController;
import main.ui.UiController.KwicUi;

public class Launcher {
	public static void main(String[] args) {
		KwicUi view = new ManView();
		UiController controller = new UiController(view);
		view.setController(controller);
	}
}
