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
import java.util.Arrays;
import java.util.Collections;

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

    double mylat, mylongitude;


    public LoadParks(Activity activity, double lat, double longitude) {
        this.rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        this.activity = activity;

        //my lat long
        this.mylat = lat;
        this.mylongitude = longitude;


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

            ArrayList<Parks_model> parkArraylist = generateData(jsonArray);

            int low = 0;
            int high = parkArraylist.size()- 1;

            quickSort(parkArraylist, low, high);

                    ParkListAdapter proAdapter = new ParkListAdapter(activity, generateData(jsonArray));
            final ListView listView = (ListView) rootView.findViewById(R.id.park_list_view);
            listView.setAdapter(proAdapter);


            //quickSort(jsondata, low, high);

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


        System.out.println(jsondata);



        ArrayList<Parks_model> items = new ArrayList<Parks_model>();

        for (int i = 0; i < jsondata.length(); i++) {


            try {
                //check if course comment
                //if(jsondata.getJSONObject(i).getString("courses_index")!="") {


                //get distance for each

                 double thedistance = getthedistance(mylat, mylongitude,
                         Double.parseDouble(jsondata.getJSONObject(i).getJSONObject("location_1").getString("latitude")),
                         Double.parseDouble(jsondata.getJSONObject(i).getJSONObject("location_1").getString("longitude"))
                 );

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
                        jsondata.getJSONObject(i).getString("psamanager"),
                        thedistance




                ));

                // }//end if
            } catch (JSONException e) {

                e.printStackTrace();

            }

        }//for

        return items;

    }// end generate




    public static void quickSort(ArrayList<Parks_model> arr, int low, int high) {


        if (arr == null || arr.size() == 0)
            return;

        if (low >= high)
            return;

        // pick the pivot
        int middle = low + (high - low) / 2;

       Double  pivotDist =  arr.get(middle).getdistance();

        // make left < pivot and right > pivot
        int i = low, j = high;
        while (i <= j) {
            while (arr.get(i).getdistance() < pivotDist ) {
                i++;
            }

            while (arr.get(j).getdistance() > pivotDist) {
                j--;
            }

            if (i <= j) {

//                Collections.swap(List<Parks_model> arr, int i, int j);

                com.parks.albertan.albertssfparks.Parks_model temp = arr.get(i);
                arr.set(i, arr.get(j));

                arr.set(j, temp);

              /*  Object temp = arr.get(i);
                arr.get(i) = arr.get(j);
                arr.get(j) = temp;*/
                i++;
                j--;
            }
        }

        // recursively sort two sub parts
        if (low < j)
            quickSort(arr, low, j);

        if (high > i)
            quickSort(arr, i, high);



    }//quick



    //get distance

    public double getthedistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

            dist = dist * 1.609344;

        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    public static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}//end class