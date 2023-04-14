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
import com.e_society.model.StaffLangModel;
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
        view = layoutInflater.inflate(R.layout.staff_table, null);

        TextView tvStaffName,tvType,tvEntry,tvExit,tvCnt,tvAdd,tvEmail,tvPass,tvAName,tvACnt;
        tvStaffName=view.findViewById(R.id.tv_staffMemberName);
        tvType=view.findViewById(R.id.tv_type);
        tvEntry=view.findViewById(R.id.tv_entryTime);
        tvExit=view.findViewById(R.id.tv_exitTime);
        tvCnt=view.findViewById(R.id.tv_contactNo);
        tvAdd=view.findViewById(R.id.tv_address);
        tvEmail=view.findViewById(R.id.tv_email);
        tvPass=view.findViewById(R.id.tv_password);
        tvAName=view.findViewById(R.id.tv_agencyName);
        tvACnt=view.findViewById(R.id.tv_agencyContactNumber);

        tvStaffName.setText(staffLangModelArrayList.get(position).getStaffMemberName());
        tvType.setText(staffLangModelArrayList.get(position).getType());
        tvEntry.setText(staffLangModelArrayList.get(position).getEntryTime());
        tvExit.setText(staffLangModelArrayList.get(position).getExitTime());
        tvCnt.setText(staffLangModelArrayList.get(position).getContactNo());
        tvAdd.setText(staffLangModelArrayList.get(position).getAddress());
        tvEmail.setText(staffLangModelArrayList.get(position).getEmail());
        tvPass.setText(staffLangModelArrayList.get(position).getPassword());
        tvAName.setText(staffLangModelArrayList.get(position).getAgencyName());
        tvACnt.setText(staffLangModelArrayList.get(position).getAgencyContactNumber());


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
                intent.putExtra("EMAIL", staffLangModelArrayList.get(position).getEmail());
                intent.putExtra("PASSWORD", staffLangModelArrayList.get(position).getPassword());
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
                intent.putExtra("EMAIL", staffLangModelArrayList.get(position).getEmail());
                intent.putExtra("PASSWORD", staffLangModelArrayList.get(position).getPassword());
                intent.putExtra("AGENCY_NAME", staffLangModelArrayList.get(position).getAgencyName());
                intent.putExtra("AGENCY_CONTACT", staffLangModelArrayList.get(position).getAgencyContactNumber());
                intent.putExtra("TYPE", staffLangModelArrayList.get(position).getType());

                context.startActivity(intent);

            }
        });

        return view;
    }
}
