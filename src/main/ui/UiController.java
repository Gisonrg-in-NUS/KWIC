package main.ui;

import java.util.List;
import java.util.Set;

import javax.swing.JTextArea;

import main.archit1.controller.ImplicitInvocationController;
import main.archit2.controller.PipesAndFiltersController;
import main.archit2.helper.IgnoreHelper;
import main.constants.ArchitectureType;

public class UiController {
	public interface KwicUi {
		List<String> getInput();
		String[] getInputArray();
		JTextArea getOutputTextArea();
		Set<String> getIgnoreWords();
		ArchitectureType getSelectedArchitecture();
		void setResutls(List<String> results);
		void setController(UiController controller);
	}
	
	final private KwicUi view;
	
	private ImplicitInvocationController controller1;
	private PipesAndFiltersController controller2;
	public UiController(KwicUi view) {
		this.view = view;
		controller1 = new ImplicitInvocationController();
		controller2 = new PipesAndFiltersController();
		controller2.run();
	}
	
	public void generateResult() {
		ArchitectureType type = this.view.getSelectedArchitecture();
		Set<String> ignoreWordsSet = view.getIgnoreWords();
		switch(type) {
			case IMPLICT_INVOKE:
				List<String> result = controller1.run(view.getInput(), ignoreWordsSet);
				view.setResutls(result);
				break;
			case PIPES_AND_FILTERS:
				IgnoreHelper.init(ignoreWordsSet);
				controller2.getOutput().setJTextArea(view.getOutputTextArea());
				controller2.getInput().write(view.getInputArray());
				break;
			default:
				System.out.println("Unsupported Architecture");
				break;
		}
	}
}
