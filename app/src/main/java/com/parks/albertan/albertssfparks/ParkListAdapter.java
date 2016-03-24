package com.parks.albertan.albertssfparks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.parks.albertan.albertssfparks.Parks_model;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by albertan on 3/24/16.
 */
public class ParkListAdapter extends ArrayAdapter<Parks_model> {


    static int rowList = R.layout.messagetabadapter_____message_frag_list_row;

    private final Context context;

    private final ArrayList<Parks_model> itemsArrayList;

    public ParkListAdapter(Context context, ArrayList<Parks_model> itemsArrayList) {


//ArrayList<HomeItem> itemsArrayList


        super(context, rowList, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(rowList, parent, false);

        // 3. Get the two text view from the rowView
        TextView username_view = (TextView) rowView.findViewById(R.id.username);


        //4. set the text

        username_view.setText(itemsArrayList.get(position).getusername());

        // 5. retrn rowView
        Integer memindex = Integer.parseInt(itemsArrayList.get(position).getuserIndex().toString());
        rowView.setId(memindex);

        //set the pro pic

        ImageView profile_photo = (ImageView) rowView.findViewById(R.id.profile_pic);

        //new LoadProfileImage(profile_photo).execute(itemsArrayList.get(position).getProfilePic().toString());

        String imageurl ="http://tanggoal.com/public/uploads/members_pic/"+ itemsArrayList.get(position).getProfilePic().toString();


        ImageLoader imageloader = ImageLoader.getInstance();

        //imageloader.displayImage(url, view);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .displayer(new RoundedBitmapDisplayer(50))

                .cacheInMemory(true)
                .considerExifParams(true)
                .build();

        imageloader.displayImage(imageurl, profile_photo, options);



        return rowView;
    }


}//park list adapter end
