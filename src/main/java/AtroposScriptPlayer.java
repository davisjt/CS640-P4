/**
 * @file   AtroposScriptPlayer.java
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

public class AtroposScriptPlayer extends AtroposPlayer {

  //instance variables
  
  private String scriptName;
  
  
  //public methods
  
  /**
   * Class constructor.
   *
   * @param paramname  Param description.
   */
  public AtroposScriptPlayer(String name, String scriptName) {
    super(name);
    this.scriptName = scriptName;
  }
  
  /**
   * 
   */
  public AtroposCircle getNextPlay(AtroposState state) {
    String stateString = state.toString();
    stateString = stateString.replaceAll(" ", "");
    stateString = stateString.replaceAll("\n", "");
    Runtime runtime = Runtime.getRuntime();
    String result = "";
    try {
      Process turn = runtime.exec(this.scriptName + " " + stateString);
      BufferedInputStream output = new BufferedInputStream(turn.getInputStream());
    
      //wait for the program to finish
      turn.waitFor();
      BufferedInputStream oerr = new BufferedInputStream(turn.getErrorStream());
      //System.err.println(output_err.toString());
      //read the result of the program
      int nextInt;
      char nextChar;
      for (nextInt = output.read(); nextInt != -1; nextInt = output.read()) {
        nextChar = (char) nextInt;
        result = result + nextChar;
      }
      String result_err = "";
      for (nextInt = oerr.read(); nextInt != -1; nextInt = oerr.read()) {
        nextChar = (char) nextInt;
        result_err = result_err + nextChar;
      }
      System.err.println(result_err);

    } catch (IOException ioe) {
      System.err.println(ioe);
    } catch (InterruptedException ie) {
      System.err.println(ie);
    }
    
    //now parse the resulting string
    //result should look something like (a,b,c,d) where a, b, c and d are integers
    int height, leftDistance, rightDistance, color;
    String heightString, leftDistString, rightDistString, colorString;
    result = result.substring(1);
    int index = result.indexOf(',');
    colorString = result.substring(0, index);
    result = result.substring(index + 1);
    index = result.indexOf(',');
    heightString = result.substring(0, index);
    result = result.substring(index + 1);
    index = result.indexOf(',');
    leftDistString = result.substring(0, index);
    result = result.substring(index + 1);
    index = result.indexOf(')');
    rightDistString = result.substring(0, index);
    color = Integer.parseInt(colorString.trim());
    height = Integer.parseInt(heightString.trim());
    leftDistance = Integer.parseInt(leftDistString.trim());
    rightDistance = Integer.parseInt(rightDistString.trim());
    
    //build and return the circle
    AtroposCircle circle = new AtroposCircle(color, height, 
                                             leftDistance, rightDistance);
    return circle;
  }
  
  
  //private methods
  
  
  //toString
  
  /**
   * Returns a string version of this.
   *
   * @param indent  Indentation string.
   */
  public String toString(String indent){
    String string = "";
    string += "The program this player will call is: " + this.scriptName;
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
    Runtime runtime = Runtime.getRuntime();
    String result = "";
    try {
      Process proc = runtime.exec("echo 1");
      BufferedInputStream output = new BufferedInputStream(proc.getInputStream());
    
      //wait for the program to finish
      proc.waitFor();
    
      //read the result of the program
      int nextInt;
      char nextChar;
      for (nextInt = output.read(); nextInt != -1; nextInt = output.read()) {
        nextChar = (char) nextInt;
        result += nextChar;
      }      
    } catch (IOException ioe) {
      System.err.println(ioe);
    } catch (InterruptedException ie) {
      System.err.println(ie);
    }
    
    System.out.println(result);
    
  
  }
   
   
   
   
   
   
} //end of AtroposScriptPlayer.java
