package main.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import main.archit1.control.MasterControl;
import main.archit2.controller.KWICPipesAndFiltersController;
import main.archit2.helper.IgnoreHelper;

public class MainUiWindow extends JFrame {

	private static final long serialVersionUID = 2734069422557954195L;

	private boolean isUsingEventArchitecture = false;
	private MasterControl controller1;
	private KWICPipesAndFiltersController controller2;

	private JTextArea linesInput;
	private JTextArea ignoreWordsInput;
	private JTextArea resultsOutput;
	private JButton generateButton;
	private JButton clearAllButton;

	public MainUiWindow(MasterControl controller1, KWICPipesAndFiltersController controller2) {
		super("Key Word In Context");
		this.controller1 = controller1;
		this.controller2 = controller2;

		JPanel mainPanel = new JPanel(new GridLayout(3, 1));

		// Lines input
		JPanel inputPanel = new JPanel();
		inputPanel.setBorder(new TitledBorder(new EtchedBorder(), "Lines Input"));
		linesInput = new JTextArea(16, 58);
		linesInput.setEditable(true);
		JScrollPane linesInputScroll = new JScrollPane(linesInput);
		linesInputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		inputPanel.add(linesInputScroll);

		// Operational Panel
		JPanel operationsPanel = new JPanel();
		// Ignore words
		BoxLayout operationLayout = new BoxLayout(operationsPanel, BoxLayout.Y_AXIS);

		JPanel ignoreWordsPanel = new JPanel();
		ignoreWordsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Words Ignored"));
		ignoreWordsInput = new JTextArea(14, 58);
		ignoreWordsInput.setEditable(true);
		JScrollPane ignoreWordsInputScroll = new JScrollPane(ignoreWordsInput);
		ignoreWordsInputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ignoreWordsPanel.add(ignoreWordsInputScroll);
		ignoreWordsPanel.setPreferredSize(ignoreWordsPanel.getPreferredSize());

		operationsPanel.add(ignoreWordsPanel);

		// Button Panel
		JPanel buttonPanel = new JPanel();
		generateButton = new JButton("Generate");
		clearAllButton = new JButton("Clear All");

		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(generateButton);
		buttonPanel.add(clearAllButton);
		buttonPanel.add(Box.createHorizontalGlue());

		operationsPanel.add(buttonPanel);
		operationsPanel.setLayout(operationLayout);

		// Result output
		JPanel outputPanel = new JPanel();
		outputPanel.setBorder(new TitledBorder(new EtchedBorder(), "Results"));
		resultsOutput = new JTextArea(16, 58);
		resultsOutput.setEditable(false);
		JScrollPane outputDisplayScroll = new JScrollPane(resultsOutput);
		outputDisplayScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		outputPanel.add(outputDisplayScroll);

		mainPanel.add(inputPanel);
		mainPanel.add(operationsPanel);
		mainPanel.add(outputPanel);

		add(mainPanel);

		// Set up events
		attachButtonEvents();

		// Run pipeline ctrl
		controller2.run();

		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void attachButtonEvents() {
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get input lines
				String inputLines = linesInput.getText();
				String[] lines = inputLines.split("\n");
				List<String> linesList = Arrays.asList(lines);

				// Get input lines
				String ignoreWords = ignoreWordsInput.getText();
				String[] ignoreWordsList = ignoreWords.split("\n");
				Set<String> ignoreWordsSet = new HashSet<>();
				for (String word : ignoreWordsList) {
					ignoreWordsSet.add(word.toLowerCase());
				}

				if (isUsingEventArchitecture) {
					List<String> result = controller1.run(linesList, ignoreWordsSet);
					showResults(result);
				} else {
					IgnoreHelper.init(ignoreWordsSet);
					controller2.getInput().write(lines);
					controller2.getOutput().setJTextArea(resultsOutput);
				}
			}
		});

		clearAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				linesInput.setText("");
				ignoreWordsInput.setText("");
				resultsOutput.setText("");
			}
		});
	}

	private void showResults(List<String> result) {
		if (result.isEmpty()) {
			resultsOutput.setText("");
			return;
		}
		StringBuilder builder = new StringBuilder();
		for (String entry : result) {
			builder.append(entry);
			builder.append("\n");
		}
		builder.setLength(builder.length() - 1); // remove the new line in the
													// end
		resultsOutput.setText(builder.toString());
	}
}
