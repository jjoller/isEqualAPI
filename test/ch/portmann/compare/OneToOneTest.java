package ch.portmann.compare;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import ch.portmann.input.Sentence;
import ch.portmann.stanford.StanfordCore;

public class OneToOneTest {

	@Test
	public void testCompare() throws ClassNotFoundException, IOException {
		
		StanfordCore.init();

		// setup
		LGTComparer oneToOne = new OneToOne();
		List<String> sentence1 = new ArrayList<String>();
		List<String> sentence2 = new ArrayList<String>();
		List<Integer[]> result = new ArrayList<Integer[]>();

		// fill test cases
		sentence1.add("This is same same.");
		sentence2.add("This is same same.");
		result.add(new Integer[] {0, 0, 0, 0, 0});
		
		sentence1.add("This is same same.");
		sentence2.add("This is not the same.");
		result.add(new Integer[] {0, 1, 2, 3, 4});
		
		// execute cases
				for (int i = 0; i < sentence1.size(); i++) {
					CompareCouple compareCouple = new CompareCouple(new Sentence(sentence1.get(i)),
							new Sentence(sentence2.get(i)));
					compareCouple = oneToOne.compare(compareCouple);
					assertEquals("Sentence: " + i + "seems to be wrong!", Arrays.equals(result.get(i), compareCouple.getInput().getGroupes()), true);
				}
	}
}
