package edu.bu;

public class AtroposMove {
	private AtroposCircle circle;
	private Colors color;
	
	AtroposMove(AtroposCircle circle, Colors color) {
		this.circle = circle;
		this.color = color;
	}
	
	AtroposCircle getCircle() {
		return circle;
	}
	
	Colors getColor() {
		return color;
	}
}
