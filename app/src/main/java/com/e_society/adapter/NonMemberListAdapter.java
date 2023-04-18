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

import com.e_society.EventActivity;
import com.e_society.LoginActivity;
import com.e_society.NonMemberActivity;
import com.e_society.R;
import com.e_society.model.NonMemberLangModel;
import com.e_society.model.PlaceLangModel;
import com.e_society.update.NonMemberUpdateActivity;
import com.e_society.update.PlaceUpdateActivity;

import java.util.ArrayList;

public class NonMemberListAdapter extends BaseAdapter {

    Context context;
    ArrayList<NonMemberLangModel> nonMemberLangModelArrayList;

    public NonMemberListAdapter(Context context, ArrayList<NonMemberLangModel> langModelArrayList) {
        this.context = context;
        this.nonMemberLangModelArrayList = langModelArrayList;
    }

    @Override
    public int getCount() {
        return nonMemberLangModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return nonMemberLangModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.non_member_table, null);


        TextView tvHouse,tvName, tvArrivingTime, tvDate,tvIsVisited, tvPickup, tvStatus;
        tvHouse=view.findViewById(R.id.tv_house);
        tvName = view.findViewById(R.id.tv_name);
        tvArrivingTime = view.findViewById(R.id.tvArrivingTime);
        tvDate=view.findViewById(R.id.tvDate);
        tvIsVisited = view.findViewById(R.id.tv_isVisited);
        tvPickup = view.findViewById(R.id.tv_pickup);
        tvStatus = view.findViewById(R.id.tv_status);

        tvHouse.setText(nonMemberLangModelArrayList.get(position).getHouseName());
        tvName.setText(nonMemberLangModelArrayList.get(position).getNonMemberName());
        tvArrivingTime.setText(nonMemberLangModelArrayList.get(position).getArrivingTime());
        tvDate.setText(nonMemberLangModelArrayList.get(position).getDate());
        tvIsVisited.setText(nonMemberLangModelArrayList.get(position).getIsVisited());
        tvPickup.setText(nonMemberLangModelArrayList.get(position).getPickup());
        tvStatus.setText(nonMemberLangModelArrayList.get(position).getDeliver());

        ImageView imgEdit = view.findViewById(R.id.img_edit);
        ImageView imgDelete = view.findViewById(R.id.img_delete);

        String name= LoginActivity.getName();
        if(name.equals("user") || name.equals("security"))
        {
            imgEdit.setEnabled(false);
            imgDelete.setEnabled(false);
        }
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = nonMemberLangModelArrayList.get(position).getId();
                Log.e("id in edit: ", id);

                Intent intent = new Intent(context, NonMemberUpdateActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("HOUSE_ID",nonMemberLangModelArrayList.get(position).getHouseId());
                intent.putExtra("NAME", nonMemberLangModelArrayList.get(position).getNonMemberName());
                intent.putExtra("ARRIVING_TIME", nonMemberLangModelArrayList.get(position).getArrivingTime());
                intent.putExtra("DATE", nonMemberLangModelArrayList.get(position).getDate());
                intent.putExtra("VISITED", nonMemberLangModelArrayList.get(position).getIsVisited());
                intent.putExtra("PICKUP", nonMemberLangModelArrayList.get(position).getPickup());
                intent.putExtra("DELIVER", nonMemberLangModelArrayList.get(position).getDeliver());


                context.startActivity(intent);
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id1 = nonMemberLangModelArrayList.get(position).getId();
                Log.e("id in delete: ", id1);

                Intent intent = new Intent(context, NonMemberUpdateActivity.class);
                intent.putExtra("ID", id1);
                intent.putExtra("HOUSE_ID",nonMemberLangModelArrayList.get(position).getHouseId());
                intent.putExtra("NAME", nonMemberLangModelArrayList.get(position).getNonMemberName());
                intent.putExtra("ARRIVING_TIME", nonMemberLangModelArrayList.get(position).getArrivingTime());
                intent.putExtra("DATE", nonMemberLangModelArrayList.get(position).getDate());
                intent.putExtra("VISITED", nonMemberLangModelArrayList.get(position).getIsVisited());
                intent.putExtra("PICKUP", nonMemberLangModelArrayList.get(position).getPickup());
                intent.putExtra("DELIVER", nonMemberLangModelArrayList.get(position).getDeliver());


                context.startActivity(intent);
            }
        });

        return view;
    }
}