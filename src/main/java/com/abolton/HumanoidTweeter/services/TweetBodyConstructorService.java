package com.abolton.HumanoidTweeter.services;

import com.abolton.HumanoidTweeter.datastores.TweetBodyDictionary;
import com.abolton.HumanoidTweeter.models.Movie;
import com.abolton.HumanoidTweeter.models.SteamSpyGame;
import com.abolton.HumanoidTweeter.models.YoutubeVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.TwitterException;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

@Service
public class TweetBodyConstructorService {
    private String tweetBody;

    @Autowired
    TweetPosterService tweetPosterService;
    @Autowired
    SteamSpyDataGrabberService steamSpyDataGrabberService;
    @Autowired
    YoutubeDataGrabberService youtubeDataGrabberService;
    @Autowired
    MovieDBDataGrabberService movieDBDataGrabberService;
    @Autowired
    TweetBodyDictionary tweetBodyDictionary;

    private enum APIChoices {
        STEAMSPY,
        YOUTUBE,
        MOVIEDB
    }

    public String getTweetBody() {
        return tweetBody;
    }

    @PostConstruct
    public void constructTweet() throws TwitterException {
        // Construct the tweet using the different methods for each API, depending on which is called by the
        // randomise function
        switch (chooseAPI()) {
            case STEAMSPY:
                this.tweetBody = steamspyTweet();
                break;
            case YOUTUBE:
                this.tweetBody = youtubeTweet();
                break;
            case MOVIEDB:
                this.tweetBody = movieTweet();
                break;
            default:
                this.tweetBody = null;
        }

        // Send this tweetBody to the TweetPoster, which will send it through to the Twitter API to post as a status
        System.out.println(tweetBody);

        // System requires a check input to make sure I'm happy with the tweet going out - just to make sure nothing
        // completely out there goes through
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type 'tweet' to tweet the created tweet body, or anything else to continue");
        if (scanner.nextLine().toLowerCase().equals("tweet")) {
            tweetPosterService.makeTweet(tweetBody);
        }
    }

    private APIChoices chooseAPI() {
        // This will randomly select one of the APIs queried for information, to use that info to construct the tweet
        int randomPick = new Random().nextInt(APIChoices.values().length);
        return APIChoices.values()[randomPick];
    }

    private String steamspyTweet() {
        // This method constructs a tweet body String based on one of the games taken from the SteamSpy API
        // First we retrieve a random game from our game list retrieved from the API
        SteamSpyGame game = steamSpyDataGrabberService.getSteamSpyGames()
                .get(new Random().nextInt(steamSpyDataGrabberService.getSteamSpyGames().size()));

        // From this game, a tweetBody is composed using one of the possible intros and outros from our
        // TweetBodyDictionary
        return tweetBodyDictionary.gamerIntros() +
                " I have played " + game.getName() +
                " for a whole " + game.getAverageRecentPlay() + " minutes this week"
                + tweetBodyDictionary.gamerOutros();
    }

    private String youtubeTweet() {
        YoutubeVideo popularVideo = youtubeDataGrabberService.getVideos()
                .get(new Random().nextInt(youtubeDataGrabberService.getVideos().size()));

        youtubeDataGrabberService.addVideoStats(popularVideo);

        return tweetBodyDictionary.youtubeIntros() + " Just watched " + popularVideo.getTitle() +
                " on Youtube, so glad I watched with all " + popularVideo.getViewCount() + " of you"
                + tweetBodyDictionary.youtubeOutros();
    }

    private String movieTweet() {
        Movie popularMovie = movieDBDataGrabberService.getMovies()
                .get(new Random().nextInt(movieDBDataGrabberService.getMovies().size()));

        return tweetBodyDictionary.movieIntros() + " Have been watching " + popularMovie.getTitle() + " non-stop since "
                + popularMovie.getReleaseDate() + ". I give it a score of " + popularMovie.getReviewAverage()
                + " outta 10, just like the rest of ya" + tweetBodyDictionary.movieOutros();
    }
}
