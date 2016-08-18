package com.datsenko.yevhenii.boats.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.datsenko.yevhenii.boats.R;
import com.datsenko.yevhenii.boats.fragments.CharactersFragmentList;
import com.datsenko.yevhenii.boats.fragments.VideoFragment;
import com.datsenko.yevhenii.boats.fragments.VideoGridListFragment;
import com.datsenko.yevhenii.boats.models.Boat;
import com.datsenko.yevhenii.boats.models.VideoEntry;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Женя on 15.08.2016.
 */
public class DetailBoatActivity extends YouTubeBaseActivity {
    public static final String SHARED_PREF_SAVED_JSON = "json_main";
    public static final String SHARED_PREF_KEY_JSON = "key";
    private static final int TIME_DELAY = 1000;
    private static long back_pressed;

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private boolean isFullscreen;

    public static List<VideoEntry> video_list;

    public static String language;

    //    private Toolbar toolbar;
    public static Spinner spinner;
    public static TextView title;
    private FrameLayout mAdditionFrame;
    private RelativeLayout mToolbarLayout;
    private LinearLayout mButtonsLayout;
    private Button characters;
    private Button images;
    private Button video;

    private String idBoat;
//    private static boolean isFirstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boat_detail_activity);
        mToolbarLayout = (RelativeLayout) findViewById(R.id.main_toolbar_2);
        mButtonsLayout = (LinearLayout) findViewById(R.id.linear_buttom);
        mAdditionFrame = (FrameLayout) findViewById(R.id.boats_grid_frame);
        spinner = (Spinner) findViewById(R.id.toolbar_spinner_2);
        title = (TextView) findViewById(R.id.toolbar_title_2);
        characters = (Button) findViewById(R.id.boats_detail_characters);
        images = (Button) findViewById(R.id.boats_detail_images);
        video = (Button) findViewById(R.id.boats_detail_videos);

        idBoat = getIntent().getStringExtra("id");
        if (getIntent().getStringExtra("name") != null)
            DetailBoatActivity.title.setText(getIntent().getStringExtra("name"));

//        initData();
        boolean isShowCharacter = getIntent().getBooleanExtra("bool",true);

        if (isShowCharacter) {
            mAdditionFrame.setVisibility(View.GONE);
            CharactersFragmentList fragmentListInner = new CharactersFragmentList();
            startFragment(fragmentListInner);
            getIntent().putExtra("bool",false);
//            isFirstStart = false;
        }

        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdditionFrame.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
            }
        });

        characters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdditionFrame.setVisibility(View.GONE);
                CharactersFragmentList fragmentListInner = new CharactersFragmentList();
                startFragment(fragmentListInner);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> arrayList = prepareArrayStringTpPlayer(getUrlArrayByID());
                spinner.setVisibility(View.GONE);
                VideoFragment videoFragment = new VideoFragment();
                Bundle arg2 = new Bundle();
                arg2.putString("id", "0");
                videoFragment.setArguments(arg2);
                FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                transaction2 = transaction2.replace(R.id.boat_detail_frame, videoFragment, "videoFragment");
                transaction2.commit();
                videoFragment.setVideoId(arrayList.get(0), false);

                VideoGridListFragment videoGridListFragment = new VideoGridListFragment();
                Bundle arg = new Bundle();
                arg.putStringArrayList("videos", arrayList);
                videoGridListFragment.setArguments(arg);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction = transaction.replace(R.id.boats_grid_frame, videoGridListFragment, "videoFragmentGridList");
                transaction.commit();

                mAdditionFrame.setVisibility(View.VISIBLE);
            }
        });

        checkYouTubeApi();
    }

    private ArrayList<String> getUrlArrayByID() {
        ArrayList<Boat> boatArrayList = MainActivity.boats;
        for (Boat tempBoat : boatArrayList) {
            if (tempBoat.getId().equals(idBoat)) {
                return tempBoat.getArrayVideos();
            }
        }
        return new ArrayList<>();
    }

    private ArrayList<String> prepareArrayStringTpPlayer(ArrayList<String> arrayList) {
        ArrayList<String> result = new ArrayList<>();
        for (String str : arrayList) {
            int i = str.lastIndexOf("v=");
            if (i > 0) {
                result.add(str.substring(i + 2));
                Log.d("LOG_TAG", str.substring(i + 2));
            }
        }
        return result;
    }

    private void startFragment(Fragment fragment) {
        Bundle arg = new Bundle();
        arg.putString("id", idBoat);
        fragment.setArguments(arg);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction = transaction.replace(R.id.boat_detail_frame, fragment, "characters");
        transaction.commit();
    }
//    private void initData() {
//        video_list = new ArrayList<>();
//        video_list.add(new VideoEntry("YouTube Collection", "Y_UmWdcTrrc"));
//        video_list.add(new VideoEntry("GMail Tap", "1KhZKNZO8mQ"));
//        video_list.add(new VideoEntry("Chrome Multitask", "UiLSiqyDf4Y"));
//        video_list.add(new VideoEntry("Google Fiber", "re0VRK6ouwI"));
//        video_list.add(new VideoEntry("Autocompleter", "blB_X38YSxQ"));
//        video_list.add(new VideoEntry("GMail Motion", "Bu927_ul_X0"));
//        video_list.add(new VideoEntry("Translate for Animals", "3I24bSteJpw"));
//    }

    private void checkYouTubeApi() {
        YouTubeInitializationResult errorReason =
                YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else if (errorReason != YouTubeInitializationResult.SUCCESS) {
            String errorMessage =
                    String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
//        isFirstStart = true;
        super.onDestroy();
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == RECOVERY_DIALOG_REQUEST) {
//            // Recreate the activity if user performed a recovery action
//            recreate();
//        }
//    }


    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
//            Toast.makeText(getBaseContext(), "Press once again to exit!",
//                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    public void changeButtonText(String charact, String photo, String videoName) {
        characters.setText(charact);
        images.setText(photo);
        video.setText(videoName);
    }
}
