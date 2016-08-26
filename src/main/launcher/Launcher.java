package main.launcher;

import main.ui.NewUiWindow;
import main.ui.UiController;
import main.ui.UiController.KwicUi;

public class Launcher {
	public static void main(String[] args) {
		KwicUi view = new NewUiWindow();
		UiController controller = new UiController(view);
		view.setController(controller);
	}
}
