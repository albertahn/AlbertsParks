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

        username_view.setText(itemsArrayList.get(position).getparkname());

        // 5. retrn rowView
        Integer memindex = Integer.parseInt(itemsArrayList.get(position).getparkid());
        rowView.setId(memindex);


        return rowView;
    }


}//park list adapter end
