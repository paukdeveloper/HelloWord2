package com.datsenko.yevhenii.boats.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datsenko.yevhenii.boats.R;
import com.datsenko.yevhenii.boats.activity.DetailBoatActivity;
import com.datsenko.yevhenii.boats.models.Boat;

import java.util.ArrayList;

/**
 * Created by Женя on 29.06.2016.
 */
public class BoatsListAdapter extends RecyclerView.Adapter<BoatsListAdapter.BoatsViewHolder> {
    private ArrayList<Boat> arrayBoats;
    private Activity activity;

    public BoatsListAdapter(ArrayList<Boat> arrayBoats, Activity activity) {
        this.arrayBoats = arrayBoats;
        this.activity = activity;
    }

    @Override
    public BoatsListAdapter.BoatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.boats_list_item_layout,parent,false);
        final BoatsViewHolder vh = new BoatsViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(activity, arrayBoats.get(vh.getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
//                FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
                Intent intent = new Intent(activity, DetailBoatActivity.class);

//                BoatDetailFragment boatDetailFragment = new BoatDetailFragment();
//                Bundle bundle = new Bundle();
                intent.putExtra("id",arrayBoats.get(vh.getAdapterPosition()).getId());
                intent.putExtra("name",arrayBoats.get(vh.getAdapterPosition()).getName());
                intent.putExtra("bool",true);
                activity.startActivity(intent);
//                transaction = transaction.replace(R.id.main_frame,boatDetailFragment);
//                transaction.commit();
//                ((MainActivity)activity).showFragment(boatDetailFragment,"detail",false);
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(BoatsListAdapter.BoatsViewHolder holder, int position) {
        holder.boatName.setText(arrayBoats.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrayBoats.size();
    }

    public class BoatsViewHolder extends RecyclerView.ViewHolder {
        private TextView boatName;
        public BoatsViewHolder(View itemView) {
            super(itemView);
            boatName = (TextView)itemView.findViewById(R.id.boats_list_name);
        }
    }
}
