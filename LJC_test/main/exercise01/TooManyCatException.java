package exercise01;

public class TooManyCatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TooManyCatException() {
	}
	public TooManyCatException(String msg) {
		super(msg);
	}
	public TooManyCatException(Throwable t) {
		super(t);
	}
}
