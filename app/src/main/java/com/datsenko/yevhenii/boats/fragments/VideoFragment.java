package com.datsenko.yevhenii.boats.fragments;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.datsenko.yevhenii.boats.DeveloperKey;
import com.datsenko.yevhenii.boats.R;
import com.datsenko.yevhenii.boats.activity.DetailBoatActivity;
import com.datsenko.yevhenii.boats.activity.MainActivity;
import com.datsenko.yevhenii.boats.adapters.MySpinnerAdapter;
import com.datsenko.yevhenii.boats.models.Boat;
import com.datsenko.yevhenii.boats.models.BoatsCharacteristics;
import com.datsenko.yevhenii.boats.models.VideoEntry;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Женя on 12.08.2016.
 */
public class VideoFragment extends YouTubePlayerFragment
        implements YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener {

    private YouTubePlayer player;
    private String videoId;
    public static List<VideoEntry> video_list;
    private ArrayList<BoatsCharacteristics> mCharacteristicsArrayList;


    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        setupSpinner();

        return super.onCreateView(layoutInflater, viewGroup, bundle);
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
        player.setOnFullscreenListener(this);
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
//            VideoGridListFragment gridListFragment = (VideoGridListFragment) getFragmentManager().findFragmentById(R.id.boats_grid_frame);
//            getFragmentManager().beginTransaction().remove(gridListFragment).commit();
//            Toast.makeText(getActivity(), "true", Toast.LENGTH_SHORT).show();
//        } else {
//            VideoGridListFragment gridListFragment2 = new VideoGridListFragment();
//            Bundle arg = new Bundle();
//            arg.putStringArrayList("videos", ((DetailBoatActivity)getActivity()).getArrayListVideos());
//            gridListFragment2.setArguments(arg);
//            getFragmentManager().beginTransaction().replace(R.id.boats_grid_frame,gridListFragment2,"videoFragmentGridList").commit();
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

    private void setupSpinner() {
        String idBoat = ((DetailBoatActivity) getActivity()).getIdBoat();
        ArrayList<Boat> boatArrayList = MainActivity.boats;
        for (Boat tempBoat : boatArrayList) {
            if (tempBoat.getId().equals(idBoat)) {
                mCharacteristicsArrayList = tempBoat.getCharacteristicsArrayList();
                break;
            }
        }
        ArrayList<String> arrayLang = new ArrayList<>();
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter();
        if (mCharacteristicsArrayList != null) {
            for (BoatsCharacteristics boatsCharacteristics : mCharacteristicsArrayList) {
                arrayLang.add(boatsCharacteristics.getName());
            }

            spinnerAdapter.addItems(arrayLang);

            Drawable spinnerDrawable = DetailBoatActivity.spinner.getBackground().getConstantState().newDrawable();
            spinnerDrawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            DetailBoatActivity.spinner.setBackground(spinnerDrawable);
            DetailBoatActivity.spinner.setAdapter(spinnerAdapter);
            int currentIndex = ((DetailBoatActivity) getActivity()).getmIndexCurrentLanguage();
            DetailBoatActivity.spinner.setSelection(currentIndex);
            DetailBoatActivity.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    ((DetailBoatActivity) getActivity()).setmIndexCurrentLanguage(position);
                    ((DetailBoatActivity) getActivity()).changeButtonText(
                            mCharacteristicsArrayList.get(position).getCharacteristics(),
                            mCharacteristicsArrayList.get(position).getPictures(),
                            mCharacteristicsArrayList.get(position).getVideos()
                    );
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
            DetailBoatActivity.spinner.setVisibility(View.VISIBLE);
            ((DetailBoatActivity) getActivity()).changeButtonText(
                    mCharacteristicsArrayList.get(currentIndex).getCharacteristics(),
                    mCharacteristicsArrayList.get(currentIndex).getPictures(),
                    mCharacteristicsArrayList.get(currentIndex).getVideos()
            );
        }
    }

}
