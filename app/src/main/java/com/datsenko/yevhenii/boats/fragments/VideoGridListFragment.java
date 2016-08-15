package com.datsenko.yevhenii.boats.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datsenko.yevhenii.boats.R;
import com.datsenko.yevhenii.boats.adapters.RecyclerAdapterVideoList;
import com.datsenko.yevhenii.boats.models.VideoEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yevhenii on 5/18/2016.
 */
public class VideoGridListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapterVideoList adapterVideoList;
    private RecyclerView.LayoutManager layoutManager;
//    public static List<VideoEntry> video_list;
    public static List<VideoEntry> video_list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_recycler_video_list,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_video);
        layoutManager = new GridLayoutManager(getActivity(),4);
        recyclerView.setLayoutManager(layoutManager);
        initData();
        adapterVideoList = new RecyclerAdapterVideoList(video_list,getActivity());
        recyclerView.setAdapter(adapterVideoList);
        

        return v;
    }

    private void initData() {
        video_list = new ArrayList<>();
        if (getArguments().getStringArrayList("videos") != null) {
            for (String str : getArguments().getStringArrayList("videos")) {
                video_list.add(new VideoEntry(str, str));
            }
        }
//        video_list.add(new VideoEntry("YouTube Collection", "Y_UmWdcTrrc"));
//        video_list.add(new VideoEntry("GMail Tap", "1KhZKNZO8mQ"));
//        video_list.add(new VideoEntry("Chrome Multitask", "UiLSiqyDf4Y"));
//        video_list.add(new VideoEntry("Google Fiber", "re0VRK6ouwI"));
//        video_list.add(new VideoEntry("Autocompleter", "blB_X38YSxQ"));
//        video_list.add(new VideoEntry("GMail Motion", "Bu927_ul_X0"));
//        video_list.add(new VideoEntry("Translate for Animals", "3I24bSteJpw"));
    }
}
