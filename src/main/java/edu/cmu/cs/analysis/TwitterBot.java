package edu.cmu.cs.analysis;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterBot {

	static boolean debug = false;

	static Twitter twitter;

	public TwitterBot(Twitter tw) {
		twitter = TwitterFactory.getSingleton();
	}

	// if something goes wrong, we might see a TwitterException
	public static void main(String... args) {
		// send a tweet
		if (!debug) {
			try {
				// Send Tweet
				// sendTweet("Hello World!");

				// Get tweets from Home Timeline
				// getHomeTimeLine();

				// Seach for tweets
				// searchForTweets("@gdg_nd");

				// Reply to a tweet
				String query_text = "\"your welcome\"";
				String reply = "I believe you meant \"you're\" here?";
				replyToTweet(query_text, reply,twitter);

				// Reply with variety
				List<String> searches = new ArrayList<>();
				searches.add("\"your welcome\"");
				searches.add("\"your the\"");
				searches.add("\"your a \"");

				List<String> replies = new ArrayList<>();
				replies.add("I believe you meant \"you're\" here?");
				replies.add(" I've detected the wrong \"you're\". Destroy!");
				replies.add(" No, you are! Seriously. You are. \"You're\".");

				replyToTweetWithVariety(searches, replies);

			} catch (TwitterException e) {
				e.printStackTrace();
			}
		} else {
			// print a message so we know when it finishes
			System.out.println("Debug Mode Enabled!");
		}
	}

	public static String sendTweet(String text, Twitter twitt, int retries) {
		twitter = twitt;
		try {
			if(twitter==null && text == "**Test Unauthorized**")
				throw new UnauthorizedException("CUSTOM EXCEPTION:: The twitter account is not valid");
			
			if(text == null)
				throw new FailedTaskException("CUSTOM EXCEPTION:: Failed Task");
			
			if(text.length()>15)
				throw new LengthExceedException("CUSTOM EXCEPTION:: Length Exceeded");
				
			if(text.equals("**ROBUSTNESS_TEST_STRING**"))
				throw new TwitterException(text);
			return sendTweet(text, twitt);
		} catch (TwitterException e) {
			e.printStackTrace();
			if(--retries>0)
				sendTweet(text, twitt, retries);
			System.out.println("Send tried "+ (retries+1) +" times yet failed");
		}
		return text;
	}

	private static String sendTweet(String text, Twitter twitter) throws TwitterException {
		// access the twitter API using your twitter4j.properties file
		// The factory instance is re-useable and thread safe.
		// Twitter twitter = TwitterFactory.getSingleton();

		String status = "";

		status = twitter.updateStatus(text).getText();
		System.out.println("Successfully updated the status to [" + status + "].");

		return status;
	}

	public static void getHomeTimeLine() throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		List<Status> statuses = null;
		statuses = twitter.getHomeTimeline();

		System.out.println("Showing home timeline.");
		if (statuses != null) {
			for (Status status : statuses) {
				System.out.println(status.getUser().getName() + ":" + status.getText());
			}
		}
	}

	public static List<String> searchForTweets(String query_text, Twitter twitt) {
		twitter = twitt;
		try {
			
			return searchForTweets(query_text);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> searchForTweets(String query_text) throws TwitterException {
		// Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query(query_text);
		QueryResult result = twitter.search(query); ;
		return loopOverTweetsAndFind(result); 
		/* List<String> listString = new
		 * ArrayList<String>(); for (Status status : result.getTweets()) {
		 * System.out.println("@" + status.getUser().getScreenName() + ":" +
		 * status.getText()); listString.add(status.getText()); } return listString;
		 */
	}
	
	public static List<String> loopOverTweetsAndFind(QueryResult result) {
		List<String> listString = new ArrayList<String>();
		for (Status status : result.getTweets()) {
			System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
			listString.add(status.getText());
		}
		return listString;
	}

	/*
	 * public static String replyToTweet(String query_text, String reply, Twitter
	 * twitt) { twitter = twitt; try { replyToTweet(query_text, reply); } catch
	 * (TwitterException e) { return reply; } return reply; }
	 */

	public static String replyToTweet(String query_text, String reply, Twitter twitter) throws TwitterException {
		// access the twitter API using your twitter4j.properties file
		// Twitter twitter = TwitterFactory.getSingleton();
		
		// create a new search
		Query query = new Query(query_text);

		// get the results from that search
		QueryResult result = twitter.search(query);

		// get the first tweet from those results
		Status tweetResult = result.getTweets().get(0);

		// reply to that tweet
		StatusUpdate statusUpdate = new StatusUpdate(".@" + tweetResult.getUser().getScreenName() + " " + reply);
		statusUpdate.inReplyToStatusId(tweetResult.getId());
		Status status = twitter.updateStatus(statusUpdate);
		return status.getText();
	}

	public static void replyToTweetWithVariety(List<String> searches, List<String> replies) throws TwitterException {
		// access the twitter API using your twitter4j.properties file
		// Twitter twitter = TwitterFactory.getSingleton();

		// keep tweeting forever
		while (true) {
			// create a new search, chosoe from random searches
			Query query = new Query(searches.get((int) (searches.size() * Math.random())));

			// get the results from that search
			QueryResult result = twitter.search(query);

			// get the first tweet from those results
			Status tweetResult = result.getTweets().get(0);

			// reply to that tweet, choose from random replies
			StatusUpdate statusUpdate = new StatusUpdate(
					".@" + tweetResult.getUser().getScreenName() + replies.get((int) (replies.size() * Math.random())));
			statusUpdate.inReplyToStatusId(tweetResult.getId());
			Status status = twitter.updateStatus(statusUpdate);
			System.out.println("Sleeping.");

			// go to sleep for an hour
			try {
				Thread.sleep(60 * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public TwitterBot getInstance() {
		return null;
	}
}
