package edu.bu;

//player did move to make newState
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
