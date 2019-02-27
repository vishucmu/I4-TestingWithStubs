package edu.cmu.cs.analysis;

import twitter4j.TwitterException;

//Step 3
public class FailedTaskException extends TwitterException {

	/**
	 * Failed Task Exception
	 */
	private static final long serialVersionUID = 1L;

	public FailedTaskException(Exception cause) {
		super(cause);
	}

	public FailedTaskException(String cause) {
		super(cause);
	}
}
