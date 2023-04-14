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
import com.e_society.update.RoleUpdateActivity;
import com.e_society.model.RoleLangModel;

import java.util.ArrayList;

public class MyRoleAdapter extends BaseAdapter {

    Context context;
    ArrayList<RoleLangModel> roleLangModelArrayList;

    public MyRoleAdapter(Context context, ArrayList<RoleLangModel> langModelArrayList) {
        this.context = context;
        this.roleLangModelArrayList = langModelArrayList;
    }

    @Override
    public int getCount() {
        return roleLangModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return roleLangModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.role_table, null);

        TextView tvName = view.findViewById(R.id.tv_roleName);
        tvName.setText(roleLangModelArrayList.get(position).getRoleName());

        ImageView imgEdit = view.findViewById(R.id.img_edit);
        ImageView imgDelete = view.findViewById(R.id.img_delete);

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = roleLangModelArrayList.get(position).get_id();
                Log.e("id in edit: ", id);

                Intent intent = new Intent(context, RoleUpdateActivity.class);
                intent.putExtra("ROLE_ID", id);
                intent.putExtra("ROLE_NAME", roleLangModelArrayList.get(position).getRoleName());
                context.startActivity(intent);

            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = roleLangModelArrayList.get(position).get_id();
                Log.e("id in delete: ", id);

                Intent intent = new Intent(context, RoleUpdateActivity.class);
                intent.putExtra("ROLE_ID", id);
                intent.putExtra("ROLE_NAME", roleLangModelArrayList.get(position).getRoleName());
                context.startActivity(intent);
            }
        });
        return view;
    }
}