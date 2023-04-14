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

import com.e_society.update.MaintenanceMasterUpdateActivity;
import com.e_society.R;
import com.e_society.model.MaintenanceMasterLangModel;

import java.util.ArrayList;

public class MaintenanceMasterListAdapter extends BaseAdapter {
    Context context;
    ArrayList<MaintenanceMasterLangModel> maintenanceMasterLangModelArrayList;

    public MaintenanceMasterListAdapter(Context context, ArrayList<MaintenanceMasterLangModel> maintenanceMasterLangModelArrayList) {
            this.context=context;
            this.maintenanceMasterLangModelArrayList=maintenanceMasterLangModelArrayList;

    }


    @Override
    public int getCount() {
        return maintenanceMasterLangModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return maintenanceMasterLangModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.master_table, null);


        TextView tvAmount,tvPenalty;

        tvAmount=view.findViewById(R.id.tv_amount);
        tvPenalty=view.findViewById(R.id.tv_penalty);

        tvAmount.setText(maintenanceMasterLangModelArrayList.get(position).getMaintenanceAmount());
        tvPenalty.setText(maintenanceMasterLangModelArrayList.get(position).getPenalty());

        ImageView imgEdit = view.findViewById(R.id.img_edit);

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = maintenanceMasterLangModelArrayList.get(position).get_id();
                Log.e("id in edit :", id);

                Intent intent = new Intent(context, MaintenanceMasterUpdateActivity.class);
                intent.putExtra("MASTER_ID", id);
                intent.putExtra("MAINTENANCE_AMOUNT", maintenanceMasterLangModelArrayList.get(position).getMaintenanceAmount());
                intent.putExtra("PENALTY", maintenanceMasterLangModelArrayList.get(position).getPenalty());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
