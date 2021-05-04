package com.abolton.HumanoidTweeter.services;

import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.List;

@Service
public class TweetPosterService {
    // Class designed to be used to construct a Tweet using twitter4j. Calls on the API details
    private Twitter twitter;
    private List<Status> statuses;


    public void makeTweet(String tweetBody) throws TwitterException {
        // New twitter object is created, and the status updated with the given tweetBody
        twitter = TwitterFactory.getSingleton();
        Status status = twitter.updateStatus(tweetBody);
    }
}
