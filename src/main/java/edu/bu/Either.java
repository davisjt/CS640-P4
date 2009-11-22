/**
 * 
 */
package edu.bu;

/**
 * A generic union of <code>A</code> and <code>B</code>.
 * 
 * @author dml
 * 
 */
public abstract class Either<A, B> {
	public static final <A, B> Left<A, B> left(A left) {
		return new Left<A, B>(left);
	}
	public static final <A, B> Right<A, B> right(B right) {
		return new Right<A, B>(right);
	}

	private Either() {
	}

	public abstract boolean isLeft();
	public abstract A getLeft();
	
	public abstract boolean isRight();
	public abstract B getRight();
	
	private static final class Left<A, B> extends Either<A, B> {
		private final A left;

		public Left(A left) {
			this.left = left;
		}

		@Override
		public boolean isLeft() {
			return true;
		}
		
		@Override
		public A getLeft() {
			return left;
		}

		@Override
		public boolean isRight() {
			return false;
		}

		@Override
		public B getRight() {
			throw new UnsupportedOperationException();
		}
	}
	
	private static final class Right<A, B> extends Either<A, B> {
		private final B right;

		public Right(B right) {
			this.right = right;
		}

		@Override
		public boolean isLeft() {
			return false;
		}

		@Override
		public boolean isRight() {
			return true;
		}

		@Override
		public A getLeft() {
			throw new UnsupportedOperationException();
		}

		@Override
		public B getRight() {
			return right;
		}
	}

}
