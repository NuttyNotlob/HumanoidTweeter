package com.abolton.HumanoidTweeter.models;

public class YoutubeVideo {
    private String title;
    private String channel;
    private String videoID;
    private double viewCount;
    private double likeCount;
    private double dislikeCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public double getViewCount() {
        return viewCount;
    }

    public void setViewCount(double viewCount) {
        this.viewCount = viewCount;
    }

    public double getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(double likeCount) {
        this.likeCount = likeCount;
    }

    public double getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(double dislikeCount) {
        this.dislikeCount = dislikeCount;
    }
}
