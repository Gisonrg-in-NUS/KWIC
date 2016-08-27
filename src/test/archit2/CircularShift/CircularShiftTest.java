package test.archit2.CircularShift;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.archit2.filters.CircularShift;
import main.archit2.helper.IgnoreHelper;

public class CircularShiftTest {

	CircularShift shifter;

	@Before
	public void setUp() {
		shifter = new CircularShift(null, null);
		Set<String> words = new HashSet<>();
		words.add("the");
		words.add("after");
		IgnoreHelper.init(words);
	}

	@Test
	public void test() {
		shifter.data = new String[]{"The Day after Tomorrow"};
		String[] ans = shifter.CircularShiftForStrings();
		System.out.println(ans[0]);
		assertEquals("Day after Tomorrow The", ans[0]);
		assertEquals("Tomorrow The Day after", ans[1]);
	}

	@After
	public void tearDown() {

	}
}
