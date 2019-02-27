package edu.cmu.cs.analysis;

import twitter4j.TwitterException;

//Step 3
public class LengthExceedException extends TwitterException {

	/**
	 * Exception class for Length
	 */
	private static final long serialVersionUID = 1L;

	public LengthExceedException(Exception cause) {
		super(cause);
	}

	public LengthExceedException(String message) {
		super(message);
	}
}