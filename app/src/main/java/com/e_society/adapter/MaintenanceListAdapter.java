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

import com.e_society.update.MaintenanceUpdateActivity;
import com.e_society.R;
import com.e_society.model.MaintenanceLangModel;

import java.util.ArrayList;

public class MaintenanceListAdapter extends BaseAdapter {
    Context context;
    ArrayList<MaintenanceLangModel> maintenanceLangModelArrayList;

    public MaintenanceListAdapter(Context context, ArrayList<MaintenanceLangModel> langModelArrayList) {
        this.context = context;
        this.maintenanceLangModelArrayList = langModelArrayList;
    }

    @Override
    public int getCount() {

        return maintenanceLangModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {

        return maintenanceLangModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.raw_list, null);

        TextView tvData = view.findViewById(R.id.tv_data);

        tvData.setText(maintenanceLangModelArrayList.get(position).get_id() + " " +
                maintenanceLangModelArrayList.get(position).getHouse() + " " +
                maintenanceLangModelArrayList.get(position).getCreationDate() + " " +
                maintenanceLangModelArrayList.get(position).getMonth() + " " +
                maintenanceLangModelArrayList.get(position).getMaintenanceAmount() + " "+
                maintenanceLangModelArrayList.get(position).getPaymentDate() +" "+
                maintenanceLangModelArrayList.get(position).getLastDate() +" "+
                maintenanceLangModelArrayList.get(position).getPenalty());

        ImageView imgEdit = view.findViewById(R.id.img_edit);
        ImageView imgDelete = view.findViewById(R.id.img_delete);


        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = maintenanceLangModelArrayList.get(position).get_id();
                Log.e("id in edit: ", id);

                Intent intent = new Intent(context, MaintenanceUpdateActivity.class);
                intent.putExtra("MAINTENANCE_ID", id);
                intent.putExtra("MAINTENANCE_HOUSE", maintenanceLangModelArrayList.get(position).getHouse());
                intent.putExtra("MAINTENANCE_AMOUNT", maintenanceLangModelArrayList.get(position).getMaintenanceAmount());
                intent.putExtra("MONTH", maintenanceLangModelArrayList.get(position).getMonth());
                intent.putExtra("PENALTY", maintenanceLangModelArrayList.get(position).getPenalty());
                intent.putExtra("CREATION_DATE", maintenanceLangModelArrayList.get(position).getCreationDate());
                intent.putExtra("PAYMENT_DATE", maintenanceLangModelArrayList.get(position).getPaymentDate());
                intent.putExtra("LAST_DATE", maintenanceLangModelArrayList.get(position).getLastDate());

                context.startActivity(intent);

            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id1 = maintenanceLangModelArrayList.get(position).get_id();
                Log.e("id in delete: ", id1);

                Intent intent = new Intent(context, MaintenanceUpdateActivity.class);
                intent.putExtra("MAINTENANCE_ID", id1);
                intent.putExtra("MAINTENANCE_HOUSE", maintenanceLangModelArrayList.get(position).getHouse());
                intent.putExtra("MAINTENANCE_AMOUNT", maintenanceLangModelArrayList.get(position).getMaintenanceAmount());
                intent.putExtra("PENALTY", maintenanceLangModelArrayList.get(position).getPenalty());
                intent.putExtra("CREATION_DATE", maintenanceLangModelArrayList.get(position).getCreationDate());
                intent.putExtra("PAYMENT_DATE", maintenanceLangModelArrayList.get(position).getPaymentDate());
                intent.putExtra("LAST_DATE", maintenanceLangModelArrayList.get(position).getLastDate());

                context.startActivity(intent);
            }
        });

        return view;
    }

}