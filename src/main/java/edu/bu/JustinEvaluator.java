/**
 * 
 */
package edu.bu;

/**
 * @author jdavis
 *
 */
public class JustinEvaluator implements Evaluator {

	/* (non-Javadoc)
	 * @see edu.bu.Evaluator#evaluateMove(edu.bu.AtroposState)
	 */
	@Override
	public int evaluateMove(AtroposState currentstate) {
		int value = 0;
		
		// If this is a losing move, immediately return -100
		if (currentstate.isFinished())
			return -100;
		else {
			// Get neighbors
			int height = currentstate.lastPlay.height();
		    int leftDistance = currentstate.lastPlay.leftDistance();
		    int leftUpColor = currentstate.circles[height + 1][leftDistance - 1].getColor();
		    int leftColor = currentstate.circles[height][leftDistance - 1].getColor();
		    int leftDownColor = currentstate.circles[height - 1][leftDistance].getColor();
		    int rightDownColor = currentstate.circles[height - 1][leftDistance + 1].getColor();
		    int rightColor = currentstate.circles[height][leftDistance + 1].getColor();
		    int rightUpColor = currentstate.circles[height + 1][leftDistance].getColor();
		    
		    // Add the number of colored spaces
			// More spaces = less chance of losing
			if (leftUpColor != Colors.Uncolored.getValue())
				value++;
			if (leftColor != Colors.Uncolored.getValue())
				value++;
			if (leftDownColor != Colors.Uncolored.getValue())
				value++;
			if (rightDownColor != Colors.Uncolored.getValue())
				value++;
			if (rightColor != Colors.Uncolored.getValue())
				value++;
			if (rightUpColor != Colors.Uncolored.getValue())
				value++;
			
			// Add for neighbors where the next neighbor is different
			if (leftUpColor != leftColor)
				value++;
			if (leftColor != leftDownColor)
				value++;
			if (leftDownColor != rightDownColor)
				value++;
			if (rightDownColor != rightColor)
				value++;
			if (rightColor != rightUpColor)
				value++;
			if (rightUpColor != leftUpColor)
				value++;
			
			// Add for neighbors with the different color
			if (leftUpColor != currentstate.lastPlay.getColor() && leftUpColor != Colors.Uncolored.getValue())
				value++;
			if (leftColor != currentstate.lastPlay.getColor() && leftColor != Colors.Uncolored.getValue())
				value++;
			if (leftDownColor != currentstate.lastPlay.getColor() && leftDownColor != Colors.Uncolored.getValue())
				value++;
			if (rightDownColor != currentstate.lastPlay.getColor() && rightDownColor != Colors.Uncolored.getValue())
				value++;
			if (rightColor != currentstate.lastPlay.getColor() && rightColor != Colors.Uncolored.getValue())
				value++;
			if (rightUpColor != currentstate.lastPlay.getColor() && rightUpColor != Colors.Uncolored.getValue())
				value++;
		}
		
		return value;
	}

}
