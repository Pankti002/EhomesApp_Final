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

import android.widget.BaseAdapter;

import com.e_society.LoginActivity;
import com.e_society.R;
import com.e_society.model.UserLangModel;
import com.e_society.update.UserUpdateActivity;

import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {

    Context context;
    ArrayList<UserLangModel> userLangModelArrayList;

    public UserListAdapter(Context context, ArrayList<UserLangModel> langModelArrayList) {
        this.context = context;
        this.userLangModelArrayList = langModelArrayList;
    }

    @Override
    public int getCount() {
        return userLangModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userLangModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.user_table, null);


        TextView tvFn, tvLn, tvDob, tvAge, tvGen, tvCnt, tvEm, tvPwd;

        tvFn = view.findViewById(R.id.tv_fn);
        tvLn = view.findViewById(R.id.tv_ln);
        tvDob = view.findViewById(R.id.tv_dob);
        tvAge = view.findViewById(R.id.tv_age);
        tvGen = view.findViewById(R.id.tv_gen);
        tvCnt = view.findViewById(R.id.tv_cnt);
        tvEm = view.findViewById(R.id.tv_em);
        tvPwd=view.findViewById(R.id.tv_pwd);

        tvFn.setText(userLangModelArrayList.get(position).getFirstName());
        tvLn.setText(userLangModelArrayList.get(position).getLastName());
        tvDob.setText(userLangModelArrayList.get(position).getDateOfBirth());
        tvAge.setText(userLangModelArrayList.get(position).getAge());
        tvGen.setText(userLangModelArrayList.get(position).getGender());
        tvCnt.setText(userLangModelArrayList.get(position).getContactNo());
        tvEm.setText(userLangModelArrayList.get(position).getEmail());
        tvPwd.setText(userLangModelArrayList.get(position).getPassword());

        ImageView
                imgEdit = view.findViewById(R.id.img_edit);
        ImageView imgDelete = view.findViewById(R.id.img_delete);

        String name= LoginActivity.getName();
        if(name.equals("user"))
        {
            imgEdit.setEnabled(false);
            imgDelete.setEnabled(false);
        }

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = userLangModelArrayList.get(position).get_id();
                Log.e("id in edit: ", id);

                Intent intent = new Intent(context, UserUpdateActivity.class);
                intent.putExtra("USER_ID", id);
                intent.putExtra("ROLE_ID", userLangModelArrayList.get(position).getRoleId());
                intent.putExtra("FIRST_NAME", userLangModelArrayList.get(position).getFirstName());
                intent.putExtra("LAST_NAME", userLangModelArrayList.get(position).getLastName());
                intent.putExtra("DATE_OF_BIRTH", userLangModelArrayList.get(position).getDateOfBirth());
                intent.putExtra("AGE", userLangModelArrayList.get(position).getAge());
                intent.putExtra("GENDER", userLangModelArrayList.get(position).getGender());
                intent.putExtra("CONTACT_NO", userLangModelArrayList.get(position).getContactNo());
                intent.putExtra("EMAIL", userLangModelArrayList.get(position).getEmail());
                intent.putExtra("PASSWORD", userLangModelArrayList.get(position).getPassword());

                context.startActivity(intent);
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = userLangModelArrayList.get(position).get_id();
                Log.e("id in edit: ", id);

                Intent intent = new Intent(context, UserUpdateActivity.class);
                intent.putExtra("USER_ID", id);
                intent.putExtra("ROLE_ID", userLangModelArrayList.get(position).getRoleId());
                intent.putExtra("FIRST_NAME", userLangModelArrayList.get(position).getFirstName());
                intent.putExtra("LAST_NAME", userLangModelArrayList.get(position).getLastName());
                intent.putExtra("DATE_OF_BIRTH", userLangModelArrayList.get(position).getDateOfBirth());
                intent.putExtra("Age", userLangModelArrayList.get(position).getAge());
                intent.putExtra("GENDER", userLangModelArrayList.get(position).getGender());
                intent.putExtra("CONTACT_NO", userLangModelArrayList.get(position).getContactNo());
                intent.putExtra("EMAIL", userLangModelArrayList.get(position).getEmail());
                intent.putExtra("PASSWORD", userLangModelArrayList.get(position).getPassword());

                context.startActivity(intent);
            }
        });
        return view;
    }
}