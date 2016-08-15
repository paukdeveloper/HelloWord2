package com.datsenko.yevhenii.boats.models;

/**
 * Created by Yevhenii on 5/18/2016.
 */
public final class VideoEntry {
    private final String text;
    private final String videoId;

    public VideoEntry(String text, String videoId) {
        this.text = text;
        this.videoId = videoId;
    }

    public String getText() {
        return text;
    }

    public String getVideoId() {
        return videoId;
    }
}
