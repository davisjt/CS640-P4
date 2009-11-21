package edu.bu;

public enum Colors {
	Uncolored (0), 
	Red (1), 
	Blue (2), 
	Green (3);
	
	private final int value;
	
	Colors(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
