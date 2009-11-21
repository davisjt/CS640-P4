/**
 * @file   AtroposGame.java
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

public class AtroposGame {

  //instance variables
  
  /**
   * Stack of all the plays
   */
  private Stack<AtroposCircle> plays;
  
  /**
   * Game State.
   */
  private AtroposState gameState;
  
  /**
   * Array of players
   */
  private AtroposPlayer[] players;
  
  /**
   * Player 1.
   */
  private AtroposPlayer playerOne;
  
  /**
   * Player 2.
   */
  private AtroposPlayer playerTwo;
  
  /**
   * Player's turn.
   */
  private int currentPlayer;
  
  //constants
  
  //public methods
  
  /**
   * Class constructor.
   *
   * @param paramname  Param description.
   */
  public AtroposGame(int size, AtroposPlayer playerOne,
                     AtroposPlayer playerTwo) {
    this.gameState = new AtroposState(size);
    this.playerOne = playerOne;
    this.playerTwo = playerTwo;
    this.plays = new Stack<AtroposCircle>();
    this.currentPlayer = 1;
    this.players = new AtroposPlayer[2];
    this.players[0] = playerOne;
    this.players[1] = playerTwo;
  }
  
  /**
   * Requests the next play from the current player, then applies it to the current game.
   */
  public void makeNextPlay() {
    AtroposCircle nextPlay;
    if (this.gameState.isFinished()) {
      System.err.println("This game is already over!");
    }
    nextPlay = this.players[currentPlayer-1].getNextPlay(this.gameState.clone());
    if (!this.gameState.isLegalPlay(nextPlay)) {
      System.err.println("Attention: Player " + 
                         this.players[currentPlayer - 1].getName() + 
                         " just tried to make an illegal move!");
      System.err.println("Attempted play was: " + nextPlay);
      System.err.println("Random player will make the move instead.");
      //make a move  
      AtroposPlayer randomPlayer = new AtroposPlayer("Random");
      nextPlay = randomPlayer.getNextPlay(this.gameState.clone());
    }
    this.gameState.makePlay(nextPlay);
    this.plays.push(nextPlay);
    this.currentPlayer = 3 - this.currentPlayer;
  }
  
  /**
   * Continues to make plays until the game is finished.
   */
  public void playEntireGame() {
    while (!this.gameState.isFinished()) {
      this.makeNextPlay();
    }
  }
  
  /**
   * Determines a winner.
   *
   * @return  1 if the first player wins, 2 if the second does.
   */
  public int whoWins() {
    this.playEntireGame();
    return this.currentPlayer;
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
    if (this.gameState.isFinished()) {
      string += "The game had the following moves:\n";
    } else {
      string += "The game has had the following moves:\n";
    }
    string += this.plays + "\n";
    if (this.gameState.isFinished()) {
      if (this.plays.size()%2 == 0) {
        string += this.playerOne.getName() + " has won ";
      } else {
        string += this.playerTwo.getName() + " has won ";
      }
      string += "in " + this.plays.size() + " moves!\n";
      string += "Final board:\n";
      string += this.gameState;
    } else {
      string += "The game is not finished!\n";
      string += "Current board:\n";
      string += this.gameState;
    }
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
    AtroposPlayer playerOne;
    AtroposPlayer playerTwo;
    String s = "6";
    int gameSize = Integer.parseInt(args[0]);
    if (args.length == 1) {
      playerOne = new AtroposPlayer("Default One");
      playerTwo = new AtroposPlayer("Default Two");
    } else if (args.length == 2) {
      playerOne = new AtroposScriptPlayer("Script", args[1]);
      playerTwo = new AtroposPlayer("Default");
    } else {
      //args.length > 1
      playerOne = new AtroposScriptPlayer("Script One", args[1]);
      playerTwo = new AtroposScriptPlayer("Script Two", args[2]);
    }
    System.out.println("This will be a fantastic battle between " + 
                       playerOne.getName() + " and " + 
                       playerTwo.getName() + "!");
    //randomly determine the turn order
    Random randomGen = new Random();
    boolean oneFirst = randomGen.nextBoolean();
    AtroposGame game;
    if (oneFirst) {
      game = new AtroposGame(gameSize, playerOne, playerTwo);
      System.out.println(playerOne.getName() + " will go first.");
    } else {
      game = new AtroposGame(gameSize, playerTwo, playerOne);
      System.out.println(playerTwo.getName() + " will go first.");
    }
    game.playEntireGame();
    System.out.println(game);
  
  }
   
   
   
   
   
   
} //end of AtroposGame.java
