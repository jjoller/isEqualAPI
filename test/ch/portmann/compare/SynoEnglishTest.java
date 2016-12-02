package ch.portmann.compare;

import static org.junit.Assert.*;

//POS is not considered in this version!!!

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ch.portmann.input.Sentence;
import ch.portmann.stanford.StanfordCore;

public class SynoEnglishTest {

	@Test
	public void testCompare() throws ClassNotFoundException, IOException {
		// fail("Not yet implemented");

		StanfordCore.init();

		// setup
		LGTComparer synoEnglish = new SynoEnglish();
		List<String> sentence1 = new ArrayList<String>();
		List<String> sentence2 = new ArrayList<String>();
		List<Integer[]> resultG = new ArrayList<Integer[]>();
		List<boolean[]> resultE = new ArrayList<boolean[]>();

		// fill test cases
		sentence1.add("The gross domestic product is the same topographic point.");
		sentence2.add("XXX gdp ddd ooo zzz place.");
		resultG.add(new Integer[] { 0, 1, 1, 1, 2, 3, 4, 5, 5, 6});
		resultE.add(new boolean[] {false, true, true, true, false, false, false, true, true, true});
		
		sentence1.add("The GDP of the USA dropped from 0.5% to 1%.");
		sentence2.add("The gross domestic product of america dropped from 1/2 percent to 1 percent.");
		resultG.add(new Integer[] { 0, 0, 1, 2, 2, 3, 3, 4, 5, 6, 6, 7, 8});
		resultE.add(new boolean[] {true, true, true, true, true, true, true, false, false, true, true, false, true});

		sentence1.add("The USA");
		sentence2.add("America");
		resultG.add(new Integer[] { 0, 0});
		resultE.add(new boolean[] {true, true});

		// execute cases
		for (int i = 0; i < sentence1.size(); i++) {
			
			CompareCouple compareCouple = new CompareCouple(new Sentence(sentence1.get(i)),
					new Sentence(sentence2.get(i)));
			compareCouple = synoEnglish.compare(compareCouple);
			
			assertEquals("Sentence: " + i + "seems to be wrong!",
					Arrays.equals(resultG.get(i), compareCouple.getInput().getGroupes()), true);
			
			assertEquals("Sentence: " + i + "seems to be wrong!",
					Arrays.equals(resultE.get(i), compareCouple.getInput().getIsHit()), true);
		}

	}

}
