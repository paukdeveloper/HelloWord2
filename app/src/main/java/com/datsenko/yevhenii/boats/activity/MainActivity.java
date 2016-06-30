package com.datsenko.yevhenii.boats.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.datsenko.yevhenii.boats.R;
import com.datsenko.yevhenii.boats.fragments.BoatsListFragment;
import com.datsenko.yevhenii.boats.models.Boat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREF_SAVED_JSON = "json_main";
    public static final String SHARED_PREF_KEY_JSON = "key";
    public static ArrayList<Boat> boats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        BoatsListFragment boatsListFragment = new BoatsListFragment();
        transaction = transaction.replace(R.id.main_frame,boatsListFragment);
        transaction.commit();
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
}
