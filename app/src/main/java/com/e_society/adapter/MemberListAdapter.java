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
import com.e_society.model.MemberLangModel;
import com.e_society.update.EventUpdateActivity;
import com.e_society.update.MemberUpdateActivity;

import java.util.ArrayList;

public class MemberListAdapter extends BaseAdapter {

    Context context;
    ArrayList<MemberLangModel> memberLangModelArrayList;

    public MemberListAdapter(Context context, ArrayList<MemberLangModel> arrayList) {
        this.context = context;
        this.memberLangModelArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return memberLangModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return memberLangModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.raw_list, null);

        TextView TvData = view.findViewById(R.id.tv_data);
        TvData.setText(memberLangModelArrayList.get(position).get_id() + " "
                + memberLangModelArrayList.get(position).getHouseId()+" "
                + memberLangModelArrayList.get(position).getMemberName() + " "
                + memberLangModelArrayList.get(position).getAge() + " "
                + memberLangModelArrayList.get(position).getContactNo() + " "
                + memberLangModelArrayList.get(position).getGender()+" "
                + memberLangModelArrayList.get(position).getDateOfBirth());

        ImageView imgEdit = view.findViewById(R.id.img_edit);
        ImageView imgDelete = view.findViewById(R.id.img_delete);

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = memberLangModelArrayList.get(position).get_id();
                Log.e("id in edit :", id);

                Intent intent = new Intent(context, MemberUpdateActivity.class);
                intent.putExtra("MEMBER_ID", id);
                intent.putExtra("HOUSE_ID", memberLangModelArrayList.get(position).getHouseId());
                intent.putExtra("MEMBER_NAME", memberLangModelArrayList.get(position).getMemberName());
                intent.putExtra("AGE", memberLangModelArrayList.get(position).getAge());
                intent.putExtra("CONTACT_NUMBER", memberLangModelArrayList.get(position).getContactNo());
                intent.putExtra("GENDER", memberLangModelArrayList.get(position).getGender());
                intent.putExtra("DATE_OF_BIRTH", memberLangModelArrayList.get(position).getDateOfBirth());

                context.startActivity(intent);
            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id1 = memberLangModelArrayList.get(position).get_id();
                Log.e("id in edit: ", id1);


                Intent intent = new Intent(context, MemberUpdateActivity.class);
                intent.putExtra("MEMBER_ID", id1);
                intent.putExtra("HOUSE_ID", memberLangModelArrayList.get(position).getHouseId());
                intent.putExtra("MEMBER_NAME", memberLangModelArrayList.get(position).getMemberName());
                intent.putExtra("AGE", memberLangModelArrayList.get(position).getAge());
                intent.putExtra("CONTACT_NO", memberLangModelArrayList.get(position).getContactNo());
                intent.putExtra("GENDER", memberLangModelArrayList.get(position).getGender());
                intent.putExtra("DATE_OF_BIRTH", memberLangModelArrayList.get(position).getDateOfBirth());

                context.startActivity(intent);
            }
        });

        return (view);


    }
}