package com.datsenko.yevhenii.boats.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

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

    private void addMargintoSpinner() {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
//        ((MainActivity)getActivity())
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins(getPixelValue(getActivity(),(int)dpWidth/2), 0, 0, 0);
        layoutParams.setMargins((int)dpWidth/2, 0, 0, 0);
        MainActivity.spinner.setLayoutParams(layoutParams);

        Toast.makeText(getActivity(), "sda " + MainActivity.spinner.getMeasuredWidth(), Toast.LENGTH_SHORT).show();
    }


    public static int getPixelValue(Context context, int dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }

    private void setupSpinner() {
        ArrayList<String> arrayLang = new ArrayList<>();
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter();

        for (BoatsCharacteristics boatsCharacteristics : mCharacteristicsArrayList) {
            arrayLang.add(boatsCharacteristics.getName());
        }

        spinnerAdapter.addItems(arrayLang);
//

        Drawable spinnerDrawable = MainActivity.spinner.getBackground().getConstantState().newDrawable();
        spinnerDrawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        MainActivity.spinner.setBackground(spinnerDrawable);
        MainActivity.spinner.setAdapter(spinnerAdapter);
        MainActivity.spinner.setSelection(0);
        MainActivity.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
//        addMargintoSpinner();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        addMargintoSpinner();
        MainActivity.spinner.setVisibility(View.VISIBLE);
//        addMargintoSpinner();
    }
}
