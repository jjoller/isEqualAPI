package ch.portmann.stanford;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ch.portmann.input.Sentence;
import ch.portmann.input.TokenLight;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

public final class StanfordUtility {

	private StanfordUtility() {


	}

	public static Annotation annotateAll(String originInput) {

		return StanfordCore.pipeline.process(originInput);

	}

	public static List<CoreMap> processSentence(String originInput) {

		Annotation annotation = annotateAll(originInput);

		List<CoreMap> sentences = new ArrayList<CoreMap>();

		for (CoreMap sentenceStanford : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {

			sentences.add(sentenceStanford);

		}

		return sentences;

	}

	public static Map<String, String> getVerb(Tree tree) {

		Map<String, String> dep_word = new HashMap<>();
		List<String> typedDep = new ArrayList<>(); // "typedDep" contains the
													// typed dependencies which
													// determine the tense of
													// sentence.

		typedDep.addAll(Arrays.asList("aux", "auxpass", "root", "cop"));

		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
		Collection<TypedDependency> td = gs.typedDependenciesCollapsed();

		for (TypedDependency td1 : td) {
			GrammaticalRelation relation = td1.reln();
			if (typedDep.contains(relation.getShortName())) {
				dep_word.put(relation.getShortName(), td1.dep().value()); // (typedDep,word)
				dep_word.put(relation.getShortName() + "POS", td1.dep().tag()); // (typedDepPOS,POS)
			}

		}
		return dep_word;
	}

	public static Sentence reCalcSentence(String inputString) {

		return null;
	}

	public static List<TokenLight> tokenizer(CoreMap sentence) {

		List<TokenLight> tokens = new ArrayList<TokenLight>();

		for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {

			TokenLight sT = new TokenLight();

			sT.setStemmed(token.get(CoreAnnotations.LemmaAnnotation.class).toLowerCase());
			sT.setPos(token.get(CoreAnnotations.PartOfSpeechAnnotation.class));

			tokens.add(sT);
			// tokenPosition++;

		}

		return tokens;
	}

}
