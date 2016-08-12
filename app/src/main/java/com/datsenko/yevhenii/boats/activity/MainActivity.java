package com.datsenko.yevhenii.boats.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.datsenko.yevhenii.boats.R;
import com.datsenko.yevhenii.boats.fragments.BoatDetailFragment;
import com.datsenko.yevhenii.boats.fragments.BoatsListFragment;
import com.datsenko.yevhenii.boats.models.Boat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREF_SAVED_JSON = "json_main";
    public static final String SHARED_PREF_KEY_JSON = "key";

    private static final String TAG_STACK = "tag_for_stack";
    private List<Fragment> stackFragment = new ArrayList<>();
    private FragmentTransaction transaction;
    private static final int TIME_DELAY = 1000;
    private static long back_pressed;
    public static String language;

//    private Toolbar toolbar;
    public static Spinner spinner;
    public static TextView title;

    public static ArrayList<Boat> boats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
//        setSupportActionBar(toolbar);
        spinner = (Spinner) findViewById(R.id.toolbar_spinner);
        spinner.setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.toolbar_title);

        BoatsListFragment boatsListFragment = new BoatsListFragment();
        showFragment(boatsListFragment, "list", false);

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    private static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    private void showSizeStack() {
        Log.d(TAG_STACK, "" + stackFragment.size());
    }

    public void showFragment(Fragment nextFragment, String type, boolean cleanStack) {
        showSizeStack();

        // Start a series of edit operations on the Fragments associated with this FragmentManager.
        transaction = this.getFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left,
//                R.animator.slide_out_right, R.animator.slide_in_right);
        if (type != null) {
            transaction = transaction.replace(R.id.main_frame, nextFragment, type);
        } else {
            transaction = transaction.replace(R.id.main_frame, nextFragment);
        }
        transaction.commit();
        if (cleanStack) {
            clearFragmentStack();
        }
        this.addFragmentToStack(nextFragment);
        showSizeStack();
    }

    public void showPrevFragment() {
        if (stackFragment.size() >= 2) {
//            if (stackFragment.get(stackFragment.size() - 1).getTag() != null && !stackFragment.get(stackFragment.size() - 1).getTag().equals("back")) {
//                toggle.setDrawerIndicatorEnabled(true);
//            } else {
//                toggle.setDrawerIndicatorEnabled(false);
//            }
            showSizeStack();
            int size = stackFragment.size();
            Fragment fragment = stackFragment.get(size - 2);
            stackFragment.remove(size - 1);

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_right,
//                    R.animator.slide_in_left, R.animator.slide_out_left);
            fragmentTransaction.replace(R.id.main_frame, fragment);
            fragmentTransaction.commit();
            showSizeStack();
        } else {
            BoatsListFragment fragment = new BoatsListFragment();
            showFragment(fragment, "list", true);
        }
    }

    /**
     * @param fragment The fragment that will be added to the Back Stack
     */
    public void addFragmentToStack(Fragment fragment) {
        stackFragment.add(fragment);
    }

    public void clearFragmentStack() {
        Log.d(TAG_STACK, "clear");
        showSizeStack();
        stackFragment.clear();
        showSizeStack();
    }

    @Override
    public void onBackPressed() {

        if (stackFragment.get(stackFragment.size() - 1) instanceof BoatsListFragment) {
            super.onBackPressed();
        } else {
            if (stackFragment.get(stackFragment.size() - 1) instanceof BoatDetailFragment) {
                if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
                    showPrevFragment();
                } else {
//                Toast.makeText(getBaseContext(), "Press once again to exit!",
//                        Toast.LENGTH_SHORT).show();
                }
                back_pressed = System.currentTimeMillis();
            } else {
                showPrevFragment();
            }
        }
    }
}
