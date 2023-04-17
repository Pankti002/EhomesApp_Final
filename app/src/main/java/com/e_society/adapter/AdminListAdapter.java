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
import com.e_society.model.AdminLangModel;
import com.e_society.model.HouseLangModel;
import com.e_society.update.AdminUpdateActivity;
import com.e_society.update.HouseUpdateActivity;

import java.util.ArrayList;

public class AdminListAdapter extends BaseAdapter {

    Context context;
    ArrayList<AdminLangModel> adminLangModelArrayList;

    public AdminListAdapter(Context context, ArrayList<AdminLangModel> langModelArrayList) {
        this.context = context;
        this.adminLangModelArrayList = langModelArrayList;
    }

    @Override
    public int getCount() {
        return adminLangModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return adminLangModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.admin_table, null);

        TextView tvEmail, tvPassword;

        tvEmail = view.findViewById(R.id.tv_adEmail);
        tvPassword = view.findViewById(R.id.tv_AdPassword);

        tvEmail.setText(adminLangModelArrayList.get(position).getEmail());
        tvPassword.setText(adminLangModelArrayList.get(position).getPassword());

        ImageView imgEdit = view.findViewById(R.id.img_edit);
        ImageView imgDelete = view.findViewById(R.id.img_delete);

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = adminLangModelArrayList.get(position).get_id();

                Intent intent = new Intent(context, AdminUpdateActivity.class);
                intent.putExtra("ADMIN_ID", id);
                intent.putExtra("EMAIL", adminLangModelArrayList.get(position).getEmail());
                intent.putExtra("PASSWORD", adminLangModelArrayList.get(position).getPassword());
                intent.putExtra("ROLE", adminLangModelArrayList.get(position).getRoleId());
                context.startActivity(intent);

            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id1 = adminLangModelArrayList.get(position).get_id();
                Log.e("id in edit: ", id1);

                Intent intent = new Intent(context, AdminUpdateActivity.class);
                intent.putExtra("ADMIN_ID", id1);
                intent.putExtra("EMAIL", adminLangModelArrayList.get(position).getEmail());
                intent.putExtra("PASSWORD", adminLangModelArrayList.get(position).getPassword());
                intent.putExtra("ROLE", adminLangModelArrayList.get(position).getRoleId());

                context.startActivity(intent);
            }
        });
        return view;


    }
}