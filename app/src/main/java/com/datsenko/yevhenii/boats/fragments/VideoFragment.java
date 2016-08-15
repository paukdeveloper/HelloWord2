package com.datsenko.yevhenii.boats.fragments;

import android.os.Bundle;
import android.util.Log;

import com.datsenko.yevhenii.boats.DeveloperKey;
import com.datsenko.yevhenii.boats.models.VideoEntry;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.List;

/**
 * Created by Женя on 12.08.2016.
 */
public class VideoFragment extends YouTubePlayerFragment
        implements YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener {

    private YouTubePlayer player;
    private String videoId;
    public static List<VideoEntry> video_list;

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize(DeveloperKey.DEVELOPER_KEY, this);
    }
    @Override
    public void onDestroy() {
        if (player != null) {
            player.release();
        }
        super.onDestroy();
    }

    public void setVideoId(String videoId, boolean autoPlay) {
        if (videoId != null && !videoId.equals(this.videoId)) {
            this.videoId = videoId;
            if (player != null) {
                if (autoPlay) {
                    player.loadVideo(videoId);
                } else {
                    player.cueVideo(videoId);
                }
            }
        }
    }

    public String getVideoId() {
        return videoId;
    }

    public String getNextVideoId(String currentVideoId) {
        for (int i = 0; i < VideoGridListFragment.video_list.size(); i++) {
            if (VideoGridListFragment.video_list.get(i).getVideoId().equals(currentVideoId)) {
                if ((i + 1) == VideoGridListFragment.video_list.size()) {
                    return VideoGridListFragment.video_list.get(0).getVideoId();
                } else {
                    return VideoGridListFragment.video_list.get(i + 1).getVideoId();
                }
            }
        }
        return currentVideoId;
    }

    public void pause() {
        if (player != null) {
            player.pause();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean restored) {
        this.player = player;
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
//        player.setOnFullscreenListener(this);
        player.setPlayerStateChangeListener(new VideoListener());
        if (!restored && videoId != null) {
            player.cueVideo(videoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        this.player = null;
    }

    @Override
    public void onFullscreen(boolean b) {
//        if (b) {
//            VideoGridListFragment gridListFragment = (VideoGridListFragment) getFragmentManager().findFragmentByTag("videoFragmentGridList");
//            getFragmentManager().beginTransaction().remove(gridListFragment).commit();
//            Toast.makeText(getActivity(), "true", Toast.LENGTH_SHORT).show();
//        } else {
//            VideoGridListFragment gridListFragment2 = new VideoGridListFragment();
//            getFragmentManager().beginTransaction().add(R.id.frame_grid_list,gridListFragment2,"videoFragmentGridList");
//        }
    }

    private final class VideoListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {
            Log.d("LogPlayList", "video started");
        }

        @Override
        public void onVideoEnded() {
            Log.d("LogPlayList", "video ended");
            player.loadVideo(getNextVideoId(getVideoId()));

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    }

}
