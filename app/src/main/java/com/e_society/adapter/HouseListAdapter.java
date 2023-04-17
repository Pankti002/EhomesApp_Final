package com.e_society.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.e_society.R;
import com.e_society.model.HouseLangModel;
import com.e_society.update.HouseUpdateActivity;

import java.util.ArrayList;

public class HouseListAdapter extends BaseAdapter {

    Context context;
    ArrayList<HouseLangModel> houseLangModelArrayList;

    public HouseListAdapter(Context context, ArrayList<HouseLangModel> langModelArrayList) {
        this.context = context;
        this.houseLangModelArrayList = langModelArrayList;
    }

    @Override
    public int getCount() {
        return houseLangModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return houseLangModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.house_table, null);

        TextView tvHouseDeets;

        tvHouseDeets = view.findViewById(R.id.tv_houseDeets);

        tvHouseDeets.setText(houseLangModelArrayList.get(position).getHouse());

        ImageView imgEdit = view.findViewById(R.id.img_edit);
        ImageView imgDelete = view.findViewById(R.id.img_delete);

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = houseLangModelArrayList.get(position).get_id();
                Log.e("id in edit: ", id);

                Intent intent = new Intent(context, HouseUpdateActivity.class);
                intent.putExtra("HOUSE_ID", id);
                intent.putExtra("HOUSE_DEETS", houseLangModelArrayList.get(position).getHouse());
                intent.putExtra("USER",houseLangModelArrayList.get(position).getUser());
                context.startActivity(intent);

            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id1 = houseLangModelArrayList.get(position).get_id();
                Log.e("id in edit: ", id1);

                Intent intent = new Intent(context, HouseUpdateActivity.class);
                intent.putExtra("HOUSE_ID", id1);
                intent.putExtra("HOUSE_DEETS", houseLangModelArrayList.get(position).getHouse());
                intent.putExtra("USER",houseLangModelArrayList.get(position).getUser());

                context.startActivity(intent);
            }
        });
        return view;

    }
}