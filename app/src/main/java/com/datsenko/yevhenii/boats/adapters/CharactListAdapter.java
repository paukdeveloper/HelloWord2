package com.datsenko.yevhenii.boats.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datsenko.yevhenii.boats.R;
import com.datsenko.yevhenii.boats.models.FieldCharacteristics;

import java.util.ArrayList;

/**
 * Created by Женя on 16.07.2016.
 */
public class CharactListAdapter extends RecyclerView.Adapter<CharactListAdapter.CharactListViewHolder> {
    private ArrayList<FieldCharacteristics> arrayCharacters;
    private Activity activity;

    public CharactListAdapter(ArrayList<FieldCharacteristics> arrayCharacters, Activity activity) {
        this.arrayCharacters = arrayCharacters;
        this.activity = activity;
    }

    @Override
    public CharactListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.characters_item_rv, parent, false);
        CharactListViewHolder vh = new CharactListViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CharactListViewHolder holder, int position) {
        holder.charactName.setText(arrayCharacters.get(position).getFieldLabel());
        holder.charactValue.setText(arrayCharacters.get(position).getFieldValue());
    }

    @Override
    public int getItemCount() {
        if (arrayCharacters == null) {
            return 0;
        } else {
            return arrayCharacters.size();
        }
    }

    public class CharactListViewHolder extends RecyclerView.ViewHolder {
        private TextView charactName;
        private TextView charactValue;

        public CharactListViewHolder(View itemView) {
            super(itemView);
            charactName = (TextView) itemView.findViewById(R.id.characters_boat_name);
            charactValue = (TextView) itemView.findViewById(R.id.characters_boat_value);
        }
    }
}
