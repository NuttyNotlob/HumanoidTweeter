package com.abolton.HumanoidTweeter.services;

import com.abolton.HumanoidTweeter.datastores.KeyStore;
import com.abolton.HumanoidTweeter.models.Movie;
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
public class MovieDBDataGrabberService {

    @Autowired
    KeyStore keyStore;

    private static String MOST_POPULAR_MOVIES =
            "https://api.themoviedb.org/3/discover/movie?id=460465&api_key=";

    private static List<Movie> movies = new ArrayList<>();

    public List<Movie> getMovies() { return movies; }

    @PostConstruct
    public void fetchAPIData() throws IOException, InterruptedException {
        // Set up HTML client to get data
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(MOST_POPULAR_MOVIES + keyStore.getMovieKey()))
                .build();

        // Send a request for the data, and then parse the body received into our videos List
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(MovieDBDataGrabberService::parseData)
                .join();
    }
    private static String parseData(String dataBody) {
        // Setup new movie list to add to
        List<Movie> newMovies = new ArrayList<>();

        // The text we get from the MovieDB API is a JSON, with an array of JSONs embedded in the 'results' key which
        // contains the data for the most popular movies
        JSONObject overallArray = new JSONObject(dataBody);
        JSONArray movieArray = new JSONArray(overallArray.getJSONArray("results"));

        // Don't believe I can do a smart loop here due to the JSON library usage
        for (int i = 0; i < movieArray.length(); i++) {
            // Here we make a new movie to add to our list, and then want to look at the i result in our array
            Movie movie = new Movie();
            JSONObject movieDetails = movieArray.getJSONObject(i);


            // Now we add the details to the movie. Only adding movies where they are listed as adult == false, just to
            // keep out anything 18+. Otherwise we just pass over it
            if (!movieDetails.getBoolean("adult")) {
                movie.setTitle(movieDetails.getString("title"));
                movie.setReleaseDate(movieDetails.getString("release_date"));
                movie.setReviewAverage(movieDetails.getFloat("vote_average"));

                // Add it to our movies list
                newMovies.add(movie);
            }
        }

        // Update class movie list
        movies = newMovies;

        return null;
    }

}
