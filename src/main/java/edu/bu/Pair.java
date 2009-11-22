/**
 * 
 */
package edu.bu;

/**
 * A generic tuple with two elements.
 * 
 * @author dml
 * 
 * @param <A>
 *            - the type of {@link #first}
 * @param <B>
 *            - the type of {@link #second}
 */
public class Pair<A, B> {
	public final A first;
	public final B second;
	public Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + first + "," + second + ")";
	}
}