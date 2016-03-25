package com.parks.albertan.albertssfparks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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


    public LoadParks(Activity activity) {
        this.rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
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

            ParkListAdapter proAdapter = new ParkListAdapter(activity, generateData(jsonArray));

            final ListView listView = (ListView) rootView.findViewById(R.id.park_list_view);

            listView.setAdapter(proAdapter);

             /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                    int cock = view.getId();
                    String  otherguyname = view.getTag(position).toString();

                    Intent i = new Intent(activity,
                            MessageInside.class);

                    i.putExtra("other_guy_index", "" + );
                    //i.putExtra("otherguyname", otherguyname);

                    i.putExtra("prevActivity", "MainActivity");
                    activity.startActivity(i);

                }
            });*/

            //  progressBar.setVisibility(View.GONE);

        } else {

            Log.d("emptyarray", "sptmey man");
        }


    }// end post ex


    ArrayList<Parks_model> generateData(JSONArray jsondata) {

        ArrayList<Parks_model> items = new ArrayList<Parks_model>();

        for (int i = 0; i < jsondata.length(); i++) {


            try {
                //check if course comment
                //if(jsondata.getJSONObject(i).getString("courses_index")!="") {

                items.add(new Parks_model(
                        jsondata.getJSONObject(i).getString("parktype"),
                        jsondata.getJSONObject(i).getString("parkname"),
                        jsondata.getJSONObject(i).getString("email"),
                        jsondata.getJSONObject(i).getString("zipcode"),
                        jsondata.getJSONObject(i).getString("parkid"),
                        jsondata.getJSONObject(i).getString("number"),
                        jsondata.getJSONObject(i).getString("parkservicearea"),
                        jsondata.getJSONObject(i).getJSONObject("location_1").getString("needs_recoding"),
                        jsondata.getJSONObject(i).getJSONObject("location_1").getString("longitude"),
                        jsondata.getJSONObject(i).getJSONObject("location_1").getString("latitude"),
                        jsondata.getJSONObject(i).getJSONObject("location_1").getString("human_address"),
                        jsondata.getJSONObject(i).getString("acreage"),
                        jsondata.getJSONObject(i).getString("psamanager")



                ));

                // }//end if
            } catch (JSONException e) {

                e.printStackTrace();

            }

        }

        return items;

    }// end generate



}