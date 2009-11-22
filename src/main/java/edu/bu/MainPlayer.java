package edu.bu;

import java.io.IOException;
import java.io.StringReader;

/**
 * Main class for {@link Player}.
 * 
 */
public class MainPlayer {
	/**
	 * @param args
	 *            - the command-line arguments
	 * @throws IOException
	 *             - if there is an error processing the command-line arguments
	 */
	public static void main(String[] args) throws IOException {
		AtroposState state = new AtroposStateReader(new StringReader(args[0])).read();
		System.out.println(state.asString());
		
		// Calculate next move
		Player player = new Player();
		AtroposState newstate = player.makeMove(state);
		
		System.out.println(newstate.asString());
	}
}
