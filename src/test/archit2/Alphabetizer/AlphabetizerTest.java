package test.archit2.Alphabetizer;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import main.archit2.filters.Alphabetizer;
import main.archit2.helper.IgnoreHelper;


public class AlphabetizerTest {
	Alphabetizer alphabetizer;

	@Before
	public void setUp() {
		alphabetizer = new Alphabetizer(null, null);
	}

	@Test
	public void test() {
		alphabetizer.data = new String[]{"ghi abc def ignore","def ghi abc ignore","abc def ghi ignore"};
		Set<String> words = new HashSet<>();
		words.add("ignore");
		IgnoreHelper.init(words);
		String[] ans = alphabetizer.sort();
		assertEquals("abc def ghi ignore", ans[0]);
		assertEquals("def ghi abc ignore", ans[1]);
		assertEquals("ghi abc def ignore", ans[2]);
	}
	
	@Test
	public void testMixCase() {
		alphabetizer.data = new String[]{"ghi abc def ignore","def ghi abc ignore","abc def ghi ignore"};
		Set<String> words = new HashSet<>();
		words.add("ignore");
		IgnoreHelper.init(words);
		String[] ans = alphabetizer.sort();
		assertEquals("Abc def ghi ignore", Alphabetizer.formalize(ans[0]));
		assertEquals("Def ghi abc ignore", Alphabetizer.formalize(ans[1]));
		assertEquals("Ghi abc def ignore", Alphabetizer.formalize(ans[2]));
	}
}
