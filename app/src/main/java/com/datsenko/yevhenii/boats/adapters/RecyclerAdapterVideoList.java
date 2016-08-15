package com.datsenko.yevhenii.boats.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.datsenko.yevhenii.boats.R;
import com.datsenko.yevhenii.boats.fragments.VideoFragment;
import com.datsenko.yevhenii.boats.models.VideoEntry;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yevhenii on 5/18/2016.
 */
public class RecyclerAdapterVideoList extends RecyclerView.Adapter<RecyclerAdapterVideoList.VideoListViewHolder> {
    private List<VideoEntry> videoEntryList;
    private Activity activity;

    public RecyclerAdapterVideoList(List<VideoEntry> videoEntryList, Activity activity) {
        this.videoEntryList = new ArrayList<>(videoEntryList);
        this.activity = activity;
    }

    @Override
    public VideoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_grid_list_item, parent, false);
        final VideoListViewHolder videoListViewHolder = new VideoListViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoFragment videoFragment =
                        (VideoFragment) activity.getFragmentManager().findFragmentById(R.id.boat_detail_frame);
                videoFragment.setVideoId(videoEntryList.get(videoListViewHolder.getAdapterPosition()).getVideoId(), true);
            }
        });
        return videoListViewHolder;
    }

    @Override
    public void onBindViewHolder(VideoListViewHolder holder, int position) {
        Picasso.with(holder.image.getContext())
//                .load(getURL(videoEntryList.get(position).getVideoId()))
                .load("http://img.youtube.com/vi/"+videoEntryList.get(position).getVideoId()+"/default.jpg")
                .placeholder(R.drawable.loading_thumbnail)
                .error(R.drawable.no_thumbnail)
                .into(holder.image);
        holder.title.setText(videoEntryList.get(position).getText());
    }

//    private String getURL(String videoId) {
//        Uri.Builder builder = new Uri.Builder();
//        builder.scheme("http")
//                .appendPath("img.youtube.com")
//                .appendPath("vi")
//                .appendEncodedPath(videoId)
//                .appendPath("default.jpg");
//        return builder.build().toString();
//    }

    @Override
    public int getItemCount() {
        return videoEntryList.size();
    }

    public static class VideoListViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;

        public VideoListViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.video_grid_list_title);
            image = (ImageView) itemView.findViewById(R.id.video_grid_list_image);

        }
    }
}
