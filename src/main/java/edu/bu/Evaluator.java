package edu.bu;

/**
 * A class which can rate a {@link AtroposState}.
 * 
 * @author dml
 * 
 */
public interface Evaluator {
	
	/**
	 * Static evaluator method to determine the value of the move
	 * 
	 * @param proposedmove
	 * 			The move that we want to make
	 * @return
	 * 			The value of that move per the static evaluators
	 */
	int evaluateMove(AtroposState currentstate); 

}
