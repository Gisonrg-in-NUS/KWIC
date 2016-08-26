package main.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import main.constants.ArchitectureType;
import main.ui.UiController.KwicUi;

public class NewUiWindow extends JFrame implements KwicUi {

	private static final long serialVersionUID = -3445311782196514706L;

	private JTextArea linesInput;
	private JTextArea ignoreWordsInput;
	private JTextArea resultsOutput;
	private JButton generateButton;
	private JButton clearAllButton;
	private ArchitectureType currentType = ArchitectureType.IMPLICT_INVOKE;
	
	private UiController controller;
	
	public NewUiWindow() {
		super("Key Word In Context");
		add(createAndAddComponents());
		attachButtonEvents();
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private JPanel createAndAddComponents() {
		JPanel mainPanel = new JPanel(new GridLayout(0, 2));
		
		// Left Panel
		JPanel userInputPanel = new JPanel(new GridLayout(2, 0));
		userInputPanel.setPreferredSize(new Dimension(400, 480));
		JPanel linesInputPanel = new JPanel();
		JPanel ignoreWordsInputPanel = new JPanel();
		
		// Right Panel
		JPanel rightPanel = new JPanel(new GridBagLayout());
		rightPanel.setPreferredSize(new Dimension(400, 480));
		JPanel resultPanel = new JPanel();
		JPanel architectureSelectionPanel = new JPanel();
		JPanel operationPanel = new JPanel();

		// Lines input
		linesInputPanel.setBorder(new TitledBorder(new EtchedBorder(), "Lines Input"));
		linesInput = new JTextArea(12, 30);
		linesInput.setEditable(true);
		JScrollPane linesInputScroll = new JScrollPane(linesInput);
		linesInputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		linesInputPanel.add(linesInputScroll);

		// Ignore words input
		ignoreWordsInputPanel.setBorder(new TitledBorder(new EtchedBorder(), "Words Ignored"));
		ignoreWordsInput = new JTextArea(12, 30);
		ignoreWordsInput.setEditable(true);
		JScrollPane ignoreWordsInputScroll = new JScrollPane(ignoreWordsInput);
		ignoreWordsInputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ignoreWordsInputPanel.add(ignoreWordsInputScroll);

		userInputPanel.add(linesInputPanel);
		userInputPanel.add(ignoreWordsInputPanel);

		// Results output
		resultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Results"));
		resultsOutput = new JTextArea(19, 30);
		resultsOutput.setEditable(false);
		JScrollPane outputDisplayScroll = new JScrollPane(resultsOutput);
		outputDisplayScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		resultPanel.add(outputDisplayScroll);
		
		// Architecture selection
		ButtonGroup group = new ButtonGroup();
		JRadioButton archit1 = new JRadioButton("Implict Invocation");
		archit1.setSelected(true);
		JRadioButton archit2 = new JRadioButton("Pipe and Filters");
		archit1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentType = ArchitectureType.IMPLICT_INVOKE;
			}
		});
		archit2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentType = ArchitectureType.PIPES_AND_FILTERS;
			}
		});
	    group.add(archit1);
	    group.add(archit2);
	    architectureSelectionPanel.add(archit1);
	    architectureSelectionPanel.add(archit2);
	    
		// Operation area
		generateButton = new JButton("Generate");
		clearAllButton = new JButton("Clear All");

		operationPanel.setLayout(new BoxLayout(operationPanel, BoxLayout.X_AXIS));
		operationPanel.add(Box.createHorizontalGlue());
		operationPanel.add(generateButton);
		operationPanel.add(clearAllButton);
		operationPanel.add(Box.createHorizontalGlue());
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 0.8;
		rightPanel.add(resultPanel, c);
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 0.1;
		rightPanel.add(architectureSelectionPanel, c);
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1.0;
		c.weighty = 0.1;
		rightPanel.add(operationPanel, c);

		mainPanel.add(userInputPanel);
		mainPanel.add(rightPanel);

		return mainPanel;
	}
	
	private void attachButtonEvents() {
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.generateResult();
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

	@Override
	public List<String> getInput() {
		List<String> linesList = Arrays.asList(getInputArray());
		return linesList;
	}

	@Override
	public Set<String> getIgnoreWords() {
		String ignoreWords = ignoreWordsInput.getText();
		String[] ignoreWordsList = ignoreWords.split("\n");
		Set<String> ignoreWordsSet = new HashSet<>();
		for (String word : ignoreWordsList) {
			ignoreWordsSet.add(word.toLowerCase());
		}
		return ignoreWordsSet;
	}

	@Override
	public ArchitectureType getSelectedArchitecture() {
		return currentType;
	}

	@Override
	public void setResutls(List<String> results) {
		if (results.isEmpty()) {
			resultsOutput.setText("");
			return;
		}
		StringBuilder builder = new StringBuilder();
		for (String entry : results) {
			builder.append(entry);
			builder.append("\n");
		}
		builder.setLength(builder.length() - 1); // remove the new line in the end
		resultsOutput.setText(builder.toString());
	}

	@Override
	public JTextArea getOutputTextArea() {
		return resultsOutput;
	}

	@Override
	public void setController(UiController controller) {
		this.controller = controller;
	}

	@Override
	public String[] getInputArray() {
		String inputLines = linesInput.getText();
		String[] lines = inputLines.split("\n");
		return lines;
	}
}
