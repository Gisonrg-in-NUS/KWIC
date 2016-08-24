package main.launcher;

import main.archit1.control.MasterControl;
import main.archit2.controller.KWICPipesAndFiltersController;
import main.ui.MainUiWindow;

public class Launcher {
	public static void main(String[] args) {
		MasterControl implictInovkeController = new MasterControl();
		KWICPipesAndFiltersController piplineController = new KWICPipesAndFiltersController();
		new MainUiWindow(implictInovkeController, piplineController);
	}
}
