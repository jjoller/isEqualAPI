package ch.portmann.compare;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import ch.portmann.input.Sentence;
import ch.portmann.stanford.StanfordCore;

public class MathjaxTest {

	@Test
	public void testMathjax() {
		// fail("Not yet implemented");
	}

	@Test
	public void testCompare() throws ClassNotFoundException, IOException {

		StanfordCore.init();

		// setup
		LGTComparer mathjax = new Mathjax();
		List<String> sentence1 = new ArrayList<String>();
		List<String> sentence2 = new ArrayList<String>();
		List<Integer[]> result = new ArrayList<Integer[]>();

		// fill test cases
		sentence1.add("Some test \\[x = {-b \\pm \\sqrt{b^2-4ac} \\over 2a}\\] and text.");
		sentence2.add("\\[x = {-b \\pm \\sqrt{b^2-4ac} \\over 2a}\\] and text.");
		result.add(new Integer[] { 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 4, 5 });

		sentence1.add("Some test \\[x = {-b \\pm \\sqrt{b^2-4ac} \\over 2a}\\] and text.");
		sentence2.add("\\[x = {-b \\pm \\sqrt{b^2222-4ac} \\over 2a}\\] and text.");
		result.add(new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
				24, 25, 26 });

		// execute cases
		for (int i = 0; i < sentence1.size(); i++) {
			CompareCouple compareCouple = new CompareCouple(new Sentence(sentence1.get(i)),
					new Sentence(sentence2.get(i)));
			compareCouple = mathjax.compare(compareCouple);
			assertEquals("Sentence: " + i + "seems to be wrong!",
					Arrays.equals(result.get(i), compareCouple.getInput().getGroupes()), true);
		}
	}


}
