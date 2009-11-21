/**
 * @file   AtroposState.java
 * @author Kyle Burke <paithan@cs.bu.edu>
 * @date   
 * 
 * @brief  This is an empty java file
 * 
 */
//package something;
 
import java.lang.*;
import java.io.*;
import java.util.*;

public class AtroposState {

  //instance variables

  /**
    * Array of AtroposCircles
    *
    * The first coordinate is the height, the second is the distance from the left.
    */
  private AtroposCircle[][] circles;

  /**
    * Last-colored circle.
    */
  private AtroposCircle lastPlay;
  
  //constants
  
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
   * @param circles   The laid-out circles.
   * @param lastPlay  The last play on the board.
   */
  public AtroposState(AtroposCircle[][] circles, AtroposCircle lastPlay) {
    this.circles = new AtroposCircle[circles.length][circles.length];
    for (int i = 0; i < circles.length; i++) {
      for (int j = 0; j < circles[i].length; j++) {
        if (circles[i][j] != null) {
          this.circles[i][j] = circles[i][j].clone();
        }
      }
    }
    this.lastPlay = null;
    if (lastPlay != null) {
      this.lastPlay = lastPlay.clone();
    }
    //System.out.println(this);
  }
  
  /**
   * Constructor by size
   */
  public AtroposState(int size) {
    if (size < 1) {
      System.err.println("The size of this board is too small!");
      return;
    }
    size = size + 2;  // this will let us use the borders.
    this.circles = new AtroposCircle[size][size];
    
    //color the bottom row red and blue
    for (int i = 1; i < size; i+=2) {
      this.circles[0][i] = new AtroposCircle(RED, 0, i, 
                                             size - i);
    }
    for (int i = 2; i < size; i+=2) {
      this.circles[0][i] = new AtroposCircle(BLUE, 0, i,
                                             size - i);
    }
    //color the left side green and red
    for (int i = 1; i < size; i+=2) {
      this.circles[i][0] = new AtroposCircle(GREEN, i, 0,
                                             size - i);
    }
    for (int i = 2; i < size; i+=2) {
      this.circles[i][0] = new AtroposCircle(RED, i, 0,
                                             size - i);
    }
    //color the right side blue and green
    for (int i = 1; i < size; i+=2) {
      this.circles[i][size - i] = 
          new AtroposCircle(BLUE, i, size - i, 0);
    }
    for (int i = 2; i < size; i+=2) {
      this.circles[i][size - i] = 
          new AtroposCircle(GREEN, i, size - i, 0);
    }
    //create uncolored circles for the rest of the board
    for (int height = 1; height < size - 1; height++) {
      for (int leftDistance = 1; 
        leftDistance < size - height; 
        leftDistance++) {
          this.circles[height][leftDistance] = 
              new AtroposCircle(UNCOLORED, height,
                                leftDistance,
                                size - height -
                                leftDistance);
      }
    } 
    this.lastPlay = null;
  System.out.println(this);
  }
  
  /**
   * Makes a deep clone of this object.
   */
  public AtroposState clone() {
    AtroposState clone;
    if (this.lastPlay != null) {
      clone = new AtroposState(this.circles, this.lastPlay.clone());
    } else {
      clone = new AtroposState(this.circles, null);
    }
    return clone;
  }
  
  /**
   * Checks to see whether a circle is a valid move.
   *
   */
  public boolean isLegalPlay(AtroposCircle play) {
    //make sure the color is legal
    int color = play.getColor();
    if (color < 1 || color > 3) {
      return false;
    }
    //check that the dimensions add up
    return this.isLegalPlayLocation(play.height(), play.leftDistance());
  }
  
  /**
   * Performs a move on the board.
   *
   * @param play  Next move to make.
   */
  public boolean makePlay(AtroposCircle play) {
    return this.makePlay(play.height(), play.leftDistance(), play.getColor());
  }
  
  /**
   * Performs a move on the board.
   *
   * @param height        Height of the circle to play.
   * @param leftDistance  Distance of the circle from the left.
   * @param color         Color to play.
   */
  public boolean makePlay(int height, int leftDistance, int color) {
    if (this.isFinished()) {
      return false;
    }
    if (!this.isLegalPlayLocation(height, leftDistance)) {
      System.err.println("This is not a legal move!");
      return false;
    }
    this.colorCircle(height, leftDistance, color);
    this.lastPlay = this.circleAt(height, leftDistance);
    return true;
  }
  
  /**
   * Determines whether the game is over.
   */
  public boolean isFinished() {
    if (this.lastPlay == null) {
      return false;
    }
    int middleColor = this.lastPlay.getColor();
    int height = this.lastPlay.height();
    int leftDistance = this.lastPlay.leftDistance();
    int leftUpColor = this.circles[height + 1][leftDistance - 1].getColor();
    int leftColor = this.circles[height][leftDistance - 1].getColor();
    int leftDownColor = this.circles[height - 1][leftDistance].getColor();
    int rightDownColor = this.circles[height - 1][leftDistance + 1].getColor();
    int rightColor = this.circles[height][leftDistance + 1].getColor();
    int rightUpColor = this.circles[height + 1][leftDistance].getColor();
    return ((this.colorConflict(middleColor, leftUpColor) &&
              this.colorConflict(middleColor, leftColor) &&
              this.colorConflict(leftUpColor, leftColor)) ||
            (this.colorConflict(middleColor, leftColor) &&
              this.colorConflict(middleColor, leftDownColor) &&
              this.colorConflict(leftColor, leftDownColor)) ||
            (this.colorConflict(middleColor, leftDownColor) &&
              this.colorConflict(middleColor, rightDownColor) &&
              this.colorConflict(leftDownColor, rightDownColor)) ||
            (this.colorConflict(middleColor, rightDownColor) &&
              this.colorConflict(middleColor, rightColor) &&
              this.colorConflict(rightDownColor, rightColor)) ||
            (this.colorConflict(middleColor, rightColor) &&
              this.colorConflict(middleColor, rightUpColor) &&
              this.colorConflict(rightColor, rightUpColor)) ||
            (this.colorConflict(middleColor, rightUpColor) &&
              this.colorConflict(middleColor, leftUpColor) &&
              this.colorConflict(rightUpColor, leftUpColor)));
  }
  
  /**
   * Options for the next play
   */
  public Iterator<AtroposCircle> playableCircles() {
    Vector<AtroposCircle> vector = new Vector<AtroposCircle>();
    for (int height = 1; height < this.circles.length; height++) {
      for (int leftDistance = 1; 
           leftDistance < this.circles.length - height; 
           leftDistance++) {
        if (this.isLegalPlayLocation(height, leftDistance)) {
          vector.add(this.circleAt(height, leftDistance));
        }
      }
    }
    return vector.iterator();
  }
  
  
  //private methods
  
  
  /**
   * Checks to see whether a location is a valid place to make the next move.
   *
   */
  private boolean isLegalPlayLocation(int height, int leftDistance) {
    if (this.isFinished()) {
      return false;
    }
    AtroposCircle circle = this.circleAt(height, leftDistance);
    if (!circle.insideBoardOfSize(this.circles.length)) {
      return false;
    }
    if (circle.isColored()) {
      return false;
    }
    if (this.canPlayAnywhere()) {
      return true;
    } else {
      return circle.adjacentTo(this.lastPlay);
    }
  }
  
  /**
   * Checks to see if the next play can be anywhere.
   */
  private boolean canPlayAnywhere() {
    if (this.isFinished()) {
      return false;
    }
    if (this.lastPlay == null) {
      return true;
    }
    int height = this.lastPlay.height();
    int leftDistance = this.lastPlay.leftDistance();
    int rightDistance = this.lastPlay.rightDistance();
    return ((this.circles[height - 1]
                         [leftDistance].isColored()) &&
            (this.circles[height - 1]
                         [leftDistance + 1].isColored()) &&
            (this.circles[height]
                         [leftDistance + 1].isColored()) &&
            (this.circles[height + 1]
                         [leftDistance].isColored()) &&
            (this.circles[height + 1]
                         [leftDistance - 1].isColored()) &&
            (this.circles[height]
                         [leftDistance - 1].isColored()));
  }
  
  /** Determines whether the two colors are not equal if both are colored. */
  private boolean colorConflict(int colorOne, int colorTwo) {
    return ((colorOne != this.UNCOLORED) &&
            (colorTwo != this.UNCOLORED) &&
            (colorOne != colorTwo));
  }
  
  /**
   * Finds the circle at a certain location.
   */
  private AtroposCircle circleAt(int height, int leftDistance) {
    return this.circles[height][leftDistance];
  }
  
  /**
   * Determines whether a circle is colored.
   *
   * @param height        Height of the circle.
   * @param leftDistance  Distance of the circle from the left.
   */
  private boolean circleIsColored(int height, int leftDistance) {
    return this.circles[height][leftDistance].isColored();
  }
  
  /**
   * Colors a given circle.
   *
   * @param height        Height of the circle.
   * @param leftDistance  Distance of the circle from the left.
   * @param color         New color for the circle.
   */
  private void colorCircle(int height, int leftDistance, int color) {
    if (this.circles[height][leftDistance].isColored()) {
      System.err.println("Error!  This circle is already colored!");
      return;
    }
    if (color < 0 || color > 3) {
      System.err.println("Error!  This is not a legal color!");
    }
    this.circles[height][leftDistance].color(color);
  }
  
  
  //toString
  
  /**
   * Returns a string version of this.
   *
   * @param indent  Indentation string.
   */
  public String toString(String indent){
    String string = "";
    for (int i = this.circles.length - 1; i >=0 ; i --) {
      //set up some nice spacing.
      for (int space = 0; space < 2*(i - 1); space++) {
        string += " ";
      }
      if (i == 0) {
        string += "  ";
      }
      string +="[";
      for (int j = 0; j < this.circles.length; j++) {
        if (this.circles[i][j] != null) {
          string += this.circles[i][j].getColor();
          if (j + i < this.circles.length) {
            if (i != 0 || j != this.circles.length - 1) {
              string += "   ";
            }
          }
        }
      }
      string += "]\n";
    }
    string += "Last Play: ";
    if (this.lastPlay == null) {
      string += "null";
    } else {
	string += this.lastPlay.getColorLocationString();
    }
    string += "\n";
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
    AtroposState atropos = new AtroposState(5);
    System.out.println(atropos);
    atropos.makePlay(2, 3, 2);
    System.out.println(atropos);
    atropos.makePlay(2, 4, 2);
    System.out.println(atropos);
    System.out.println(atropos.isFinished());
    atropos.makePlay(1, 4, 2);
    System.out.println(atropos);
    atropos.makePlay(1, 5, 2);
    System.out.println(atropos);
    atropos.makePlay(2, 2, 1);
    System.out.println(atropos);
  }
   
   
   
   
   
   
} //end of AtroposState.java
