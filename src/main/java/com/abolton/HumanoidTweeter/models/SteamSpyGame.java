package com.abolton.HumanoidTweeter.models;

public class SteamSpyGame implements Comparable {
    private String name;
    private double positiveReviews;
    private double negativeReviews;
    private String owners;
    private int averageRecentPlay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPositiveReviews() {
        return positiveReviews;
    }

    public void setPositiveReviews(double positiveReviews) {
        this.positiveReviews = positiveReviews;
    }

    public double getNegativeReviews() {
        return negativeReviews;
    }

    public void setNegativeReviews(double negativeReviews) {
        this.negativeReviews = negativeReviews;
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public int getAverageRecentPlay() {
        return averageRecentPlay;
    }

    public void setAverageRecentPlay(int averageRecentPlay) {
        this.averageRecentPlay = averageRecentPlay;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
