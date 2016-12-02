package ch.portmann.compare;

import java.util.ArrayList;
import java.util.List;
import ch.portmann.input.Sentence;
import ch.portmann.wolframalpha.Wolframalpha;

/***
 * This class don't need Stanford parsing
 * 
 */
public class Mathjax implements LGTComparer {

	public class MExpression {
		private String expression;
		private Integer[] startEndPosition;

		public String getExpression() {
			return expression;
		}

		public void setExpression(String expression) {
			this.expression = expression;
		}

		public Integer[] getStartEndPosition() {
			return startEndPosition;
		}

		public void setStartEndPosition(Integer[] startEndPosition) {
			this.startEndPosition = startEndPosition;
		}

	}

	public Mathjax() {

	}

	@Override
	public CompareCouple compare(CompareCouple compareCouple) {

		List<MExpression> mExpressionsInput = getMathjaxStrings(compareCouple.getInput());
		List<MExpression> mExpressionsSolution = getMathjaxStrings(compareCouple.getSolution());

		// Check if equal
		Wolframalpha wa = new Wolframalpha();

		for (int i = 0; i < mExpressionsInput.size(); i++) {
			if (mExpressionsSolution.size() - 1 >= i) {
				if (wa.isEqual(mExpressionsInput.get(i).getExpression(), mExpressionsSolution.get(i).getExpression(),
						true)) {

					if (mExpressionsInput.get(i).getExpression().length() * 0.85 > mExpressionsSolution.get(i)
							.getExpression().length()) {
						compareCouple.setMessage(1);
					} else if (mExpressionsInput.get(i).getExpression().length() * 1.15 < mExpressionsSolution.get(i)
							.getExpression().length()) {
						compareCouple.setMessage(2);
					}

					else {

						compareCouple.getInput().setHitandGroupe(mExpressionsInput.get(i).getStartEndPosition());
						compareCouple.getSolution().setHitandGroupe(mExpressionsSolution.get(i).getStartEndPosition());
						compareCouple.setMessage(0);

					}

				}

			}
		}

		return compareCouple;
	}

	public List<MExpression> getMathjaxStrings(Sentence s) {

		// Mathjax expressions
		List<MExpression> mExpressions = new ArrayList<MExpression>();
		String regX1 = "([{word:/\\\\?/}] [{word:/-LSB-?/}])";
		String regX2 = "([{word:/\\\\?/}] [{word:/-RSB-?/}])";

		for (Integer[] i : s.findbetween(regX1, regX2)) {
			MExpression mExpression = new MExpression();
			Integer[] tempPos = i.clone();
			tempPos[0] = tempPos[0] - 2;
			tempPos[1] = tempPos[1] + 2;
			

			if (s.isUngrouped(tempPos)) {

				mExpression.startEndPosition = tempPos;
				// replace spaces -> wolframalpha cant handle it
				mExpression.expression = s.getString(i[0], i[1]).replace(" ", "");
				mExpressions.add(mExpression);
			}
		}

		return mExpressions;
	}

}
