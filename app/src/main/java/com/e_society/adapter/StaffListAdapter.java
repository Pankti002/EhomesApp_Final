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
import com.e_society.display.StaffDisplayActivity;
import com.e_society.model.StaffLangModel;
import com.e_society.update.MaintenanceUpdateActivity;
import com.e_society.update.StaffUpdateAcivity;

import java.util.ArrayList;

public class StaffListAdapter extends BaseAdapter {

    Context context;
    ArrayList<StaffLangModel> staffLangModelArrayList;

    public StaffListAdapter(Context context, ArrayList<StaffLangModel> staffLangModelArrayList) {
        this.context = context;
        this.staffLangModelArrayList = staffLangModelArrayList;
    }

    @Override
    public int getCount() {
        return staffLangModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return staffLangModelArrayList.get(position);
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

        tvData.setText(staffLangModelArrayList.get(position).getStaffMemberName() + " " + staffLangModelArrayList.get(position).getType() +
                " " + staffLangModelArrayList.get(position).getEntryTime() + " " + staffLangModelArrayList.get(position).getExitTime() + " " +
                staffLangModelArrayList.get(position).getContactNo() + " " + staffLangModelArrayList.get(position).getAddress() + " " +
                staffLangModelArrayList.get(position).getAgencyName() + " "
                + staffLangModelArrayList.get(position).getAgencyContactNumber());

        ImageView imgEdit = view.findViewById(R.id.img_edit);
        ImageView imgDelete = view.findViewById(R.id.img_delete);


        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = staffLangModelArrayList.get(position).get_id();
                Log.e("id in edit: ", "id " + id);
                Log.e("position: ", "id: " + staffLangModelArrayList.get(position).get_id());

                Intent intent = new Intent(context, StaffUpdateAcivity.class);
                intent.putExtra("STAFF_ID", id);
                intent.putExtra("STAFF_NAME", staffLangModelArrayList.get(position).getStaffMemberName());
                intent.putExtra("ENTRY_TIME", staffLangModelArrayList.get(position).getEntryTime());
                intent.putExtra("EXIT_TIME", staffLangModelArrayList.get(position).getExitTime());
                intent.putExtra("CONTACT", staffLangModelArrayList.get(position).getContactNo());
                intent.putExtra("ADDRESS", staffLangModelArrayList.get(position).getAddress());
                intent.putExtra("AGENCY_NAME", staffLangModelArrayList.get(position).getAgencyName());
                intent.putExtra("AGENCY_CONTACT", staffLangModelArrayList.get(position).getAgencyContactNumber());
                intent.putExtra("TYPE", staffLangModelArrayList.get(position).getType());

                context.startActivity(intent);

            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id1 = staffLangModelArrayList.get(position).get_id();
                Log.e("id in delete: ", id1);

                Intent intent = new Intent(context, StaffUpdateAcivity.class);
                intent.putExtra("STAFF_ID", id1);
                intent.putExtra("STAFF_NAME", staffLangModelArrayList.get(position).getStaffMemberName());
                intent.putExtra("ENTRY_TIME", staffLangModelArrayList.get(position).getEntryTime());
                intent.putExtra("EXIT_TIME", staffLangModelArrayList.get(position).getExitTime());
                intent.putExtra("CONTACT", staffLangModelArrayList.get(position).getContactNo());
                intent.putExtra("ADDRESS", staffLangModelArrayList.get(position).getAddress());
                intent.putExtra("AGENCY_NAME", staffLangModelArrayList.get(position).getAgencyName());
                intent.putExtra("AGENCY_CONTACT", staffLangModelArrayList.get(position).getAgencyContactNumber());
                intent.putExtra("TYPE", staffLangModelArrayList.get(position).getType());

                context.startActivity(intent);

            }
        });

        return view;
    }
}
