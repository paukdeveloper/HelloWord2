package com.datsenko.yevhenii.boats.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.datsenko.yevhenii.boats.R;
import com.datsenko.yevhenii.boats.activity.MainActivity;
import com.datsenko.yevhenii.boats.models.Boat;

import java.util.ArrayList;

/**
 * Created by Женя on 01.07.2016.
 */
public class BoatDetailFragment extends Fragment {
    private TextView detail;
    private Button characters;
    private Button images;
    private Button video;

    private ArrayList<Boat> boatArrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.boat_detail_layout,container,false);
        detail = (TextView) v.findViewById(R.id.boats_detail_detail);
        characters = (Button) v.findViewById(R.id.boats_detail_characters);
        images = (Button) v.findViewById(R.id.boats_detail_images);
        video = (Button) v.findViewById(R.id.boats_detail_videos);
        boatArrayList = MainActivity.boats;

        String id = getArguments().getString("id");
        Boat boat = null;
        for (Boat tempBoat : boatArrayList) {
            if (tempBoat.getId().equals(id)) {
                boat = tempBoat;
                break;
            }
        }
        detail.setText(boat.toString());
        return v;
    }
}
