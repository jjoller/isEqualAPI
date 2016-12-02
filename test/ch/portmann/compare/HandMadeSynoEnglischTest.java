package ch.portmann.compare;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ch.portmann.input.Sentence;
import ch.portmann.stanford.StanfordCore;

public class HandMadeSynoEnglischTest {

	@Test
	public void test() throws ClassNotFoundException, IOException {

		StanfordCore.init();

		// setup
		LGTComparer hadMadeEnglisch = new HandMadeEnglish();
		List<String> sentence1 = new ArrayList<String>();
		List<String> sentence2 = new ArrayList<String>();
		List<boolean[]> result = new ArrayList<boolean[]>();

		// fill test cases
		sentence1.add("Here we hava a test synonym.");
		sentence2.add("This is same same bla bla bla.");
		result.add(new boolean[] { false, false, false, false, true, true, true });

		sentence1.add("The GDP of the USA dropped from 0.5% to 1%.");
		sentence2.add("The GDP of the USA dropped from 0.5 percent to 1 percent.");
		result.add(new boolean[] { true, true, true, true, true, true, true, true, true, true, true, true, true });

		// execute cases
		for (int i = 0; i < sentence1.size(); i++) {
			CompareCouple compareCouple = new CompareCouple(new Sentence(sentence1.get(i)),
					new Sentence(sentence2.get(i)));
			compareCouple = hadMadeEnglisch.compare(compareCouple);
			assertEquals("Sentence: " + i + "seems to be wrong!",
					Arrays.equals(result.get(i), compareCouple.getInput().getIsHit()), true);
		}
	}

}
