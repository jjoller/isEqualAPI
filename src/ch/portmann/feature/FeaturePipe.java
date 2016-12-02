package ch.portmann.feature;

import java.util.ArrayList;
import java.util.List;
import ch.portmann.input.Sentence;

public class FeaturePipe {

	private List<LGTFeature> featurePipe = new ArrayList<LGTFeature>();


	public FeaturePipe() {

	}

	public Sentence run(Sentence sentence) {

		for (LGTFeature feature : featurePipe) {
			sentence = feature.run(sentence);

		}

		return sentence;

	}

	public void addFeature(LGTFeature feature) {

		this.getFeaturePipe().add(feature);

	}

	public List<LGTFeature> getFeaturePipe() {
		return this.featurePipe;
	}

	public void setFeaturePipe(List<LGTFeature> featurePipe) {
		this.featurePipe = featurePipe;
	}

}