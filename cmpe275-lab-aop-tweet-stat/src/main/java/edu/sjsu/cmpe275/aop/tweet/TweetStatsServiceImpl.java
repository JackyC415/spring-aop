package edu.sjsu.cmpe275.aop.tweet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TweetStatsServiceImpl implements TweetStatsService {
    /***
     * Following is a dummy implementation.
     * You are expected to provide an actual implementation based on the requirements.
     */
	private static int lengthOfLongestTweet = 0;
	private static String mostFollowedUser = null;
	private static Map<String, ArrayList<String>> tweetMap = new HashMap<String, ArrayList<String>>();
	private static Map<String, TreeSet<String>> distinctTweetMap = new HashMap<String, TreeSet<String>>();
	private static Map<String, Set<String>> followerMap = new HashMap<String, Set<String>>();

	@Override
	public void resetStatsAndSystem() {
		lengthOfLongestTweet = 0;
		tweetMap.clear();
		followerMap.clear();
	}
	
	public void addTweets(String user, String message) {
		if(tweetMap.containsKey(user)) {
			tweetMap.get(user).add(message);
		} else {
			tweetMap.put(user, new ArrayList<>(Arrays.asList(message)));
		}
	}
	
	public void addFollowers(String follower, String followee) {
		if(followerMap.containsKey(follower)) {
			followerMap.get(followee).add(follower);
		} else {
			followerMap.put(followee, new HashSet<String>(Arrays.asList(follower)));
		}
	}
	
	public void addDistinctTweets(String user, String message) {
		if(distinctTweetMap.containsKey(user)) {
			distinctTweetMap.get(user).add(message);
		} else {
			distinctTweetMap.put(user, new TreeSet<>(Arrays.asList(message)));
		}
	}
	
	@Override
	public int getLengthOfLongestTweet() {

		ArrayList<String> compareTweetLengthList = new ArrayList<String>();

		for (Map.Entry<String, ArrayList<String>> entry : tweetMap.entrySet()) {
			// System.out.println(entry.getKey() + " = " + entry.getValue());
			compareTweetLengthList.addAll(entry.getValue());
		}

		for (int i = 0; i < compareTweetLengthList.size(); i++) {
			int lengthOfTweet = compareTweetLengthList.get(i).length();
			if (lengthOfTweet > lengthOfLongestTweet) {
				lengthOfLongestTweet = lengthOfTweet;
			}
		}

		return lengthOfLongestTweet;
	}

	@Override
	public String getMostFollowedUser() {

		Map<String, Integer> mostFollowedUserMap = new TreeMap<String, Integer>();

		int mostFollowerSize = Integer.MIN_VALUE;
		if (!followerMap.isEmpty()) {
			for (Map.Entry<String, Set<String>> entry : followerMap.entrySet()) {
				if (entry.getValue() != null && entry.getValue().size() > mostFollowerSize) {
					mostFollowerSize = entry.getValue().size();
				}
			}

			for (Map.Entry<String, Set<String>> entry : followerMap.entrySet()) {
				if (entry.getValue().size() == mostFollowerSize) {
					mostFollowedUserMap.put(entry.getKey(), mostFollowerSize);
				}
			}

			if (!mostFollowedUserMap.isEmpty()) {
				mostFollowedUser = (String) mostFollowedUserMap.keySet().toArray()[0];
			}
		}

		return mostFollowedUser;
	}

	@Override
	public String getMostPopularMessage() {
		
		String mostPopularMessage = null;
		if(mostFollowedUser != null && distinctTweetMap.containsKey(mostFollowedUser)) {
			mostPopularMessage = distinctTweetMap.get(mostFollowedUser).first();
		}
		
		return mostPopularMessage;
	}
	
	@Override
	public String getMostProductiveUser() {
		
		String mostProductiveUser = null;
		Map<String, Integer> userTweetLengthMap = new HashMap<String, Integer>();
		Set<String> userSet = new HashSet<String>(); 
		
		//store the length of all tweets for each user in a map
		for (Map.Entry<String, ArrayList<String>> entry : tweetMap.entrySet()) {
			int totalStringLength = 0;
			for (int i = 0; i < entry.getValue().size(); i++) {
				totalStringLength += entry.getValue().get(i).length();
			}
			userTweetLengthMap.put(entry.getKey(), totalStringLength);
		}

		//find the max length of all tweets for a specific user
		int maxLengthTweets = Integer.MIN_VALUE;
		for (Map.Entry<String, Integer> entry : userTweetLengthMap.entrySet()) {
			if (entry.getValue() > maxLengthTweets) {
				maxLengthTweets = entry.getValue();
			}
		}

		//check whether there are multiple users with same max length tweets
		int frequencyOfSameLengthTweets = Collections.frequency(userTweetLengthMap.values(), maxLengthTweets);
		//multiple keys with same tweet lengths
		if (frequencyOfSameLengthTweets > 1) {
			for (Map.Entry<String, Integer> entry : userTweetLengthMap.entrySet()) {
				if(entry.getValue().equals(maxLengthTweets)) {
					userSet.add(entry.getKey());
				}
			}
			mostProductiveUser = (String) userSet.toArray()[0];
		} else {
			//get the user with highest tweet length
			for (Map.Entry<String, Integer> entry : userTweetLengthMap.entrySet()) {
				if(entry.getValue().equals(maxLengthTweets)) {
					System.out.println("max: " + maxLengthTweets);
					mostProductiveUser = entry.getKey();
					break;
				}
			}
		}

		return mostProductiveUser;
	}

	@Override
	public String getMostBlockedFollowerByNumberOfMissedTweets() {
	
		return null;
	}

	@Override
	public String getMostBlockedFollowerByNumberOfFollowees() {
		
		return null;
	}
	
}



