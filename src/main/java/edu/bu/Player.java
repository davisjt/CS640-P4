package edu.bu;

import java.util.ArrayList;
import java.util.Iterator;

public class Player {	
	/**
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
	
	private boolean wouldLose(AtroposState state, AtroposMove proposedmove) {
		proposedmove.getCircle().color(proposedmove.getColor().getValue());
		state.makePlay(proposedmove.getCircle());
		
		return state.isFinished();
	}
	
	public AtroposState makeMove(AtroposState currentstate) {
		AtroposCircle bestCircle = null;
		int bestval = -200;
		Colors[] colors = Colors.values();
		
		// loop through all playable next circles
		for (Iterator<AtroposCircle> circleIterator = currentstate.playableCircles(); circleIterator.hasNext();) {
			AtroposCircle circle = (AtroposCircle) circleIterator.next();
		
			for(int i=1; i< colors.length; i++){
				AtroposMove nextMove = new AtroposMove(circle, colors[i]);	// make new move	
				circle.color(i); 											// color the circle
				AtroposState nextState = currentstate.clone(); 				// copy the current state
				nextState.makePlay(circle);
				
				int val = alphabeta(new StateMove(currentstate, nextMove), 3, -100, 100);
				
				if (val > bestval) {
					bestval = val;
					bestCircle = circle;
				}
			}
		}
	
		currentstate.makePlay(bestCircle);
		
		return currentstate;
	}
	
	private int alphabeta(StateMove sm, int depth, int alpha, int beta){
		ArrayList <StateMove> childStateMoves = new ArrayList<StateMove>();
		AtroposState currState = sm.getState();
		
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
