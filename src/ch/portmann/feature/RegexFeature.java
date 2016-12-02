package ch.portmann.feature;

import java.util.List;
import ch.portmann.input.Sentence;
import edu.stanford.nlp.util.CoreMap;

public abstract class RegexFeature implements LGTFeature {

	public RegexFeature() {

	}

	@Override
	public abstract Sentence run(Sentence sentence);

	public Sentence runRegX(Sentence sentence, String regX) {
		List<List<CoreMap>> matches = sentence.runTRegx(regX);

		for (List<CoreMap> s : matches) {
			sentence.setHitandGroupe(sentence.getStartEndIndex(s));
		}

		return sentence;
	}
}
