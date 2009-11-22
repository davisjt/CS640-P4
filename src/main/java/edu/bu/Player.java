package edu.bu;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author jdavis
 *
 * Class that handles the logic of the player
 */
public class Player {	
	/**
	 * Static evaluator method to determine the value of the move
	 * 
	 * @param proposedmove
	 * 			The move that we want to make
	 * @return
	 * 			The value of that move per the static evaluators
	 */
	private int EvaluateMove(AtroposState currentstate) {
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
		    
			// Add the number of open spaces
			// More spaces = less chance of losing
			if (leftUpColor == Colors.Uncolored.getValue())
				value++;
			if (leftColor == Colors.Uncolored.getValue())
				value++;
			if (leftDownColor == Colors.Uncolored.getValue())
				value++;
			if (rightDownColor == Colors.Uncolored.getValue())
				value++;
			if (rightColor == Colors.Uncolored.getValue())
				value++;
			if (rightUpColor == Colors.Uncolored.getValue())
				value++;
			
			// Subtract for neighbors where the next neighbor is different (bad)
			if (leftUpColor != leftColor)
				value--;
			if (leftColor != leftDownColor)
				value--;
			if (leftDownColor != rightDownColor)
				value--;
			if (rightDownColor != rightColor)
				value--;
			if (rightColor != rightUpColor)
				value--;
			if (rightUpColor != leftUpColor)
				value--;
			
			// Add for neighbors with the same color (good)
			if (leftUpColor == currentstate.lastPlay.getColor())
				value++;
			if (leftColor == currentstate.lastPlay.getColor())
				value++;
			if (leftDownColor == currentstate.lastPlay.getColor())
				value++;
			if (rightDownColor == currentstate.lastPlay.getColor())
				value++;
			if (rightColor == currentstate.lastPlay.getColor())
				value++;
			if (rightUpColor == currentstate.lastPlay.getColor())
				value++;
		}
		
		return value;
	}
	
	/**
	 * Main method for the player to make a move
	 * 
	 * @param currentstate
	 * 			The current state of the board
	 * @return
	 * 			The new state of the board with the selected move
	 */
	public AtroposState makeMove(AtroposState currentstate) {
		AtroposCircle bestCircle = null;
		int bestAlpha = Integer.MIN_VALUE;
		
		// loop through all playable next circles
	    Colors[] colors = Colors.values();
		for (Iterator<AtroposCircle> circleIterator = currentstate.playableCircles(); circleIterator.hasNext(); ) {
			AtroposCircle circle = (AtroposCircle) circleIterator.next();
			
			for(int i = 1; i < colors.length; i++){
				AtroposCircle circlecopy = circle.clone();	
				circlecopy.color(i); 							// color the circle
				AtroposState nextState = currentstate.clone(); 	// copy the current state
				nextState.makePlay(circlecopy); 				// make move on copy 
				
				int alpha = alphabeta(nextState, 3, Integer.MIN_VALUE, Integer.MAX_VALUE);
				
				if (alpha > bestAlpha) {
					bestAlpha = alpha;
					bestCircle = circlecopy;
				}
			}
		}
		
		// Make the play
		currentstate.makePlay(bestCircle);
		
		// Return the board with the new move
		return currentstate;
	}
	
	/**
	 * The main method for calculating the alpha beta values for the current move
	 *  
	 * @param sm
	 * 			The StateMove object holding the current state and the proposed move
	 * @param depth
	 * 			How deep the alpha beta should go down the tree
	 * @param alpha
	 * 			The current alpha value
	 * @param beta
	 * 			The current beta value
	 * @return
	 * 			The value of the proposed move
	 */
	private int alphabeta(AtroposState state, int depth, int alpha, int beta){
		ArrayList<AtroposState> childStates = new ArrayList<AtroposState>();
		
		// If we got a losing condition or the depth is reached, return
	    if(state.isFinished() || depth == 0)
			return this.EvaluateMove(state);
	    
	    // loop through all playable next circles
	    Colors[] colors = Colors.values();
		for (Iterator<AtroposCircle> circleIterator = state.playableCircles(); circleIterator.hasNext(); ) {
			AtroposCircle circle = (AtroposCircle) circleIterator.next();
			
			for(int i = 1; i < colors.length; i++){
				AtroposCircle circlecopy = circle.clone();	
				circlecopy.color(i); 						// color the circle
				AtroposState nextState = state.clone(); 	// copy the current state
				nextState.makePlay(circlecopy); 			// make move on copy 
				childStates.add(nextState);
			}
		}
		
		// loop through all child states and call alphabeta
		for(AtroposState child : childStates) {	
			alpha = Math.max(alpha, -alphabeta(child, depth-1, -beta, -alpha));
			
			if (beta <= alpha)
				break;
		}
		
		return alpha;
	}
}
