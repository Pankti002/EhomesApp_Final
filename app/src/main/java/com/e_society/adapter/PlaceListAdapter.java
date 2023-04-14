package com.e_society.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.e_society.R;
import com.e_society.model.PlaceLangModel;
import com.e_society.update.PlaceUpdateActivity;

import java.util.ArrayList;

public class PlaceListAdapter extends BaseAdapter {
    Context context;
    ArrayList<PlaceLangModel> placeLangModelArrayList;


    public PlaceListAdapter(Context context, ArrayList<PlaceLangModel> langModelArrayList) {
        this.context = context;
        this.placeLangModelArrayList = langModelArrayList;
    }

    @Override
    public int getCount() {
        return placeLangModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return placeLangModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.place_table, null);

        TextView tvPlaceName = view.findViewById(R.id.tv_placeName);
        TextView tvPlaceRent=view.findViewById(R.id.tv_Rent);

        tvPlaceName.setText(placeLangModelArrayList.get(position).getPlaceName());
        tvPlaceRent.setText(placeLangModelArrayList.get(position).getRent());

        ImageView imgEdit = view.findViewById(R.id.img_edit);
        ImageView imgDelete = view.findViewById(R.id.img_delete);


        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = placeLangModelArrayList.get(position).get_id();
                Log.e("id in edit: ", id);

                Intent intent = new Intent(context, PlaceUpdateActivity.class);
                intent.putExtra("PLACE_ID", id);
                intent.putExtra("PLACE_NAME", placeLangModelArrayList.get(position).getPlaceName());
                intent.putExtra("RENT", placeLangModelArrayList.get(position).getRent());


                context.startActivity(intent);
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id1 = placeLangModelArrayList.get(position).get_id();
                Log.e("id in delete: ", id1);

                Intent intent = new Intent(context, PlaceUpdateActivity.class);
                intent.putExtra("PLACE_ID", id1);
                intent.putExtra("PLACE_NAME", placeLangModelArrayList.get(position).getPlaceName());
                intent.putExtra("RENT", placeLangModelArrayList.get(position).getRent());

                context.startActivity(intent);
            }
        });

        return view;
    }
}