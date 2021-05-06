package com.abolton.HumanoidTweeter.models;

public class Movie {
    private String title;
    private float reviewAverage;
    private String releaseDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getReviewAverage() {
        return reviewAverage;
    }

    public void setReviewAverage(float reviewAverage) {
        this.reviewAverage = reviewAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
