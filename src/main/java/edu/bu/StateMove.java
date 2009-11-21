package edu.bu;

/**
 * 
 * @author hmartinez
 *
 * Class to wrap a current state and move
 */
public class StateMove {
	private AtroposState newState;
	private AtroposMove move;
	
	public StateMove(AtroposState s, AtroposMove m){
		this.newState = s;
		this.move = m;
	}
	
	public AtroposState getState() {
		return this.newState;
	}
	
	public AtroposMove getMove() {
		return this.move;
	}
}
