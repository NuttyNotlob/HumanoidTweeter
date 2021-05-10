package com.abolton.HumanoidTweeter.services;

import com.abolton.HumanoidTweeter.datastores.KeyStore;
import com.abolton.HumanoidTweeter.models.YoutubeVideo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeDataGrabberService {

    @Autowired
    KeyStore keyStore;

    private static String MOST_POPULAR_US_GAMING_VIDEOS =
            "https://www.googleapis.com/youtube/v3/videos?part=snippet&chart=mostPopular&regionCode=US&videoCategoryId=20&key=";
    private static String SPECIFIC_VIDEO_STATS =
            "https://www.googleapis.com/youtube/v3/videos?part=statistics&id=";

    private static List<YoutubeVideo> videos = new ArrayList<>();
    private static YoutubeVideo tempVideoStore;

    public List<YoutubeVideo> getVideos() {
        return videos;
    }

    @PostConstruct
    public void fetchAPIData() throws IOException, InterruptedException {
        // Set up HTML client to get data
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(MOST_POPULAR_US_GAMING_VIDEOS + keyStore.getYoutubeKey()))
                .build();

        // Send a request for the data, and then parse the body received into our videos List
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(YoutubeDataGrabberService::parseData)
                .join();
    }

    private static String parseData(String dataBody) {
        // Setup new youtube list to add to
        List<YoutubeVideo> popularVideos = new ArrayList<>();

        // The text we get from the YoutubeAPI is a JSON, with an array of JSONs embedded in the 'items' key which
        // contains the popular videos we actually want to grab the info for
        JSONObject overallArray = new JSONObject(dataBody);
        JSONArray videoArray = new JSONArray(overallArray.getJSONArray("items"));

        // Don't believe I can do a smart loop here due to the JSON library usage
        for (int i = 0; i < videoArray.length(); i++) {
            // Here we make a new video to add to our list, and get the new video from our popular videos list of JSONs.
            // The snippet part of the API containing the title is also its own JSON within the videoDetails JSON
            YoutubeVideo video = new YoutubeVideo();
            JSONObject videoDetails = videoArray.getJSONObject(i);
            JSONObject videoDetailsSnippet = videoDetails.getJSONObject("snippet");

            // Now we add the details to the video, and then add it to our list
            video.setTitle(videoDetailsSnippet.getString("title"));
            video.setChannel(videoDetailsSnippet.getString("channelTitle"));
            video.setVideoID(videoDetails.getString("id"));
            popularVideos.add(video);
        }

        // Update class game list
        videos = popularVideos;

        return null;
    }

    public boolean addVideoStats(YoutubeVideo video) {
        // Method only applicable to videos with an ID already added
        if (video.getVideoID() == null) { return false;}

        // Set up request string
        String APIRequest = SPECIFIC_VIDEO_STATS + video.getVideoID() + "&key=" + keyStore.getYoutubeKey();

        // Set up HTML client to get data
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APIRequest))
                .build();

        // Send a request for the data, and then parse the body received into our videos List
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(YoutubeDataGrabberService::parseVideoStats)
                .join();

        // Now we set the values to the values of our tempVideoStore variable which is holding the information from
        // the parseVideoStats method, and reset the variable
        video.setViewCount(tempVideoStore.getViewCount());
        video.setLikeCount(tempVideoStore.getLikeCount());
        video.setDislikeCount(tempVideoStore.getDislikeCount());
        tempVideoStore = null;
        return true;
    }

    private static String parseVideoStats(String dataBody) {
        // Reset the tempVideoStore variable
        tempVideoStore = new YoutubeVideo();

        // Set the required values for the tempVideoStore from the parsed data
        JSONObject overallArray = new JSONObject(dataBody);
        tempVideoStore.setViewCount(overallArray.getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("statistics")
                .getDouble("viewCount"));
        tempVideoStore.setLikeCount(overallArray.getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("statistics")
                .getDouble("likeCount"));
        tempVideoStore.setDislikeCount(overallArray.getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("statistics")
                .getDouble("dislikeCount"));

        return null;

    }
}
