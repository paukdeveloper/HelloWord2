package com.datsenko.yevhenii.boats.fragments;

import android.app.Fragment;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.datsenko.yevhenii.boats.R;
import com.datsenko.yevhenii.boats.activity.DetailBoatActivity;
import com.datsenko.yevhenii.boats.activity.MainActivity;
import com.datsenko.yevhenii.boats.adapters.CharactListAdapter;
import com.datsenko.yevhenii.boats.adapters.MySpinnerAdapter;
import com.datsenko.yevhenii.boats.models.Boat;
import com.datsenko.yevhenii.boats.models.BoatsCharacteristics;

import java.util.ArrayList;

/**
 * Created by Женя on 16.07.2016.
 */
public class CharactersFragmentList extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CharactListAdapter mCharactListAdapter;

    private Boat mBoat;
    private String mIDBoat;
    private ArrayList<BoatsCharacteristics> mCharacteristicsArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.char_list_fragment, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.characters_boat_rv);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        setupBoatAndID();

        mCharacteristicsArrayList = mBoat.getCharacteristicsArrayList();
        int currentIndex = ((DetailBoatActivity)getActivity()).getmIndexCurrentLanguage();
        mCharactListAdapter = new CharactListAdapter(mCharacteristicsArrayList.get(currentIndex).getCharacteristicsArrayList(), getActivity());
        mRecyclerView.setAdapter(mCharactListAdapter);

        setupSpinner();
        return root;
    }

    private void setupBoatAndID() {
        ArrayList<Boat> boatArrayList = MainActivity.boats;
        mIDBoat = getArguments().getString("id");
        for (Boat tempBoat : boatArrayList) {
            if (tempBoat.getId().equals(mIDBoat)) {
                mBoat = tempBoat;
                break;
            }
        }
    }

    private void setupSpinner() {
        ArrayList<String> arrayLang = new ArrayList<>();
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter();

        for (BoatsCharacteristics boatsCharacteristics : mCharacteristicsArrayList) {
            arrayLang.add(boatsCharacteristics.getName());
        }

        spinnerAdapter.addItems(arrayLang);

        Drawable spinnerDrawable = DetailBoatActivity.spinner.getBackground().getConstantState().newDrawable();
        spinnerDrawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        DetailBoatActivity.spinner.setBackground(spinnerDrawable);
        DetailBoatActivity.spinner.setAdapter(spinnerAdapter);
        int currentIndex = ((DetailBoatActivity)getActivity()).getmIndexCurrentLanguage();
        DetailBoatActivity.spinner.setSelection(currentIndex);
        DetailBoatActivity.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mCharactListAdapter = new CharactListAdapter(mCharacteristicsArrayList.get(position).getCharacteristicsArrayList(), getActivity());
                ((DetailBoatActivity)getActivity()).setmIndexCurrentLanguage(position);
                ((DetailBoatActivity)getActivity()).changeButtonText(
                        mCharacteristicsArrayList.get(position).getCharacteristics(),
                        mCharacteristicsArrayList.get(position).getPictures(),
                        mCharacteristicsArrayList.get(position).getVideos()
                );
                mRecyclerView.setAdapter(mCharactListAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
//        DetailBoatActivity.spinner.setVisibility(View.VISIBLE);
        ((DetailBoatActivity)getActivity()).changeButtonText(
                mCharacteristicsArrayList.get(currentIndex).getCharacteristics(),
                mCharacteristicsArrayList.get(currentIndex).getPictures(),
                mCharacteristicsArrayList.get(currentIndex).getVideos()
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        DetailBoatActivity.spinner.setVisibility(View.VISIBLE);
    }
}
