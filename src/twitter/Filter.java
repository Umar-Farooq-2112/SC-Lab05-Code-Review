package twitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Filter {

// Invariant 1
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        List<Tweet> result = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getAuthor().equals(username)) {
                result.add(tweet);
            }
        }
        return result;
    }
	//Invariant 2
/* 
 	public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
	    List<Tweet> result = new ArrayList<>();
	    Iterator<Tweet> iterator = tweets.iterator();

	    while (iterator.hasNext()) {
	        Tweet tweet = iterator.next();
	        if (tweet.getAuthor().equals(username)) {
	            result.add(tweet);
	        }
	    }

	    return result;
	}
 */
	// Invariant 3
/*public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
	    List<Tweet> result = new ArrayList<>();
	    tweets.forEach(tweet -> {
	        if (tweet.getAuthor().equals(username)) {
	            result.add(tweet);
	        }
	    });
	    return result;
	}
*/    
//    Invariant 1
	public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
		List<Tweet> result = new ArrayList<>();
		for (Tweet tweet : tweets) {
			if (tweet.getTimestamp().isAfter(timespan.getStart()) && tweet.getTimestamp().isBefore(timespan.getEnd())) {
				result.add(tweet);
			}
		}
		return result;
	}

//	Invariant 2
	/*
	 public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
	    return tweets.stream()
	                 .filter(tweet -> tweet.getTimestamp().isAfter(timespan.getStart()) &&
	                                  tweet.getTimestamp().isBefore(timespan.getEnd()))
	                 .collect(Collectors.toList());
	}
	
	*/
    //Invariant 3
    /*
     public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        List<Tweet> result = new ArrayList<>(tweets);
        for (Tweet tweet : tweets) {
            if (!(tweet.getTimestamp().isAfter(timespan.getStart()) && tweet.getTimestamp().isBefore(timespan.getEnd()))) {
                result.remove(tweet);
            }
        }
        return result;
    }
*/
	
// Invariant 1	
	public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        List<Tweet> result = new ArrayList<>();
        // Convert words to lowercase for case-insensitive comparison
        List<String> lowerCaseWords = new ArrayList<>();
        for (String word : words) {
            lowerCaseWords.add(word.toLowerCase(Locale.ROOT));
        }

        for (Tweet tweet : tweets) {
            String tweetText = tweet.getText().toLowerCase(Locale.ROOT);
            for (String word : lowerCaseWords) {
                if (tweetText.contains(word)) {
                    result.add(tweet);
                    break; // No need to check further words once we found a match
                }
            }
        }
        return result;
    }
	
// Invariant 2
	/*
	public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
	    List<Tweet> result = new ArrayList<>();
	    for (Tweet tweet : tweets) {
	        String tweetText = tweet.getText().toLowerCase(Locale.ROOT);
	        for (String word : words) {
	            if (tweetText.contains(word.toLowerCase(Locale.ROOT))) {
	                result.add(tweet);
	                break; // Move to the next tweet once a match is found
	            }
	        }
	    }
	    return result;
	}
	*/
	
//	Invariant 3
	/*

public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
    List<Tweet> result = new ArrayList<>();
    for (Tweet tweet : tweets) {
        if (containsAnyWord(tweet.getText(), words)) {
            result.add(tweet);
        }
    }
    return result;
}

// Helper function to check if tweet contains any word
private static boolean containsAnyWord(String text, List<String> words) {
    String lowerCaseText = text.toLowerCase(Locale.ROOT);
    for (String word : words) {
        if (lowerCaseText.contains(word.toLowerCase(Locale.ROOT))) {
            return true;
        }
    }
    return false;
}
	
	*/
	
	
	
}
