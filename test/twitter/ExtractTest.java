/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;
import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "@JohnDoe check this out!", d1);
    private static final Tweet tweet4 = new Tweet(4, "bbitdiddle", "Hey @janedoe, have you seen @JohnDoe's post?", d3);
    private static final Tweet tweet5 = new Tweet(5, "alyssa", "Email me at example@example.com", d3);
    
//    @Test(expected=AssertionError.class)
//    public void testAssertionsEnabled() {
//        assert false; // make sure assertions are enabled with VM argument: -ea
//    }
       
    // Test cases for getTimespan()
    
    @Test
    public void testGetTimespanEmptyList() {
        // Test when the list of tweets is empty.
        try {
            Extract.getTimespan(Collections.emptyList());
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
    
    @Test
    public void testGetTimespanSingleTweet() {
        // Test with a single tweet
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        // Test with two tweets, one before the other
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanMultipleTweetsSameTimestamp() {
        // Test with multiple tweets all having the same timestamp
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet3));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }

    @Test
    public void testGetTimespanMultipleTweetsDifferentTimestamps() {
        // Test with multiple tweets having different timestamps
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }
    
    // Test cases for getMentionedUsers()
    
    @Test
    public void testGetMentionedUsersNoMention() {
        // Test when there are no mentions in the tweet
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersSingleMention() {
        // Test with a single mention
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        assertEquals("expected one mention", Set.of("johndoe"), mentionedUsers);
    }
    
    @Test
    public void testGetMentionedUsersMultipleMentionsSingleTweet() {
        // Test with multiple mentions in a single tweet
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4));
        assertEquals("expected two mentions", Set.of("janedoe", "johndoe"), mentionedUsers);
    }
    
    @Test
    public void testGetMentionedUsersDuplicateMentionsAcrossTweets() {
        // Test with duplicate mentions across multiple tweets
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3, tweet4));
        assertEquals("expected two mentions", Set.of("johndoe", "janedoe"), mentionedUsers);
    }
    
    @Test
    public void testGetMentionedUsersIgnoreEmailAddress() {
        // Test when an email address is included in the tweet
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
        assertTrue("expected no mentions due to email", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetMentionedUsersCaseInsensitive() {
        // Test that mentions are case-insensitive
        Tweet tweet6 = new Tweet(6, "bbitdiddle", "Hey @JohnDoe, @johndoe is here", d3);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
        assertEquals("expected one mention due to case-insensitivity", Set.of("johndoe"), mentionedUsers);
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}

