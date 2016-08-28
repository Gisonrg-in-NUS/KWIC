package test.archit1.control;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import main.archit1.controller.ImplicitInvocationController;

public class MasterControlTest {

	ImplicitInvocationController master;
	
	@Before
	public void setUp() {
		master = new ImplicitInvocationController();
	}
	
	@Test
	public void test() {
		Set<String> ignoreWords = new HashSet<>();
		ignoreWords.add("is");
		ignoreWords.add("the");
		ignoreWords.add("of");
		ignoreWords.add("and");
		ignoreWords.add("as");
		ignoreWords.add("a");
		ignoreWords.add("after");
		
		List<String> input = new ArrayList<>();
		input.add("The Day after Tomorrow");
		input.add("Fast and Furious");
		input.add("Man of Steel");
		
		List<String> result = master.run(input, ignoreWords);
		
		assertEquals(6, result.size());
		assertEquals("Day after Tomorrow the", result.get(0));
		assertEquals("Fast and Furious", result.get(1));
		assertEquals("Furious Fast and", result.get(2));
		assertEquals("Man of Steel", result.get(3));
		assertEquals("Steel Man of", result.get(4));
		assertEquals("Tomorrow the Day after", result.get(5));
	}
	
	@Test
	public void test_firstWordCapitalized() {
		Set<String> ignoreWords = new HashSet<>();
		ignoreWords.add("is");
		ignoreWords.add("the");
		ignoreWords.add("of");
		ignoreWords.add("and");
		ignoreWords.add("as");
		ignoreWords.add("a");
		ignoreWords.add("after");
		
		List<String> input = new ArrayList<>();
		input.add("The day after tomorrow");
		input.add("fast and Furious");
		input.add("man Of steel");
		
		List<String> result = master.run(input, ignoreWords);
		
		assertEquals(6, result.size());
		assertEquals("Day after tomorrow the", result.get(0));
		assertEquals("Fast and Furious", result.get(1));
		assertEquals("Furious fast and", result.get(2));
		assertEquals("Man of steel", result.get(3));
		assertEquals("Steel man of", result.get(4));
		assertEquals("Tomorrow the day after", result.get(5));
	}

	@Test
	public void test_DontHandleduplicate() {
		Set<String> ignoreWords = new HashSet<>();
		ignoreWords.add("is");
		ignoreWords.add("the");
		ignoreWords.add("of");
		ignoreWords.add("and");
		ignoreWords.add("as");
		ignoreWords.add("a");
		ignoreWords.add("after");
		
		List<String> input = new ArrayList<>();
		input.add("man of steel");
		input.add("man of steel");
		
		List<String> result = master.run(input, ignoreWords);
		
		assertEquals(4, result.size());
		assertEquals("Man of steel", result.get(0));
		assertEquals("Man of steel", result.get(1));
		assertEquals("Steel man of", result.get(2));
		assertEquals("Steel man of", result.get(3));
	}
	
	@Test
	public void test_duplicateWords() {
		Set<String> ignoreWords = new HashSet<>();
		ignoreWords.add("is");
		ignoreWords.add("the");
		ignoreWords.add("of");
		ignoreWords.add("and");
		ignoreWords.add("as");
		ignoreWords.add("a");
		ignoreWords.add("after");
		
		List<String> input = new ArrayList<>();
		input.add("man man man");
		
		List<String> result = master.run(input, ignoreWords);
		
		assertEquals(3, result.size());
		assertEquals("Man man man", result.get(0));
		assertEquals("Man man man", result.get(1));
		assertEquals("Man man man", result.get(2));
	}
	
	@Test
	public void test_emptyInput() {
		Set<String> ignoreWords = new HashSet<>();
		ignoreWords.add("is");
		ignoreWords.add("the");
		ignoreWords.add("of");
		ignoreWords.add("and");
		ignoreWords.add("as");
		ignoreWords.add("a");
		ignoreWords.add("after");
		
		List<String> input = new ArrayList<>();
		input.add("");
		
		List<String> result = master.run(input, ignoreWords);
		
		assertEquals(0, result.size());
	}
	
	@Test
	public void test_emptyLine() {
		Set<String> ignoreWords = new HashSet<>();
		ignoreWords.add("is");
		ignoreWords.add("the");
		ignoreWords.add("of");
		ignoreWords.add("and");
		ignoreWords.add("as");
		ignoreWords.add("a");
		ignoreWords.add("after");
		
		List<String> input = new ArrayList<>();
		input.add(" ");
		input.add("Man");
		input.add("     ");
		input.add(" of    ");
		input.add("     ");
		
		List<String> result = master.run(input, ignoreWords);
		
		assertEquals(1, result.size());
		assertEquals("Man", result.get(0));
	}
	
	@Test
	public void test_allIngored() {
		Set<String> ignoreWords = new HashSet<>();
		ignoreWords.add("is");
		ignoreWords.add("the");
		ignoreWords.add("of");
		ignoreWords.add("and");
		ignoreWords.add("as");
		ignoreWords.add("a");
		ignoreWords.add("after");
		
		List<String> input = new ArrayList<>();
		input.add("is");
		input.add("the");
		input.add("and as a after");
		
		List<String> result = master.run(input, ignoreWords);
		
		assertEquals(0, result.size());
	}
}
