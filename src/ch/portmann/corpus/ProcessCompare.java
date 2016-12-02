package ch.portmann.corpus;

import java.io.IOException;
import ch.portmann.cash.MapDBStore;
import ch.portmann.compare.CompareCouple;
import ch.portmann.compare.ComparePipe;
import ch.portmann.compare.HandMadeEnglish;
import ch.portmann.compare.Mathjax;
import ch.portmann.compare.NumExpressions;
import ch.portmann.compare.OneToOne;
import ch.portmann.compare.QuantExpressions;
import ch.portmann.compare.SynoEnglish;
import ch.portmann.input.Sentence;
import ch.portmann.stanford.StanfordCore;
import edu.stanford.nlp.ling.CoreLabel;

public class ProcessCompare {

	public ProcessCompare() {

	}

	public static void main(String[] args) throws IOException {

		StanfordCore.init();

		ComparePipe comparePipe = new ComparePipe();

		// killers
		comparePipe.addKillerPipe(new OneToOne());

		// preprocessing
		// ---

		// and pipes
		//comparePipe.addAndPipe(new Mathjax());
		//comparePipe.addAndPipe(new SynoEnglish());
		//comparePipe.addAndPipe(new HandMadeEnglish());
		comparePipe.addAndPipe(new NumExpressions());
		//comparePipe.addAndPipe(new QuantExpressions());

		// check pipe
		

		Sentence input = null;
		Sentence solution = null;
		try {
			input = new Sentence("The Gross domestic product of america drops from 1/2 percent to 1 percent the following week.");
			solution = new Sentence("A GDP of the USA drops from 0.5% to 1% next week.");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CompareCouple compareCouple = comparePipe.run(new CompareCouple(input, solution));

		int i = 0;
		for (boolean b : compareCouple.getInput().getIsHit()) {

			System.out.print(input.getToken(i) + ": ");
			System.out.print(b + "; ");
			i++;
		}

		System.out.println(" ");
		for (CoreLabel s : input.getTokens()) {

			System.out.print(s + " ");
		}

		System.out.println(compareCouple.getInput().isAllHit() && compareCouple.getSolution().isAllHit());
		System.out.println(compareCouple.getMessage());
		
		MapDBStore.getInstance().close();
	}
}
