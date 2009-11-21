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
	private int EvaluateMove(AtroposState currentstate, AtroposMove proposedmove) {
		int value = 0;
		
		// If this is a losing move, immediately return -100
		if (wouldLose(currentstate, proposedmove))
			return -100;
		else {
			// Get neighbors
			int height = proposedmove.getCircle().height();
		    int leftDistance = proposedmove.getCircle().leftDistance();
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
			if (leftUpColor == proposedmove.getColor().getValue())
				value++;
			if (leftColor == proposedmove.getColor().getValue())
				value++;
			if (leftDownColor == proposedmove.getColor().getValue())
				value++;
			if (rightDownColor == proposedmove.getColor().getValue())
				value++;
			if (rightColor == proposedmove.getColor().getValue())
				value++;
			if (rightUpColor == proposedmove.getColor().getValue())
				value++;
		}
		
		return value;
	}
	
	/**
	 * Method to determine if the move would cause the game to end
	 * 
	 * @param state
	 * 			The current state of the board
	 * @param proposedmove
	 * 			The proposed move to make
	 * @return
	 * 			If the move would result in the game ending
	 */
	private boolean wouldLose(AtroposState state, AtroposMove proposedmove) {
		proposedmove.getCircle().color(proposedmove.getColor().getValue());
		state.makePlay(proposedmove.getCircle());
		
		return state.isFinished();
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
		int bestval = -200;
		Colors[] colors = Colors.values();
		
		// loop through all playable next circles
		for (Iterator<AtroposCircle> circleIterator = currentstate.playableCircles(); circleIterator.hasNext();) {
			AtroposCircle circle = (AtroposCircle) circleIterator.next();
		
			// Iterate through each color
			for(int i=1; i< colors.length; i++){
				circle.color(i); 											// color the circle
				AtroposMove nextMove = new AtroposMove(circle, colors[i]);	// make new move	
				AtroposState nextState = currentstate.clone(); 				// copy the current state
				nextState.makePlay(circle);
				
				// Get the value of the move based on alpha beta procedure
				int val = alphabeta(new StateMove(currentstate, nextMove), 3, -100, 100);
				
				// Is it better?
				if (val > bestval) {
					bestval = val;
					bestCircle = circle;
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
	private int alphabeta(StateMove sm, int depth, int alpha, int beta){
		ArrayList <StateMove> childStateMoves = new ArrayList<StateMove>();
		AtroposState currState = sm.getState();
		
		// If we got a losing condition or the depth is reached, return
	    if( currState.isFinished() || depth == 0)
			return this.EvaluateMove(sm.getState(), sm.getMove());
	    
	    // loop through all playable next circles
	    Colors[] colors = Colors.values();
		for (Iterator<AtroposCircle> circleIterator = currState.playableCircles(); circleIterator.hasNext(); ) {
			AtroposCircle circle = (AtroposCircle) circleIterator.next();
			
			for(int i=1; i< colors.length; i++){
				AtroposMove nextMove = new AtroposMove(circle, colors[i]);	// make new move	
				circle.color(i); 											// color the circle
				AtroposState nextState = currState.clone(); 				// copy the current state
				nextState.makePlay(circle); 								// make move on copy
				StateMove nextSM = new StateMove(nextState, nextMove);		// create new StateMove object 
				childStateMoves.add(nextSM);
			}
		}
		
		// loop through all child states and call alphabeta
		for(StateMove childSM : childStateMoves) {
			alpha = Math.max(alpha, -alphabeta(childSM, depth-1, -beta, -alpha));
			if (beta<=alpha)
				break;
		}
		
		return alpha;
	}
}
