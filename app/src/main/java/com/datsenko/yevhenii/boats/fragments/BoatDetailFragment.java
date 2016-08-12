package com.datsenko.yevhenii.boats.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.datsenko.yevhenii.boats.R;
import com.datsenko.yevhenii.boats.activity.MainActivity;

/**
 * Created by Женя on 01.07.2016.
 */
public class BoatDetailFragment extends Fragment {
    private Button characters;
    private Button images;
    private Button video;

    private String idBoat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.boat_detail_layout, container, false);
        characters = (Button) v.findViewById(R.id.boats_detail_characters);
        images = (Button) v.findViewById(R.id.boats_detail_images);
        video = (Button) v.findViewById(R.id.boats_detail_videos);

        startCharactersFragment();

        characters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCharactersFragment();
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idBoat = getArguments().getString("id");
        if (getArguments().get("name")!= null)
        MainActivity.title.setText(getArguments().getString("name"));
    }

    private void startCharactersFragment() {
        CharactersFragmentList fragmentList = new CharactersFragmentList();
        Bundle arg = new Bundle();
        arg.putString("id", idBoat);
        fragmentList.setArguments(arg);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction = transaction.replace(R.id.frame_detail, fragmentList, "characters");
        transaction.commit();
    }



}
