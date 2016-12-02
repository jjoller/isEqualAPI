package ch.portmann.main;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.portmann.input.Sentence;
import ch.portmann.stanford.StanfordCore;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.TokenSequenceMatcher;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.util.CoreMap;

public class Test {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		StanfordCore.init();

		Sentence sentence = new Sentence("The Gross domestic product of america drops from 1/2 percent to 1 percent the following week.");
		
		for(CoreLabel t : sentence.getTokens()){
			
			System.out.print(t.get(CoreAnnotations.TextAnnotation.class) + " ");
			System.out.print(t.get(CoreAnnotations.PartOfSpeechAnnotation.class));
			System.out.print(" ");
			System.out.println(t.beginPosition());
			
		}

	}

	// Artikel noch machen
	// Mutationen berücksichtigen
	// Konijunktionen tauschen

	public static boolean process(String inputText) throws ClassNotFoundException, IOException {

		StanfordCore.init();
		Sentence sentence = new Sentence(inputText);

		System.out.println(sentence.getStanfordSentence().get(CoreAnnotations.TokensAnnotation.class).size());

		for (CoreLabel token : sentence.getStanfordSentence().get(CoreAnnotations.TokensAnnotation.class)) {
			System.out.print(" ; ");
			System.out.print(token.get(CoreAnnotations.IndexAnnotation.class));
			System.out.print(" ; ");
			System.out.print(token.get(CoreAnnotations.TextAnnotation.class));
			System.out.print(" ; ");
			System.out.println(token.get(CoreAnnotations.OriginalTextAnnotation.class));

		}

		String reg = "([{word:/\\\\?/}] [{word:/-LSB-?/}]) []* ([{word:/\\\\?/}] [{word:/-RSB-?/}])";
		// String reg = "[{word:/\\\\?/}] [{word:/-LSB-?/}] []* [{word:/\\\\?/}]
		// [{word:/-RSB-?/}]";
		TokenSequencePattern pattern = TokenSequencePattern.compile(reg);

		List<CoreLabel> tokens = sentence.getTokens();

		TokenSequenceMatcher matcher = pattern.getMatcher(tokens);

		while (matcher.find()) {
			List<CoreMap> matchedTokens = matcher.groupNodes();

			System.out.println("!!!!!!");
			System.out.print(matchedTokens.toString());
			System.out.println(" ; ");
			System.out.println("StartIndex:");
			System.out.println(matchedTokens.get(0).get(CoreAnnotations.IndexAnnotation.class));
			System.out.println("StartIndex:");
			System.out.println(matchedTokens.get(matchedTokens.size() - 1).get(CoreAnnotations.IndexAnnotation.class));

		}

		return false;
	}
}