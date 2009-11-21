/**
 * @file   AtroposPlayer.java
 * @author Kyle Burke <paithan@cs.bu.edu>
 * @date   
 * 
 * @brief  An opponent for the game Atropos
 * 
 */
//package something;
 
import java.lang.*;
import java.io.*;
import java.util.*;

public class AtroposPlayer {

  //instance variables
  
  /**
   *  Indicates the level of playing ability of this opponent.
   */
  //private int level;
  
  /**
   * Name of this player.
   */
  protected String name;

  /**
    * Blank Color.
    */
  private static final int UNCOLORED = 0;

  /**
    * Color Red.
    */
  public static final int RED = 1;
  
  /**
    * Color Blue.
    */
  public static final int BLUE = 2;
  
  /**
    * Color Green.
    */
  public static final int GREEN = 3;
  
  
  
  //public methods
  
  /**
   * Class constructor.
   *
   * @param paramname  Param description.
   */
  public AtroposPlayer(String name) {
    this.name = name;
  }
  
  /**
   * 
   */
  public AtroposCircle getNextPlay(AtroposState state) {
    Vector circles = new Vector();
    AtroposCircle circle;
    int randomIndex;
    for (Iterator circleIterator = state.playableCircles();
         circleIterator.hasNext(); ) {
      circle = (AtroposCircle) circleIterator.next();
      if (!this.wouldLose(state.clone(), circle.clone(), this.RED) ||
          !this.wouldLose(state.clone(), circle.clone(), this.BLUE) ||
          !this.wouldLose(state.clone(), circle.clone(), this.GREEN)) {
        circles.add(circle);
      }
    }
    if (circles.isEmpty()) {
      //no moves are safe.  Time to lose
      Iterator circleIterator = state.playableCircles();
      circle = (AtroposCircle) circleIterator.next();
      randomIndex = this.RED;
    } else {
      randomIndex = (int) Math.floor(circles.size() * Math.random());
      circle = (AtroposCircle) circles.get(randomIndex);
      //choose a random color
      randomIndex = (int) Math.floor(3 * Math.random()) + 1;
      while (this.wouldLose(state.clone(), circle.clone(), randomIndex)) {
        randomIndex = (int) Math.floor(3 * Math.random()) + 1;
      }
    }
    circle = circle.clone();
    circle.color(randomIndex);
    return circle;
  }
  
  /**
   * Returns the name of this player.
   */
  public String getName() {
    return this.name;
  }
  
  
  //private methods
  
  /**
   * Determines whether the specified play would lose.
   */
  private boolean wouldLose(AtroposState state, 
                            AtroposCircle circle, int color) {
    circle.color(color);
    state.makePlay(circle);
    return state.isFinished();
  }
  
  
  //toString
  
  /**
   * Returns a string version of this.
   *
   * @param indent  Indentation string.
   */
  public String toString(String indent){
  	String string = "";
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
  
  }
   
   
   
   
   
   
} //end of AtroposPlayer.java