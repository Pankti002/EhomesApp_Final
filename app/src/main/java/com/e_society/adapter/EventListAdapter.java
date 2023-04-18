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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.e_society.EventActivity;
import com.e_society.LoginActivity;
import com.e_society.R;
import com.e_society.display.PlaceDisplayActivity;
import com.e_society.model.EventLangModel;
import com.e_society.model.PlaceLangModel;
import com.e_society.update.EventUpdateActivity;
import com.e_society.utils.Utils;
import com.e_society.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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



        TextView tvPlace,tvHouse,tvDate, tvEndDate, tvDetails, tvRent;
        tvHouse=view.findViewById(R.id.tv_house);
        tvPlace=view.findViewById(R.id.tv_place);
        tvDate = view.findViewById(R.id.tv_eventDate);
        tvEndDate = view.findViewById(R.id.tv_eventEndDate);
        tvDetails = view.findViewById(R.id.tv_eventDetails);
        tvRent = view.findViewById(R.id.tv_rent);

        tvHouse.setText(eventLangModelArrayList.get(position).getHouseName());
        tvPlace.setText(eventLangModelArrayList.get(position).getPlaceName());
        tvDate.setText(eventLangModelArrayList.get(position).getDate());
        tvEndDate.setText(eventLangModelArrayList.get(position).getEventDate());
        tvDetails.setText(eventLangModelArrayList.get(position).getEventDetails());
        tvRent.setText(eventLangModelArrayList.get(position).getRent());


        ImageView imgDelete = view.findViewById(R.id.img_delete);

        String name = LoginActivity.getName();
        if (name.equals("user")) {
            imgDelete.setEnabled(false);
        }


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