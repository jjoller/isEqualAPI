package ch.portmann.corpus;

import java.io.IOException;

import ch.portmann.cash.MapDBStore;
import ch.portmann.feature.FSurprise;
import ch.portmann.feature.FeaturePipe;
import ch.portmann.feature.LGTFeature;
import ch.portmann.feature.RegexFeature;
import ch.portmann.input.Sentence;
import ch.portmann.stanford.StanfordCore;
import edu.stanford.nlp.ling.CoreLabel;

public class ProcessFeature {

	public static void main(String[] args) throws IOException {

		StanfordCore.init();

		FeaturePipe featurePipe = new FeaturePipe();
		LGTFeature surprise = new FSurprise();
		featurePipe.addFeature(surprise);

		Sentence sentence = null;
		try {
			sentence = new Sentence("I wonder if this project is going to work.");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sentence = featurePipe.run(sentence);

		int i = 0;
		for (boolean b : sentence.getIsHit()) {

			System.out.print(sentence.getToken(i) + ": ");
			System.out.print(b + "; ");
			i++;
		}

		System.out.println(" ");
		for (CoreLabel s : sentence.getTokens()) {

			System.out.print(s + " ");
		}

		System.out.println(sentence.isAllHit() && sentence.isAllHit());

		MapDBStore.getInstance().close();
	}

}
