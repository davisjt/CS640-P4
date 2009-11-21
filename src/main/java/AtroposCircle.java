/**
 * @file   AtroposCircle.java
 * @author Kyle Burke <paithan@cs.bu.edu>
 * @date   
 * 
 * @brief  This defines one circle of the sperner triangle.
 * 
 */
//package something;
 
//import java.lang.*;
//import java.io.*;
import java.util.*;

public class AtroposCircle {

  /**
    * Blank Color.
    */
  private static final int UNCOLORED = 0;

  /**
    * Color Red.
    */
  private static final int RED = 1;
  
  /**
    * Color Blue.
    */
  private static final int BLUE = 2;
  
  /**
    * Color Green.
    */
  private static final int GREEN = 3;

  /**
    * Color of the circle.
    */
  private int color;
  
  /**
    * Height of the circle (0 = bottom).
    */
  private int height;
  
  /**
    * Distance from the left side of the triangle (0 = on the left side).
    */
  private int leftDistance;
  
  /**
    * Distance form the right side of the triangle (0 = on the right side).
    */
  private int rightDistance;
	
  

  /**
   * Class constructor.  Creates an empty circle.
   *
   * @param height         Height of the circle.
   * @param leftDistance   Left Distance of the circle.
   * @param rightDistance  Right Distance of the circle.
   */
  public AtroposCircle(int height, int leftDistance, 
                       int rightDistance) {
    this.color = UNCOLORED;
    this.height = height;
    this.leftDistance = leftDistance;
    this.rightDistance = rightDistance;
  }  
  
  /**
   * Class constructor.  Creates an colored circle.
   *
   * @param color          Color of the circle.
   * @param height         Height of the circle.
   * @param leftDistance   Left Distance of the circle.
   * @param rightDistance  Right Distance of the circle.
   */
  public AtroposCircle(int color, int height, int leftDistance, 
                       int rightDistance) {
    if (color > 3 || color < 0) {
    	System.out.println("Error: Not a legal color!");
    }
    this.color = color;
    this.height = height;
    this.leftDistance = leftDistance;
    this.rightDistance = rightDistance;
  }  
  
  public AtroposCircle clone() {
    return new AtroposCircle(this.color, this.height, this.leftDistance, this.rightDistance);
  }
  
  /**
   * Colors an uncolored circle.
   *
   * @param color  New color for this.
   */
  public void color(int color) {
    if (this.color != UNCOLORED) {
      System.out.println("Error: This circle is already colored!");
      return;
    }
    this.color = color;
  }
  
  /**
   * Returns the color of this.
   */
  public int getColor() {
  	return this.color;
  }
  
  /**
   * Returns the height of this.
   */
  public int height() {
  	return this.height;
  }
  
  /**
   * Returns the left distance of this.
   */
  public int leftDistance() {
  	return this.leftDistance;
  }
  
  /**
   * Returns the right distance of this.
   */
  public int rightDistance() {
  	return this.rightDistance;
  }
  
  /**
   * Determines whether a circle is colored.
   *
   */
  public boolean isColored() {
  	return this.color != UNCOLORED;
  }
  
  /**
   * Determines whether this circle is adjacent to another.
   *
   * @param circle  Circle we're comparing with.
   */
  public boolean adjacentTo(AtroposCircle circle) {
  	return (((Math.abs(this.height - circle.height()) == 1) &&
  	         (this.leftDistance == circle.leftDistance())) ||
  	        ((Math.abs(this.leftDistance -
  	                   circle.leftDistance()) == 1) &&
  	         (this.height == circle.height())) ||
  	        ((Math.abs(this.height -
  	                   circle.height()) == 1) &&
  	         (this.rightDistance == circle.rightDistance())));
  }
  
  /**
   * Determines whether this circle is in a board of the given size.
   *
   * @param size  Size of the board.
   */
  public boolean insideBoardOfSize(int size) {
    //this test removes the corners
    if (this.height == size ||
        this.leftDistance == size ||
        this.rightDistance == size) {
      return false;
    }
    //then just make sure it's inside the board
    return (this.height + this.leftDistance + this.rightDistance == size);
  }
  
  public String getLocationString() {
    return "(" + this.height + ", " + this.leftDistance + ", " +
	this.rightDistance + ")";}
  public String getColorLocationString() {
    return "(" + this.color + ", " + this.height + ", " + this.leftDistance + ", " + this.rightDistance + ")";}
  
  /**
   * Returns a string version of this.
   *
   * @param indent  Indentation string.
   */
  public String toString(String indent){
  	String string = "";
  	string += "Circle colored " + this.color + " at: (" +
  	          this.height + ", " + this.leftDistance + ", " +
  	          this.rightDistance + ").\n";
  	return string;
  }
  
  /**
   * Returns a string version of this.
   */
  public String toString() {
  	return this.toString("");
  }
   
  /**
   * Main method for testing.
   */
  public static void main(String[] args) { 
  	AtroposCircle circle = new AtroposCircle(0, 1, 2);
  	System.out.println(circle.toString());
  	circle.color(RED);
  	System.out.println(circle.toString());
  }
   
   
   
   
   
   
} //end of AtroposCircle.java
