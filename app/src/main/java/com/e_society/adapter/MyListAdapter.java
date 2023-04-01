package com.e_society.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.e_society.R;
import com.e_society.model.LangModel;

public class MyListAdapter extends BaseAdapter {
    Context context;
    ArrayList<LangModel> langModelArrayList;

    public MyListAdapter(Context context, ArrayList<LangModel> langModelArrayList) {
        this.context = context;
        this.langModelArrayList = langModelArrayList;
    }

    @Override
    public int getCount() {

        return langModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {

        return langModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.raw_grid, null);

        ImageView imgLang = view.findViewById(R.id.img_Lang);
        TextView tvData = view.findViewById(R.id.tv_data);

        tvData.setText(langModelArrayList.get(i).getStrData());
        imgLang.setImageResource(langModelArrayList.get(i).getImgData());

       /* tvData.setText(langModelArrayList.get(position).getStrData() + " " + langModelArrayList.get(position).getImgData());*/

        return view;
    }
}

