/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.List;
import java.util.Set;
import java.time.Instant;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */

public class Extract {

    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets == null || tweets.isEmpty()) {
            throw new IllegalArgumentException("Invalid input!!!");
        }
        
        Instant start = tweets.get(0).getTimestamp();
        Instant end = tweets.get(0).getTimestamp();

        for (Tweet tweet : tweets) {
            Instant currentTimestamp = tweet.getTimestamp();
            if (currentTimestamp.isBefore(start)) {
                start = currentTimestamp;
            }
            if (currentTimestamp.isAfter(end)) {
                end = currentTimestamp;
            }
        }

        return new Timespan(start, end);
    }

    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();

        // Regular expression to match Twitter usernames following '@'
        Pattern pattern = Pattern.compile("(?<=^|\\s)@([A-Za-z0-9_]+)(?=\\b)");

        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                String username = matcher.group(1).toLowerCase(); 
                mentionedUsers.add(username);
            }
        }

        return mentionedUsers;
    }

}
