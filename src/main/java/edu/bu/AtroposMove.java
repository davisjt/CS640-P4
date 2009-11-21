package edu.bu;

public class AtroposMove {
	private int x;
	private int y;
	private int z;
	private Colors color;
	
	AtroposMove(int x, int y, int z, Colors color) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.color = color;
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	int getZ() {
		return z;
	}
	
	Colors getColor() {
		return color;
	}
}
