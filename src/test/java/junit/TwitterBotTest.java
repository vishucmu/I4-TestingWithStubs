package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.cmu.cs.analysis.FailedTaskException;
import edu.cmu.cs.analysis.LengthExceedException;
import edu.cmu.cs.analysis.TwitterBot;
import edu.cmu.cs.analysis.UnauthorizedException;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterBotTest {

	@Mock
	private static Twitter tw;

	public TwitterBotTest() {
		MockitoAnnotations.initMocks(this);
	}

	// Step 1
	@Test
	public void testSendTweets() {
		String testString = "HeyTest";
		try {
			String expected = TwitterBot.sendTweet(testString, tw, 1);
			assertEquals(expected, "HeyTest");
		} catch (Exception ex) {
		}
	}

	// Step 2
	@Test
	public void testRobustnessSendTweets() {
		String testString = "**ROBUSTNESS_TEST_STRING**";
		Boolean result = false;
		try {
			String expected = TwitterBot.sendTweet(testString, tw, 3); // try for three times
		} catch (Exception ex) {
			System.out.println("Robustness test");
			if(ex instanceof FailedTaskException)
				result = true;
		}
		assertTrue(result);

		
	}
	
	@Test
	public void testLengthTweets() {
		String testString = "**AVERYLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO0000000NG_TEST_STRING123456789123456789123456789123456789**";
		Boolean result = false;
		try {
			String expected = TwitterBot.sendTweet(testString, tw, 3); // try for three times
		} catch (Exception ex) {
			System.out.println("Tweet length test");
			if(ex instanceof LengthExceedException)
				result = true;
		}
		assertTrue(result);

		
	}

	// Step 1
	@Test
	public void testSearchTweets() throws TwitterException {
		String testString = "HeyTest";
		TwitterBot.sendTweet(testString, tw, 1);
		List<String> expected = TwitterBot.searchForTweets(testString, tw);
		boolean result = false;
		if (expected.contains("HeyTest"))
			result = true;
		assertTrue(result);
	}

	// Step 1
	@Test
	public void testReplyToTweets() throws TwitterException {
		String query_text = "HeyTest";
		String reply = "HeyTest";

		String expected = TwitterBot.replyToTweet(query_text, reply, tw);
		boolean result = false;
		if (expected.contains("HeyTest"))
			result = true;
		assertTrue(result);
	}

	@Test
	public void testUnauthorizedException() {
		String testString = "**Test Unauthorized**";
		Boolean result = false;
		try {
			String expected = TwitterBot.sendTweet(testString, null, 1); 
		} catch (Exception ex) {
			if(ex instanceof UnauthorizedException)
				result = true;
		}
		assertTrue(result);
	}
}
