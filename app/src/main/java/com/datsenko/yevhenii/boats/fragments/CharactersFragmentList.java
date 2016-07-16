package com.datsenko.yevhenii.boats.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.datsenko.yevhenii.boats.R;
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
        mCharactListAdapter = new CharactListAdapter(mCharacteristicsArrayList.get(0).getCharacteristicsArrayList(), getActivity());
        mRecyclerView.setAdapter(mCharactListAdapter);

//        setupSpinner();
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
//        ActionBar ab = getActivity().get();
        ActionBar ab = ((MainActivity) getActivity()).getSupportActionBar();
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.action_bar_spinner, null);
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter();
        for (BoatsCharacteristics boatsCharacteristics : mCharacteristicsArrayList) {
            arrayLang.add(boatsCharacteristics.getName());
        }

        spinnerAdapter.addItems(arrayLang);
//
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner_main);
        Drawable spinnerDrawable = spinner.getBackground().getConstantState().newDrawable();
        spinnerDrawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        spinner.setBackground(spinnerDrawable);
        spinner.setAdapter(spinnerAdapter);
//        if (getIntent().getStringExtra(TYPE_NEWS) != null) {
//            Toast.makeText(NewsActivity.this, getIntent().getStringExtra(TYPE_NEWS), Toast.LENGTH_SHORT).show();
////            for (int i = 0; i < filteredTitles.size(); i++) {
////                if (filteredTitles.get(i).contains(getIntent().getStringExtra(TYPE_NEWS))) {
////                    typeNews = i+1;
////                } else typeNews = 0;
////
////            }
//            ArrayList<TVNew> filteredNewsList = new ArrayList<>();
//            String lastType = getIntent().getStringExtra(TYPE_NEWS);
//            Log.d("Tag_titlr_id", lastType);// + " size news" + news.size());
////            for (TVNew newsItem : news) {
////                Log.d("Tag_titlr_id",lastType + " " + newsItem.getTitle());
////                if (lastType.contains(newsItem.getTitle())) {
////                    filteredNewsList.add(newsItem);
////                }
////            }
////            setNewsToList(filteredNewsList);
//
//        }
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mCharactListAdapter = new CharactListAdapter(mCharacteristicsArrayList.get(position).getCharacteristicsArrayList(), getActivity());
                mRecyclerView.setAdapter(mCharactListAdapter);
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
