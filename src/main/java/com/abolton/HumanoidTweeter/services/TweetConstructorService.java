package com.abolton.HumanoidTweeter.services;

import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TweetConstructorService {
    // Class designed to be used to construct a Tweet using twitter4j. Calls on the API details
    private Twitter twitter;
    private List<Status> statuses;

    @PostConstruct
    public void makeTweet() throws TwitterException {
        twitter = TwitterFactory.getSingleton();
        Status status = twitter.updateStatus("Practise tweet from the API");
        System.out.println(status.getText());
    }
}
