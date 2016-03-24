package com.parks.albertan.albertssfparks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.parks.albertan.albertssfparks.utils.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by albert on 10/3/14.
 */
public class LoadParks extends AsyncTask<String, Integer, String> {

    JSONArray jsonArray;

    Activity activity;
    String userID;
    JSONObject jsonOb;
    View rootView;
    // ProgressBar progressBar;


    public LoadParks(View rootView, Activity activity) {
        this.rootView = rootView;
        this.activity = activity;


    }


    //interface to get result
    @Override
    protected String doInBackground(String... params) {
        try {

            final String STREAMURL = "https://data.sfgov.org/resource/z76i-7s65.json";

            jsonArray = JsonReader.readJsonArrayFromUrl(STREAMURL);

            // return jsonArray;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("here", "noJSON");
        }
        return null;
    }//end do in background

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);


        if (jsonArray != null) {
            //homeListView.getContext()

            ParkListAdapter proAdapter = new ParkListAdapter(activity, generateData(jsonArray), userID);

            final ListView listView = (ListView) rootView.findViewById(R.id.battle_list_view);

            listView.setAdapter(proAdapter);

            //  progressBar.setVisibility(View.GONE);

        } else {

            Log.d("emptyarray", "sptmey man");
        }


    }// end post ex


    ArrayList<Battle_partner_item> generateData(JSONArray jsondata) {

        ArrayList<Battle_partner_item> items = new ArrayList<Battle_partner_item>();

        for (int i = 0; i < jsondata.length(); i++) {


            try {
                //check if course comment
                //if(jsondata.getJSONObject(i).getString("courses_index")!="") {

                items.add(new Battle_partner_item(
                        jsondata.getJSONObject(i).getString("friends_key_index"),
                        jsondata.getJSONObject(i).getString("my_index"),
                        jsondata.getJSONObject(i).getString("other_friend_index"),
                        jsondata.getJSONObject(i).getString("status"),
                        jsondata.getJSONObject(i).getString("timestamp"),
                        jsondata.getJSONObject(i).getString("news_type"),
                        jsondata.getJSONObject(i).getString("battle"),
                        jsondata.getJSONObject(i).getString("friend_username"),
                        jsondata.getJSONObject(i).getString("friend_profile_picture"),
                        jsondata.getJSONObject(i).getString("friend_points"),

                        jsondata.getJSONObject(i).getString("friend_score")


                ));

                // }//end if
            } catch (JSONException e) {

                e.printStackTrace();

            }

        }

        return items;

    }// end generate



}