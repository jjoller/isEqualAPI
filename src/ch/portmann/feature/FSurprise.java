package ch.portmann.feature;

import ch.portmann.input.Sentence;

public class FSurprise extends RegexFeature implements LGTFeature {

	// SET REGEX TO CREATE A REGEXFEATURE
	String regX = "/amaze\\w*|amazing|astonish\\w*|dumbfound\\w*|startl\\w*|stunn\\w*|surpris\\w*|aback|thunderstruck|wonder\\w*/";

	public FSurprise() {

	}

	@Override
	public Sentence run(Sentence sentence) {

		sentence = this.runRegX(sentence, this.getRegX());
		return sentence;

	}

	public String getRegX() {
		return regX;
	}

	public void setRegX(String regX) {
		this.regX = regX;
	}

}
