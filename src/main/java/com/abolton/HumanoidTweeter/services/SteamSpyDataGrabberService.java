package com.abolton.HumanoidTweeter.services;

import com.abolton.HumanoidTweeter.models.SteamSpyGame;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SteamSpyDataGrabberService {
    // This service sends a GET request to the SteamSpy API for the top 100 games in the last 2 weeks. This information
    // is then displayed on the homepage, and is also used in the construction of a tweet for the twitter bot

    private static String TOP_100_RECENT_GAMES = "https://steamspy.com/api.php?request=top100in2weeks";
    private static List<SteamSpyGame> steamSpyGames = new ArrayList<>();

    public List<SteamSpyGame> getSteamSpyGames() {
        return steamSpyGames;
    }

    @PostConstruct
    public void fetchAPIData() throws IOException, InterruptedException {
        // Set up HTML client to get data
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(TOP_100_RECENT_GAMES)).build();

        // Send a request for the data, and then parse the body received so it becomes our new steamSpyGames List
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(SteamSpyDataGrabberService::parseData)
                .join();
    }

    private static String parseData(String dataBody) {
        // Setup new game list to add to
        List<SteamSpyGame> newGames = new ArrayList<>();

        // Now we create a JSONObject from this String from the dataBody. This is essentially a JSON os JSONs, so we'll need to get all the keys and iterate through them
        JSONObject overallArray = new JSONObject(dataBody);

        // Our overallArray is essentially a JSON os JSONs, so we'll need to get all the keys and iterate through them.
        // Now we need to access each of these using the key which is the ID given to it by SteamSpy. This isn't just 1
        // - 100, so we actually need to get the keySet and iterate through that on each JSON
        Set<String> keys = overallArray.keySet();
        for (String key : keys) {
            // Set the game we're looking at
            JSONObject gameDetails = overallArray.getJSONObject(key);

            // Now we make a new SteamSpyGame object, and add all the details to it
            SteamSpyGame game = new SteamSpyGame();
            game.setName(gameDetails.getString("name"));
            game.setPositiveReviews(gameDetails.getDouble("positive"));
            game.setNegativeReviews(gameDetails.getDouble("negative"));
            game.setOwners(gameDetails.getString("owners"));
            game.setAverageRecentPlay(gameDetails.getInt("average_2weeks"));
            newGames.add(game);
        }

        // Update class game list
        steamSpyGames = newGames;

        return null;
    }

}
