package test.archit1.module.alphabetizer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.archit1.data.LineStorage;
import main.archit1.module.alphabetizer.Alphabetizer;

public class AlphabetizerTest {
	LineStorage storage;
	Alphabetizer alphabetizer;

	@Before
	public void setUp() {
		storage = new LineStorage();
		alphabetizer = new Alphabetizer();
		storage.addObserver(alphabetizer);
	}

	@Test
	public void test() {
		storage.addLine("ghi abc def");
		storage.addLine("def ghi abc");
		storage.addLine("abc def ghi");
		
		assertEquals(3, storage.size());
		assertEquals("abc def ghi", storage.get(0).toString());
		assertEquals("def ghi abc", storage.get(1).toString());
		assertEquals("ghi abc def", storage.get(2).toString());
	}
	
	@Test
	public void testMixCase() {
		storage.addLine("A");
		storage.addLine("b");
		storage.addLine("D");
		storage.addLine("c");

		assertEquals(4, storage.size());
		assertEquals("A", storage.get(0).toString());
		assertEquals("b", storage.get(1).toString());
		assertEquals("c", storage.get(2).toString());
		assertEquals("D", storage.get(3).toString());
	}
}
