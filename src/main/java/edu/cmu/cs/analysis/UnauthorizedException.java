package edu.cmu.cs.analysis;

import twitter4j.TwitterException;

//Step 3
public class UnauthorizedException extends TwitterException {

	/**
	 * Exception class for Unauthorized exception
	 */
	private static final long serialVersionUID = 1L;

	public UnauthorizedException(Exception cause) { 
	  super(cause);
	  // TODO Auto-generated constructor stub 
	  }

	public UnauthorizedException(String e) {
		super(e);

		// TODO Auto-generated constructor stub
	}
}
