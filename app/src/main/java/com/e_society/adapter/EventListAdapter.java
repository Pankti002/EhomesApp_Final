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
import com.e_society.model.EventLangModel;
import com.e_society.update.EventUpdateActivity;
import com.e_society.update.MaintenanceUpdateActivity;

import java.util.ArrayList;


public class EventListAdapter extends BaseAdapter {
    Context context;
    ArrayList<EventLangModel> eventLangModelArrayList;

    public EventListAdapter(Context context, ArrayList<EventLangModel> arrayList) {
        this.context = context;
        this.eventLangModelArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return eventLangModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventLangModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        //update
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.event_tabel, null);

        TextView tvDate,tvEndDate,tvDetails,tvRent;
        tvDate=view.findViewById(R.id.tv_eventDate);
        tvEndDate=view.findViewById(R.id.tv_eventEndDate);
        tvDetails=view.findViewById(R.id.tv_eventDetails);
        tvRent=view.findViewById(R.id.tv_rent);

        tvDate.setText(eventLangModelArrayList.get(position).getDate());
        tvEndDate.setText(eventLangModelArrayList.get(position).getEventDate());
        tvDetails.setText(eventLangModelArrayList.get(position).getEventDetails());
        tvRent.setText(eventLangModelArrayList.get(position).getRent());


        ImageView imgEdit = view.findViewById(R.id.img_edit);
        ImageView imgDelete = view.findViewById(R.id.img_delete);

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = eventLangModelArrayList.get(position).get_id();
                Log.e("id in edit :", id);

                Intent intent = new Intent(context, EventUpdateActivity.class);
                intent.putExtra("EVENT_ID", id);
                intent.putExtra("HOUSE_ID", eventLangModelArrayList.get(position).getHouse_id());
                intent.putExtra("PLACE_ID", eventLangModelArrayList.get(position).getPlace_id());
                intent.putExtra("EVENT_DATE", eventLangModelArrayList.get(position).getDate());
                intent.putExtra("EVENT_END_DATE", eventLangModelArrayList.get(position).getEventDate());
                intent.putExtra("EVENT_DETAILS", eventLangModelArrayList.get(position).getEventDetails());
                intent.putExtra("RENT", eventLangModelArrayList.get(position).getRent());

                context.startActivity(intent);
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id1 = eventLangModelArrayList.get(position).get_id();
                Log.e("id in edit: ", id1);

                Intent intent = new Intent(context, EventUpdateActivity.class);
                intent.putExtra("EVENT_ID", id1);
                intent.putExtra("HOUSE_ID", eventLangModelArrayList.get(position).getHouse_id());
                intent.putExtra("PLACE_ID", eventLangModelArrayList.get(position).getPlace_id());
                intent.putExtra("EVENT_DATE", eventLangModelArrayList.get(position).getDate());
                intent.putExtra("EVENT_END_DATE", eventLangModelArrayList.get(position).getEventDate());
                intent.putExtra("EVENT_DETAILS", eventLangModelArrayList.get(position).getEventDetails());
                intent.putExtra("RENT", eventLangModelArrayList.get(position).getRent());

                context.startActivity(intent);
            }
        });

        return view;
    }

}