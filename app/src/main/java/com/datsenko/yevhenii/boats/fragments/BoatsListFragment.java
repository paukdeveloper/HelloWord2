package com.datsenko.yevhenii.boats.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datsenko.yevhenii.boats.R;
import com.datsenko.yevhenii.boats.activity.MainActivity;
import com.datsenko.yevhenii.boats.adapters.BoatsListAdapter;
import com.datsenko.yevhenii.boats.models.Boat;
import com.datsenko.yevhenii.boats.models.BoatsCharacteristics;
import com.datsenko.yevhenii.boats.models.FieldCharacteristics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Женя on 29.06.2016.
 */
public class BoatsListFragment extends Fragment {

    private static final String URL_BOATS = "https://umsboats.firebaseio.com/boats.json";
    private static final String JSON_NAME_BOAT = "name";
    private static final String JSON_IMAGE_BOAT = "videos";
    private static final String JSON_VIDEO_BOAT = "images";
    private static final String JSON_LANGUAGES_BOAT = "languages";
    private static final String JSON_LANGUAGE_ABBREVIATION_BOAT = "abbreviation";
    private static final String JSON_LANGUAGE_FIELDS_BOAT = "fields";
    private static final String JSON_LANGUAGE_NAME_BOAT = "name";
    private static final String JSON_FIELD_LABEL_BOAT = "fieldLabel";
    private static final String JSON_FIELD_VALUE_BOAT = "fieldValue";


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private BoatsListAdapter mBoatsListAdapter;

    private ArrayList<Boat> mBoatArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.boats_list_layout, container, false);
        initData();
        mRecyclerView = (RecyclerView) root.findViewById(R.id.boats_list_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mBoatsListAdapter = new BoatsListAdapter(mBoatArrayList,getActivity());
        mRecyclerView.setAdapter(mBoatsListAdapter);

        return root;
    }

    private void initData() {


        if(mBoatArrayList.isEmpty())
            new DownloadJSONTask().execute(URL_BOATS);
    }

    public ArrayList<FieldCharacteristics> getArrayListFields(Object object) throws JSONException {
        if (object instanceof JSONObject) {
            JSONObject jo = (JSONObject) object;
            ArrayList<String> keys = getAllKeys(jo);
            ArrayList<FieldCharacteristics> characteristicsArrayList = new ArrayList<>();
            for (String key : keys) {
                JSONObject tempField = jo.getJSONObject(key);
                FieldCharacteristics fieldCharacteristics =
                        new FieldCharacteristics(tempField.getString(JSON_FIELD_LABEL_BOAT), tempField.getString(JSON_FIELD_VALUE_BOAT));
                characteristicsArrayList.add(fieldCharacteristics);
            }
            return characteristicsArrayList;
        } else {
            JSONArray ja = (JSONArray) object;
            ArrayList<FieldCharacteristics> characteristicsArrayList = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject tempField = ja.getJSONObject(i);
                FieldCharacteristics fieldCharacteristics =
                        new FieldCharacteristics(tempField.getString(JSON_FIELD_LABEL_BOAT), tempField.getString(JSON_FIELD_VALUE_BOAT));
                characteristicsArrayList.add(fieldCharacteristics);

            }
            return characteristicsArrayList;
        }
    }

    private ArrayList<Boat> getBoatArrayList(String json) {
        ArrayList<Boat> boatArrayListInner = null;
        try {
            JSONObject root = new JSONObject(json);
            List<String> boatsIDs = getAllKeys(root);
            Log.d("LOG_TAG",boatsIDs.toString());
            boatArrayListInner = new ArrayList<>();
            for (String boatID : boatsIDs) {
                JSONObject boatJsonObject = root.getJSONObject(boatID);
                Boat boat = new Boat(boatID,boatJsonObject.getString(JSON_NAME_BOAT));
                ArrayList<String> tempArrayKeys = new ArrayList<>();
                ArrayList<String> tempArrayValue = new ArrayList<>();
                //get array images
                JSONObject tempJsonObject = boatJsonObject.getJSONObject(JSON_IMAGE_BOAT);
                tempArrayKeys = getAllKeys(tempJsonObject);
                for (String key : tempArrayKeys) {
                    tempArrayValue.add(tempJsonObject.getString(key));
                }
                boat.setArrayImages(tempArrayValue);
                tempArrayKeys.clear();
                tempArrayValue.clear();
                //get array videos
                tempJsonObject = boatJsonObject.getJSONObject(JSON_VIDEO_BOAT);
                tempArrayKeys = getAllKeys(tempJsonObject);
                for (String key : tempArrayKeys) {
                    tempArrayValue.add(tempJsonObject.getString(key));
                }
                boat.setArrayVideos(tempArrayValue);

                //get array languages
//
                JSONArray arrayLanguages = boatJsonObject.getJSONArray(JSON_LANGUAGES_BOAT);
                for (int i = 0; i < arrayLanguages.length(); i++) {
                    JSONObject languageJSON = arrayLanguages.getJSONObject(i);
                    BoatsCharacteristics boatsCharacteristics =
                            new BoatsCharacteristics(languageJSON.getString(JSON_LANGUAGE_ABBREVIATION_BOAT),languageJSON.getString(JSON_LANGUAGE_NAME_BOAT));
                    Object object = languageJSON.get(JSON_LANGUAGE_FIELDS_BOAT);
                    boatsCharacteristics.setCharacteristicsArrayList(getArrayListFields(object));
                    boat.addCharacteristicsArrayList(boatsCharacteristics);
//
                }
                boatArrayListInner.add(boat);
            }
//                Log.d("LOG_TAG","Mine structure" + boatArrayListInner.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return boatArrayListInner;
    }

    private ArrayList<String> getAllKeys (JSONObject jsonObject) {
        Iterator iterator = jsonObject.keys();
        ArrayList<String> keys = new ArrayList<String>();
        while(iterator.hasNext()) {
            String key = (String) iterator.next();
            keys.add(key);
        }
        Log.d("LOG_TAG", keys.toString());
        return keys;
    }

    class DownloadJSONTask extends AsyncTask<String, Void, ArrayList<Boat>> {
        @Override
        protected ArrayList<Boat> doInBackground(String[] strings) {
            String content;
            ArrayList<Boat> boatArrayListInner = null;
            try{
                content = getContent(strings[0]);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Log.d("LOG_TAG", "saveJSONToSharedPref: " + content);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(MainActivity.SHARED_PREF_KEY_JSON, content);
                editor.apply();
                boatArrayListInner = getBoatArrayList(content);
                Log.d("LOG_TAG","Mine structure" + boatArrayListInner.toString());
//            Log.d("LOG_TAG", root.toString());
                Log.d("LOG_TAG", content);
            }
            catch (IOException ex){
                content = ex.getMessage();
            }

            return boatArrayListInner;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Boat> boatArrayList) {
//        super.onPostExecute(s);
//            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
//            Log.d("LOG_TAG", s);
            mBoatArrayList.clear();
            mBoatArrayList.addAll(boatArrayList);
            mBoatsListAdapter.notifyDataSetChanged();
            MainActivity.boats.clear();
            MainActivity.boats.addAll(mBoatArrayList);

        }
        private String getContent(String path) throws IOException {
            BufferedReader reader=null;
            try {
                URL url=new URL(path);
                HttpURLConnection c=(HttpURLConnection)url.openConnection();
                c.setRequestMethod("GET");
                c.setReadTimeout(10000);
                c.connect();
                reader= new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder buf=new StringBuilder();
                String line=null;
                while ((line=reader.readLine()) != null) {
                    buf.append(line);
                }
                return(buf.toString());
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
    }
}
