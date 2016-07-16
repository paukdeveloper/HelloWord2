package com.datsenko.yevhenii.boats.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.datsenko.yevhenii.boats.R;
import com.datsenko.yevhenii.boats.activity.MainActivity;
import com.datsenko.yevhenii.boats.adapters.MySpinnerAdapter;
import com.datsenko.yevhenii.boats.models.Boat;
import com.datsenko.yevhenii.boats.models.BoatsCharacteristics;

import java.util.ArrayList;

/**
 * Created by Женя on 01.07.2016.
 */
public class BoatDetailFragment extends Fragment {
    private Button characters;
    private Button images;
    private Button video;

    private String idBoat;
    private Boat boat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.boat_detail_layout, container, false);
        characters = (Button) v.findViewById(R.id.boats_detail_characters);
        images = (Button) v.findViewById(R.id.boats_detail_images);
        video = (Button) v.findViewById(R.id.boats_detail_videos);

        setupBoatAndID();

        characters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharactersFragmentList fragmentList = new CharactersFragmentList();
                Bundle arg = new Bundle();
                arg.putString("id", idBoat);
                fragmentList.setArguments(arg);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction = transaction.replace(R.id.frame_detail, fragmentList, "characters");
                transaction.commit();
            }
        });

//        setupSpinner();
        return v;
    }

    private void setupBoatAndID() {
        ArrayList<Boat> boatArrayList = MainActivity.boats;
        idBoat = getArguments().getString("id");
        for (Boat tempBoat : boatArrayList) {
            if (tempBoat.getId().equals(idBoat)) {
                boat = tempBoat;
                break;
            }
        }
    }

    private void setupSpinner() {
        ArrayList<String> arrayLang = new ArrayList<>();
        ActionBar ab = ((MainActivity) getActivity()).getSupportActionBar();
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.action_bar_spinner, null);
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter();
        for (BoatsCharacteristics boatsCharacteristics : boat.getCharacteristicsArrayList()) {
            arrayLang.add(boatsCharacteristics.getName());
        }

        spinnerAdapter.addItems(arrayLang);
//
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner_main);
//        Drawable spinnerDrawable = spinner.getBackground().getConstantState().newDrawable();
//        spinnerDrawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
//        spinner.setBackground(spinnerDrawable);
        spinner.setAdapter(spinnerAdapter);

        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                mCharactListAdapter = new CharactListAdapter(mCharacteristicsArrayList.get(position).getCharacteristicsArrayList(), getActivity());
//                mRecyclerView.setAdapter(mCharactListAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        v.setVisibility(View.GONE);
        ab.setCustomView(v);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
    }


}
